package com.example.agendacontatos
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity
class Contato (nome: String, fone: String, email: String ) {
    @PrimaryKey (autoGenerate = true) var id: Int = 0
    @ColumnInfo var nome: String = nome
    @ColumnInfo var fone: String = fone
    @ColumnInfo var email: String = email
}
