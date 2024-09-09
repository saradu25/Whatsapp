package br.edu.ifsp.dmo.whatsapp.adapters

import br.edu.ifsp.dmo.whatsapp.model.Conversa
import br.edu.ifsp.dmo.whatsapp.model.Usuario
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import br.edu.ifsp.dmo.whatsapp.databinding.ItemConversasBinding
import br.edu.ifsp.dmo.whatsapp.databinding.ItemContatosBinding
import com.squareup.picasso.Picasso

class ConversasAdapter(
    private val onClick: (Conversa) -> Unit
) : Adapter<ConversasAdapter.ConversasViewHolder>() {

    private var listaConversas = emptyList<Conversa>()
    fun adicionarLista( lista: List<Conversa> ){
        listaConversas = lista
        notifyDataSetChanged()
    }

    inner class ConversasViewHolder(
        private val binding: ItemConversasBinding
    ) : RecyclerView.ViewHolder( binding.root ){

        fun bind( conversa: Conversa ){

            binding.textConversaNome.text = conversa.nome
            binding.textConversaMensagem.text = conversa.ultimaMensagem
            Picasso.get()
                .load( conversa.foto )
                .into( binding.imageConversaFoto )

            //Evento de clique
            binding.clItemConversa.setOnClickListener {
                onClick( conversa )
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversasViewHolder {

        val inflater = LayoutInflater.from( parent.context )
        val itemView = ItemConversasBinding.inflate(
            inflater, parent, false
        )
        return ConversasViewHolder( itemView )

    }

    override fun onBindViewHolder(holder: ConversasViewHolder, position: Int) {
        val conversa = listaConversas[position]
        holder.bind( conversa )
    }

    override fun getItemCount(): Int {
        return listaConversas.size
    }

}
