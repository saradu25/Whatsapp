package br.edu.ifsp.dmo.whatsapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.dmo.whatsapp.databinding.ActivityLoginBinding
import br.edu.ifsp.dmo.whatsapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import br.edu.ifsp.dmo.whatsapp.utils.exibirMensagem
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private lateinit var email: String
    private lateinit var senha: String


    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inicializarEventosClique()
        //firebaseAuth.signOut()


    }

    override fun onStart(){
        super.onStart()
        verificarUsuarioLogado()
    }

    private fun verificarUsuarioLogado() {
        val usuarioAtual = firebaseAuth.currentUser
        if(usuarioAtual != null){
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }
    }


    private fun inicializarEventosClique() {
        binding.textCadastro.setOnClickListener {
            startActivity(
                Intent(this, CadastroActivity::class.java)
            )
        }
        binding.btnLogar.setOnClickListener {
            if (validarCampos()) {
                logarUsuario()
            }
        }
    }

    private fun validarCampos(): Boolean {
            email = binding.editLoginEmail.text.toString()
            senha =  binding.editLoginSenha.text.toString()

            if(email.isNotEmpty()){
                binding.textInputLayoutLoginEmail.error = null
                if(senha.isNotEmpty()){
                    binding.textInputLayoutLoginSenha.error = null
                    return true
                }else {
                    binding.textInputLayoutLoginSenha.error = "Preencha a sua senha!"
                    return false
                }
            }else{
                binding.textInputLayoutLoginEmail.error = "Preencha o seu email!"
                return false
            }

    }

    private fun logarUsuario() {

        firebaseAuth.signInWithEmailAndPassword(
            email, senha
        ).addOnSuccessListener {
            exibirMensagem("Logado com sucesso")
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }.addOnFailureListener { erro ->

            try {
                throw erro
            } catch (erroUsuarioInvalido: FirebaseAuthWeakPasswordException) {
                erroUsuarioInvalido.printStackTrace()
                exibirMensagem("Erro: E-mail não cadastrado.")
            } catch (erroCredenciaisInvalidas: FirebaseAuthInvalidCredentialsException) {
                erroCredenciaisInvalidas.printStackTrace()
                exibirMensagem("Erro: E-mail ou senha incorretos.")
            }
        }

    }

}

