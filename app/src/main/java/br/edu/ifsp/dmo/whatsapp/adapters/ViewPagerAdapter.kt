package br.edu.ifsp.dmo.whatsapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.edu.ifsp.dmo.whatsapp.fragments.ContatosFragment
import br.edu.ifsp.dmo.whatsapp.fragments.ConversasFragment

class ViewPagerAdapter(
    private val abas : List<String>,
    fragmentManager: FragmentManager,
    lifecycle : Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return abas.size //listOf( 0 "CONVERSAS", 1 "CONTATOS")
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            1  -> return ContatosFragment()
        }
        return ConversasFragment()

    }
}