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

    private lateinit var nome: String
    private lateinit var email: String
    private lateinit var senha: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializarToolbar()
        inicializarEventosClique()
    }

    private fun inicializarEventosClique() {
        binding.btnCadastrar.setOnClickListener{
            if(validarCampos()){
                //criar usuario

            }
        }
    }

    private fun validarCampos(): Boolean {

        nome = binding.editNome.text.toString()
        email = binding.editEmail.text.toString()
        senha =  binding.editSenha.text.toString()

        if (nome.isNotEmpty()){
            binding.textInputLayoutNome.error = null
            if(email.isNotEmpty()){
                binding.textInputLayoutEmail.error = null
                if(senha.isNotEmpty()){
                    binding.textInputLayoutSenha.error = null
                    return true
                }else {
                    binding.textInputLayoutSenha.error = "Preencha a sua senha!"
                    return false
                }
            }else{
                binding.textInputLayoutEmail.error = "Preencha o seu email!"
                return false
            }
        } else{
            binding.textInputLayoutNome.error = "Preencha o seu nome!"
            return false
        }
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