package com.example.registroponto.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "registros")
data class Registro(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tipo: TipoRegistro,
    val dataHora: Long = System.currentTimeMillis(),
    val latitude: Double,
    val longitude: Double
) {
    fun getDataHoraFormatada(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(dataHora))
    }

    fun getLocalizacaoFormatada(): String {
        return "Lat: %.6f, Lng: %.6f".format(latitude, longitude)
    }
}

enum class TipoRegistro {
    ENTRADA,
    SAIDA
}
