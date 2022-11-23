package com.example.agendacontatos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contato::class], version = 1)
abstract class ContatoDatabase  : RoomDatabase(){
    abstract fun ContatoDao(): ContatoDao
    fun returnBd(context: Context): ContatoDatabase {
        return Room. databaseBuilder(
            context,
            ContatoDatabase::class.java,
            "ContatoDatabase.db"
        ).allowMainThreadQueries().build()
    }
}