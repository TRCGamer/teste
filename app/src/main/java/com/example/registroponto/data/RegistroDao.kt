package com.example.registroponto.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RegistroDao {

    @Insert
    suspend fun inserir(registro: Registro): Long

    @Query("SELECT * FROM registros ORDER BY dataHora DESC")
    fun getTodosRegistros(): Flow<List<Registro>>

    @Query("SELECT * FROM registros ORDER BY dataHora DESC LIMIT 1")
    suspend fun getUltimoRegistro(): Registro?

    @Query("SELECT * FROM registros ORDER BY dataHora ASC")
    suspend fun getTodosRegistrosParaExportar(): List<Registro>

    @Delete
    suspend fun deletar(registro: Registro)

    @Query("DELETE FROM registros")
    suspend fun deletarTodos()
}
