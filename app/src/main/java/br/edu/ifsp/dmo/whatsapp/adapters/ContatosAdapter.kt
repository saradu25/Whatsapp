package br.edu.ifsp.dmo.whatsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.edu.ifsp.dmo.whatsapp.databinding.ItemContatosBinding
import br.edu.ifsp.dmo.whatsapp.model.Usuario
import com.squareup.picasso.Picasso
import br.edu.ifsp.dmo.whatsapp.R

class ContatosAdapter(
    private val onClick: (Usuario) -> Unit
) : Adapter<ContatosAdapter.ContatosViewHolder>() {

    private var listaContatos = emptyList<Usuario>()

    fun adicionarLista(lista: List<Usuario>) {
        listaContatos = lista
        notifyDataSetChanged()
    }

    inner class ContatosViewHolder(
        private val binding: ItemContatosBinding
    ) : ViewHolder(binding.root) {

        fun bind(usuario: Usuario) {

            binding.textContatoNome.text = usuario.nome

            // Verificar se a URL da foto não está vazia ou null
            if (!usuario.foto.isNullOrEmpty()) {
                Picasso.get()
                    .load(usuario.foto)
                    .placeholder(R.drawable.placeholder_image)  // Imagem temporária
                    .error(R.drawable.error_image)  // Imagem para exibir em caso de erro
                    .into(binding.imageContatoFoto)
            } else {
                // Se a URL da foto estiver vazia ou null, carregar a imagem padrão
                binding.imageContatoFoto.setImageResource(R.drawable.placeholder_image)
            }

            // Evento de clique
            binding.clItemContato.setOnClickListener {
                onClick(usuario)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = ItemContatosBinding.inflate(
            inflater, parent, false
        )
        return ContatosViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContatosViewHolder, position: Int) {
        val usuario = listaContatos[position]
        holder.bind(usuario)
    }

    override fun getItemCount(): Int {
        return listaContatos.size
    }
}
