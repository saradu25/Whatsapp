package br.edu.ifsp.dmo.whatsapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.dmo.whatsapp.databinding.ActivityCadastroBinding
import br.edu.ifsp.dmo.whatsapp.databinding.ActivityLoginBinding

class CadastroActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityCadastroBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializarToolbar()

    }

    private fun inicializarToolbar() {
        val toolbar = binding.includeToolbar.tbPrincipal
        setSupportActionBar(toolbar)
        supportActionBar?.apply{
            title = "Faça o seu cadastro"
            setDisplayHomeAsUpEnabled(true)
        }
    }
}