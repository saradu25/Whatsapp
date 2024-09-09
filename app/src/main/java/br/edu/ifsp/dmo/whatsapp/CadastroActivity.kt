package br.edu.ifsp.dmo.whatsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.dmo.whatsapp.databinding.ActivityCadastroBinding
import br.edu.ifsp.dmo.whatsapp.databinding.ActivityLoginBinding
import br.edu.ifsp.dmo.whatsapp.model.Usuario
import br.edu.ifsp.dmo.whatsapp.utils.exibirMensagem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class CadastroActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityCadastroBinding.inflate(layoutInflater)
    }

    private lateinit var nome: String
    private lateinit var email: String
    private lateinit var senha: String


    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }

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
                cadastrarUsuario(nome, email, senha)

            }
        }
    }

    private fun cadastrarUsuario(nome: String, email: String, senha: String) {
        firebaseAuth.createUserWithEmailAndPassword(
            email, senha
        ).addOnCompleteListener{ resultado ->
            if(resultado.isSuccessful){

                //Salvar dados firestore: id, nome, email, foto
                val idUsuario = resultado.result.user?.uid
                if( idUsuario != null ){
                    val usuario = Usuario(
                        idUsuario, nome, email
                    )
                    salvarUsuarioFirestore(usuario)
                }

            }
        }.addOnFailureListener{ erro ->
            try{
                throw erro
            }catch(erroSenhaFraca: FirebaseAuthWeakPasswordException){
                erroSenhaFraca.printStackTrace()
                exibirMensagem("Senha fraca, digite uma mais forte usando letras, numeros e caracteres especiais.")
            }catch (erroUsuarioExistente: FirebaseAuthUserCollisionException){
                erroUsuarioExistente.printStackTrace()
                exibirMensagem("E-mail já pertence a outro usuario!")
            }catch(erroCredenciaisInvalidas: FirebaseAuthInvalidCredentialsException){
                erroCredenciaisInvalidas.printStackTrace()
                exibirMensagem("E-mail inváido, digite um outro e-mail!")
            }
        }
    }

    private fun salvarUsuarioFirestore(usuario: Usuario) {
        firestore
            .collection("usuarios")
            .document(usuario.id)
            .set(usuario)
            .addOnSuccessListener {
                exibirMensagem("Cadastro efetuado com sucesso!")

                startActivity(
                    Intent(applicationContext,MainActivity::class.java)
                )

            }.addOnFailureListener{
                exibirMensagem("Erro ao fazer seu cadastro.")
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