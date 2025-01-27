package br.edu.fateczl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.fateczl.databinding.ServicosItemBinding
import br.edu.fateczl.model.Servicos

class ServicosAdapter(
    private val context: Context, private val listaServicos: MutableList<Servicos>
) :
    RecyclerView.Adapter<ServicosAdapter.ServicosViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicosViewHolder {
        val itemLista = ServicosItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ServicosViewHolder(itemLista)
    }

    override fun getItemCount() = listaServicos.size

    override fun onBindViewHolder(holder: ServicosViewHolder, position: Int) {
        holder.imgServicos.setImageResource(listaServicos[position].img!!)
        holder.tvServicos.text = listaServicos[position].nome
    }

    inner class ServicosViewHolder(binding: ServicosItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgServicos = binding.imgServicos
        val tvServicos = binding.tvServico
    }

}