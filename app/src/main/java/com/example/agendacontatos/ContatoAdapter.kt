package com.example.agendacontatos

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class ContatoAdapter(private var contato: ArrayList<Contato>, private var context: Context)
    : RecyclerView.Adapter<ContatoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val delete_button = itemView.findViewById<ImageView>(R.id.img_delete)
        val edit_button = itemView.findViewById<ImageView>(R.id.img_edit)
        fun bind(contato: Contato) {
            itemView.findViewById<TextView>(R.id.txt_nome).text = contato.nome
            itemView.findViewById<TextView>(R.id.txt_fone).text = contato.fone
            itemView.findViewById<TextView>(R.id.txt_email).text = contato.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoAdapter.ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_contatos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContatoAdapter.ViewHolder, position: Int) {
        val contato = this.contato[position]
        holder.bind(contato)
        holder.delete_button.setOnClickListener{
            AlertDialog.Builder(this.context)
                .setTitle("Deletar ${contato.nome} ?")
                .setPositiveButton("Confirmar",{ _, _-> deleteItem(contato) })
                .setNegativeButton(" Cancelar",{ _, _->})
                .show()
        }
        holder.edit_button.setOnClickListener{
            val intent = android.content.Intent(context, CreateContato::class.java)
            intent.putExtra("id", contato.id.toString())
            context.startActivity(intent)
            //Toast.makeText(context, "${contato.id}", Toast.LENGTH_SHORT).show()

        }
    }
    fun deleteItem(item: Contato){
        this.contato.remove(item)
        var db = Room.databaseBuilder(
            context, ContatoDatabase::class.java,
            "ContatoDatabase.db"
        ).allowMainThreadQueries().build()
        val dao = db.ContatoDao()
        dao.deleteContato(item)
        Toast.makeText(context, "Removida com sucesso",
            Toast.LENGTH_SHORT).show()
        this.notifyDataSetChanged()
    }

    // fun update checkList
    fun atualizaContato(contato: Contato){
        var db = Room.databaseBuilder(
            context, ContatoDatabase::class.java,
            "ContatoDatabase.db"
        ).allowMainThreadQueries().build()
        val dao = db.ContatoDao()
        dao.updateContato(contato)
        Toast.makeText(context, "Atualizado com sucesso",
            Toast.LENGTH_SHORT).show()
        this.notifyDataSetChanged()
    }



    override fun getItemCount(): Int {
        return this.contato.size
    }
}