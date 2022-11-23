package com.example.agendacontatos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.agendacontatos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.floatingActionButton.setOnClickListener(this)
        //remove o título da action bar
        supportActionBar!!.hide()

        val db = Room.databaseBuilder(
            this,
            ContatoDatabase::class.java,
            "ContatoDatabase.db"
        ).allowMainThreadQueries().build()


        //insere dados

        val contatoDao = db.ContatoDao()

        //contatoDao.insereContato(Contato( nome = "João", "123456789", "leonard@gmail.com"))


    }

    override fun onClick(v: View) {
        when(v.id){
            binding.floatingActionButton.id -> {
                //abre a tela de cadastro
                val intent = Intent(this, CreateContato::class.java)
                //intent.putExtra("id", "null")
                startActivity(intent)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        val db = Room.databaseBuilder(
            this,
            ContatoDatabase::class.java,
            "ContatoDatabase.db"
        ).allowMainThreadQueries().build()



        val contatoDao = db.ContatoDao()
        contatoDao.buscaContato()
        val contato = contatoDao.buscaContato()
        if(contato.isNotEmpty()){
            binding.recyclerChecklist.adapter = ContatoAdapter(contato as ArrayList<Contato>, this)
            binding.recyclerChecklist.layoutManager = LinearLayoutManager(this)
        } else {
            Toast.makeText(this, "Não há contatos cadastrados", Toast.LENGTH_SHORT).show()
        }
    }
}