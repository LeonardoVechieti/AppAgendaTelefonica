package com.example.agendacontatos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.room.Room
import com.example.agendacontatos.databinding.ActivityCreateContatoBinding

class CreateContato : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityCreateContatoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateContatoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener(this)
        binding.buttonExit.setOnClickListener(this)
        //remove action bar
        supportActionBar!!.hide()


    }
    fun CreateOrEdit(){
        //pega id do contato
        val id = intent.getStringExtra("id")
        //Toast.makeText(this, "Id: "+ id.toString(), Toast.LENGTH_SHORT).show()
        if (id == null){
            binding.button.text = "Cadastrar"
        } else {
            binding.button.text = "Salvar"
            //pega o banco de dados
            val db = Room.databaseBuilder(
                this,
                ContatoDatabase::class.java,
                "ContatoDatabase.db"
            ).allowMainThreadQueries().build()
            val contatoDao = db.ContatoDao()
            val contato = contatoDao.buscaContatoPorId(id.toString())
            binding.textNome.setText(contato.nome)
            binding.textFone.setText(contato.fone)
            binding.textEmail.setText(contato.email)
        }
    }
    fun CriaContato(){
        if (binding.textNome.text.toString()
                .isNotEmpty() && binding.textFone.text.toString()
                .isNotEmpty() && binding.textEmail.text.toString().isNotEmpty()
        ) {
            //salva o contato
            val contato = Contato(
                nome = binding.textNome.text.toString(),
                fone = binding.textFone.text.toString(),
                email = binding.textEmail.text.toString()
            )
            val db = Room.databaseBuilder(
                this,
                ContatoDatabase::class.java,
                "ContatoDatabase.db"
            ).allowMainThreadQueries().build()
            val contatoDao = db.ContatoDao()
            contatoDao.insereContato(contato)
            Toast.makeText(this, "Contato cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            if (binding.textNome.text.toString().isEmpty()) {
                binding.textNome.error = "Campo obrigatório!"
            }
            if (binding.textFone.text.toString().isEmpty()) {
                binding.textFone.error = "Campo obrigatório!"
            }
            if (binding.textEmail.text.toString().isEmpty()) {
                binding.textEmail.error = "Campo obrigatório!"
            }
        }
    }

    fun AtualizaContato(){
        if (binding.textNome.text.toString()
                .isNotEmpty() && binding.textFone.text.toString()
                .isNotEmpty() && binding.textEmail.text.toString().isNotEmpty()
        ) {
            //salva o contato
            val id = intent.getStringExtra("id")
            val nome = binding.textNome.text.toString()
            val fone = binding.textFone.text.toString()
            val email = binding.textEmail.text.toString()

            val db = Room.databaseBuilder(
                this,
                ContatoDatabase::class.java,
                "ContatoDatabase.db"
            ).allowMainThreadQueries().build()
            val contatoDao = db.ContatoDao()
            contatoDao.updateContatoPorId(id.toString() ,nome, fone, email)
            Toast.makeText(this, "Contato alterado com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            if (binding.textNome.text.toString().isEmpty()) {
                binding.textNome.error = "Campo obrigatório!"
            }
            if (binding.textFone.text.toString().isEmpty()) {
                binding.textFone.error = "Campo obrigatório!"
            }
            if (binding.textEmail.text.toString().isEmpty()) {
                binding.textEmail.error = "Campo obrigatório!"
            }
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.button.id -> {
                //pega id
                val id = intent.getStringExtra("id")
                if(id != null){
                    AtualizaContato()
                } else {
                    CriaContato()
                }

            }
        }
        when(v.id) {
            binding.buttonExit.id -> {
                finish()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        CreateOrEdit()
    }
}