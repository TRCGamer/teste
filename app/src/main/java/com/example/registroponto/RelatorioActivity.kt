package com.example.registroponto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registroponto.adapter.RelatorioAdapter
import com.example.registroponto.data.AppDatabase
import com.example.registroponto.data.RegistroDao
import com.example.registroponto.data.TipoRegistro
import com.example.registroponto.databinding.ActivityRelatorioBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class RelatorioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRelatorioBinding
    private lateinit var registroDao: RegistroDao
    private lateinit var adapter: RelatorioAdapter

    data class ItemRelatorio(
        val data: String,
        val horasTrabalhadas: String,
        val detalhes: String
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRelatorioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Relatório de Horas"

        registroDao = AppDatabase.getDatabase(this).registroDao()

        setupRecyclerView()
        carregarRelatorio()
    }

    private fun setupRecyclerView() {
        adapter = RelatorioAdapter()
        binding.rvRelatorio.apply {
            layoutManager = LinearLayoutManager(this@RelatorioActivity)
            adapter = this@RelatorioActivity.adapter
        }
    }

    private fun carregarRelatorio() {
        lifecycleScope.launch {
            val registros = withContext(Dispatchers.IO) {
                registroDao.getTodosRegistrosParaExportar()
            }

            val itensRelatorio = calcularRelatorio(registros)
            adapter.submitList(itensRelatorio)

            // Atualizar totais
            val totalHoras = calcularTotalHoras(registros)
            binding.tvTotalGeral.text = "Total Geral: ${formatarHoras(totalHoras)}"
        }
    }

    private fun calcularRelatorio(registros: List<com.example.registroponto.data.Registro>): List<ItemRelatorio> {
        val sdfDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val sdfTime = SimpleDateFormat("HH:mm", Locale.getDefault())

        return registros
            .groupBy { sdfDate.format(Date(it.dataHora)) }
            .map { (data, registrosDia) ->
                val ordenados = registrosDia.sortedBy { it.dataHora }
                val horasTrabalhadas = calcularHorasDia(ordenados)

                val detalhes = buildString {
                    ordenados.forEach { registro ->
                        append("${registro.tipo.name}: ${sdfTime.format(Date(registro.dataHora))}  ")
                    }
                }

                ItemRelatorio(
                    data = data,
                    horasTrabalhadas = formatarHoras(horasTrabalhadas),
                    detalhes = detalhes.trim()
                )
            }
            .sortedByDescending {
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(it.data)
            }
    }

    private fun calcularHorasDia(registros: List<com.example.registroponto.data.Registro>): Double {
        var totalMinutos = 0.0
        var ultimaEntrada: Long? = null

        registros.forEach { registro ->
            when (registro.tipo) {
                TipoRegistro.ENTRADA -> {
                    ultimaEntrada = registro.dataHora
                }
                TipoRegistro.SAIDA -> {
                    ultimaEntrada?.let { entrada ->
                        val diff = registro.dataHora - entrada
                        totalMinutos += diff / (1000.0 * 60.0)
                        ultimaEntrada = null
                    }
                }
            }
        }

        return totalMinutos / 60.0
    }

    private fun calcularTotalHoras(registros: List<com.example.registroponto.data.Registro>): Double {
        val sdfDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return registros
            .groupBy { sdfDate.format(Date(it.dataHora)) }
            .values
            .sumOf { registrosDia ->
                calcularHorasDia(registrosDia.sortedBy { it.dataHora })
            }
    }

    private fun formatarHoras(horas: Double): String {
        val h = horas.toInt()
        val m = ((horas - h) * 60).toInt()
        return String.format("%dh %02dmin", h, m)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
