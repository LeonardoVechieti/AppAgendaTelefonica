package com.example.agendacontatos
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface ContatoDao {

    @Query("SELECT * FROM Contato")
    fun buscaContato(): List<Contato>

    @Insert
    fun insereContato(task: Contato)

    @Delete
    fun deleteContato(task: Contato)

    @Update
    fun updateContato(task: Contato)

    @Query ("SELECT * FROM Contato WHERE id = :id")
    fun buscaContatoPorId(id: String): Contato

    @Query ("UPDATE Contato SET nome = :nome, fone = :telefone, email = :email WHERE id = :id")
    fun updateContatoPorId(id: String, nome: String, telefone: String, email: String)
}