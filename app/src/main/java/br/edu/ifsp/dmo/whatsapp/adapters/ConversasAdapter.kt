package br.edu.ifsp.dmo.whatsapp.adapters

import br.edu.ifsp.dmo.whatsapp.model.Conversa
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import br.edu.ifsp.dmo.whatsapp.R
import br.edu.ifsp.dmo.whatsapp.databinding.ItemConversasBinding
import com.squareup.picasso.Picasso

class ConversasAdapter(
    private val onClick: (Conversa) -> Unit
) : Adapter<ConversasAdapter.ConversasViewHolder>() {

    private var listaConversas = emptyList<Conversa>()

    fun adicionarLista(lista: List<Conversa>) {
        listaConversas = lista
        notifyDataSetChanged()
    }

    inner class ConversasViewHolder(
        private val binding: ItemConversasBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(conversa: Conversa) {
            binding.textConversaNome.text = conversa.nome
            binding.textConversaMensagem.text = conversa.ultimaMensagem

            // Verificar si la URL de la foto no está vacía o es null
            if (!conversa.foto.isNullOrEmpty()) {
                Picasso.get()
                    .load(conversa.foto)
                    .placeholder(R.drawable.placeholder_image) // Imagen temporal mientras carga
                    .error(R.drawable.error_image)              // Imagen en caso de error
                    .into(binding.imageConversaFoto)
            } else {
                // Si la URL de la foto está vacía o es null, mostrar una imagen por defecto
                binding.imageConversaFoto.setImageResource(R.drawable.placeholder_image)
            }

            // Evento de clique
            binding.clItemConversa.setOnClickListener {
                onClick(conversa)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversasViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = ItemConversasBinding.inflate(inflater, parent, false)
        return ConversasViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ConversasViewHolder, position: Int) {
        val conversa = listaConversas[position]
        holder.bind(conversa)
    }

    override fun getItemCount(): Int {
        return listaConversas.size
    }
}
