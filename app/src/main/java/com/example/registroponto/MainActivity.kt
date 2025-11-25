package com.example.registroponto

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registroponto.adapter.RegistroAdapter
import com.example.registroponto.data.*
import com.example.registroponto.databinding.ActivityMainBinding
import com.example.registroponto.export.ExcelExporter
import com.example.registroponto.location.LocationService
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: AppDatabase
    private lateinit var registroDao: RegistroDao
    private lateinit var locationService: LocationService
    private lateinit var adapter: RegistroAdapter
    private lateinit var excelExporter: ExcelExporter

    private var currentLocation: Location? = null

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            iniciarAtualizacoesDeLocalizacao()
        } else {
            Toast.makeText(
                this,
                getString(R.string.permissao_negada),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDatabase()
        setupLocationService()
        setupRecyclerView()
        setupClickListeners()
        requestLocationPermission()
        observarRegistros()
    }

    private fun setupDatabase() {
        database = AppDatabase.getDatabase(this)
        registroDao = database.registroDao()
        excelExporter = ExcelExporter(this)
    }

    private fun setupLocationService() {
        locationService = LocationService(this)
    }

    private fun setupRecyclerView() {
        adapter = RegistroAdapter()
        binding.rvRegistros.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupClickListeners() {
        binding.btnEntrada.setOnClickListener {
            registrarPonto(TipoRegistro.ENTRADA)
        }

        binding.btnSaida.setOnClickListener {
            registrarPonto(TipoRegistro.SAIDA)
        }

        binding.btnRelatorio.setOnClickListener {
            val intent = Intent(this, RelatorioActivity::class.java)
            startActivity(intent)
        }

        binding.btnExportar.setOnClickListener {
            exportarParaXLSX()
        }
    }

    private fun requestLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                iniciarAtualizacoesDeLocalizacao()
            }
            else -> {
                locationPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    private fun iniciarAtualizacoesDeLocalizacao() {
        lifecycleScope.launch {
            val location = locationService.getCurrentLocation()
            if (location != null) {
                currentLocation = location
                atualizarTextoLocalizacao(location)
            }
        }

        locationService.startLocationUpdates { location ->
            currentLocation = location
            atualizarTextoLocalizacao(location)
        }
    }

    private fun atualizarTextoLocalizacao(location: Location) {
        binding.tvCurrentLocation.text = getString(
            R.string.localizacao_formato,
            location.latitude,
            location.longitude
        )
    }

    private fun registrarPonto(tipo: TipoRegistro) {
        if (currentLocation == null) {
            Toast.makeText(
                this,
                getString(R.string.erro_localizacao),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val registro = Registro(
            tipo = tipo,
            latitude = currentLocation!!.latitude,
            longitude = currentLocation!!.longitude
        )

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                registroDao.inserir(registro)
            }

            Toast.makeText(
                this@MainActivity,
                getString(R.string.registro_salvo, tipo.name),
                Toast.LENGTH_SHORT
            ).show()

            atualizarUltimoRegistro()
        }
    }

    private fun observarRegistros() {
        lifecycleScope.launch {
            registroDao.getTodosRegistros().collect { registros ->
                adapter.submitList(registros)
            }
        }

        atualizarUltimoRegistro()
    }

    private fun atualizarUltimoRegistro() {
        lifecycleScope.launch {
            val ultimoRegistro = withContext(Dispatchers.IO) {
                registroDao.getUltimoRegistro()
            }

            if (ultimoRegistro != null) {
                binding.tvLastRecord.text = getString(
                    R.string.ultimo_registro,
                    "${ultimoRegistro.tipo.name} - ${ultimoRegistro.getDataHoraFormatada()}"
                )
            } else {
                binding.tvLastRecord.text = getString(R.string.ultimo_registro_nenhum)
            }
        }
    }

    private fun exportarParaXLSX() {
        lifecycleScope.launch {
            val registros = withContext(Dispatchers.IO) {
                registroDao.getTodosRegistrosParaExportar()
            }

            if (registros.isEmpty()) {
                Toast.makeText(
                    this@MainActivity,
                    "Nenhum registro para exportar",
                    Toast.LENGTH_SHORT
                ).show()
                return@launch
            }

            val file = withContext(Dispatchers.IO) {
                excelExporter.exportarParaXLSX(registros)
            }

            if (file != null) {
                mostrarDialogoArquivoExportado(file)
            } else {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.erro_exportar),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun mostrarDialogoArquivoExportado(file: File) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Arquivo Exportado")
            .setMessage(getString(R.string.arquivo_exportado, file.name))
            .setPositiveButton("Compartilhar") { _, _ ->
                compartilharArquivo(file)
            }
            .setNegativeButton("OK", null)
            .show()
    }

    private fun compartilharArquivo(file: File) {
        try {
            val uri: Uri = FileProvider.getUriForFile(
                this,
                "${packageName}.fileprovider",
                file
            )

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            startActivity(Intent.createChooser(intent, "Compartilhar arquivo"))
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao compartilhar arquivo", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationService.stopLocationUpdates()
    }
}
