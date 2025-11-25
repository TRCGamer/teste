package com.example.registroponto.data

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromTipoRegistro(tipo: TipoRegistro): String {
        return tipo.name
    }

    @TypeConverter
    fun toTipoRegistro(value: String): TipoRegistro {
        return TipoRegistro.valueOf(value)
    }
}
