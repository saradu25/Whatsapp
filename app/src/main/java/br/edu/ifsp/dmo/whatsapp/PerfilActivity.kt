package br.edu.ifsp.dmo.whatsapp

import android.os.Build
import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import br.edu.ifsp.dmo.whatsapp.databinding.ActivityPerfilBinding
import br.edu.ifsp.dmo.whatsapp.databinding.ActivityCadastroBinding
import br.edu.ifsp.dmo.whatsapp.utils.exibirMensagem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class PerfilActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPerfilBinding.inflate( layoutInflater )
    }

    private var temPermissaoCamera = false
    private var temPermissaoGaleria = false

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val storage by lazy {
        FirebaseStorage.getInstance()
    }

    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val gerenciadorGaleria = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){ uri ->
        if( uri != null ){
            binding.imagePerfil.setImageURI( uri )
            uploadImagemStorage(uri)
        }else{
            exibirMensagem("Nenhuma imagem selecionada")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( binding.root )
        inicializarToolbar()
        solicitarPermissoes()
        inicializarEventosClique()
    }

    override fun onStart() {
        super.onStart()
        recuperarDadosIniciaisUsuarios()
    }

    private fun recuperarDadosIniciaisUsuarios() {
        val idUsuario = firebaseAuth.currentUser?.uid
        if( idUsuario != null ){

            firestore
                .collection("usuarios")
                .document( idUsuario )
                .get()
                .addOnSuccessListener { documentSnapshot ->

                    val dadosUsuarios = documentSnapshot.data
                    if( dadosUsuarios != null ){

                        val nome = dadosUsuarios["nome"] as String
                        val foto = dadosUsuarios["foto"] as String

                        binding.editNomePerfil.setText( nome )
                        if( foto.isNotEmpty() ){
                            Picasso.get()
                                .load( foto )
                                .into( binding.imagePerfil )
                        }

                    }

                }

        }

    }


    private fun uploadImagemStorage(uri: Uri) {

        val idUsuario = firebaseAuth.currentUser?.uid
        if(idUsuario != null) {

            storage
                .getReference("fotos")
                .child("usuarios")
                .child("id")
                .child("perfil.jpg")
                .putFile(uri)
                .addOnSuccessListener { task ->

                    exibirMensagem("Upload de imagem com sucesso!")
                    task.metadata
                        ?.reference
                        ?.downloadUrl
                        ?.addOnSuccessListener { url ->
                            val dados = mapOf(
                                "foto" to url.toString()
                            )
                            atualizarDadosPerfil(idUsuario, dados)
                        }
                }.addOnFailureListener{
                    exibirMensagem("ERROR: Upload de imagem falhou.")
                }
        }
    }

    private fun atualizarDadosPerfil(idUsuario: String, dados: Map<String, String>) {

        firestore
            .collection("usuarios")
            .document( idUsuario )
            .update( dados )
            .addOnSuccessListener {
                exibirMensagem("Sucesso ao atualizar perfil!")
            }
            .addOnFailureListener {
                exibirMensagem("Erro ao atualizar perfil")
            }

    }



    private fun inicializarEventosClique() {

        binding.fabSelecionar.setOnClickListener {
            if( temPermissaoGaleria ){
                gerenciadorGaleria.launch("image/*")
            }else{
                exibirMensagem("Não tem permissão para acessar galeria")
                solicitarPermissoes()
            }
        }

        binding.btnAtualizarPerfil.setOnClickListener {

            val nomeUsuario = binding.editNomePerfil.text.toString()
            if( nomeUsuario.isNotEmpty() ){

                val idUsuario = firebaseAuth.currentUser?.uid
                if( idUsuario != null ){
                    val dados = mapOf(
                        "nome" to nomeUsuario
                    )
                    atualizarDadosPerfil( idUsuario, dados )
                }

            }else{
                exibirMensagem("Preencha o nome para atualizar")
            }

        }

    }

    private fun solicitarPermissoes() {

        // Verificar se o usuário já tem permissão para usar a câmera
        temPermissaoCamera = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        // Verificar se o usuário já tem permissão para acessar a galeria, de acordo com a versão da API
        temPermissaoGaleria = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }

        // Lista de permissões negadas
        val listaPermissoesNegadas = mutableListOf<String>()
        if( !temPermissaoCamera )
            listaPermissoesNegadas.add( Manifest.permission.CAMERA )

        // Adicionar a permissão correta de acordo com a versão da API
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if( !temPermissaoGaleria )
                listaPermissoesNegadas.add( Manifest.permission.READ_MEDIA_IMAGES )
        } else {
            if( !temPermissaoGaleria )
                listaPermissoesNegadas.add( Manifest.permission.READ_EXTERNAL_STORAGE )
        }

        if( listaPermissoesNegadas.isNotEmpty() ){

            // Solicitar múltiplas permissões
            val gerenciadorPermissoes = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ){ permissoes ->

                temPermissaoCamera = permissoes[Manifest.permission.CAMERA]
                    ?: temPermissaoCamera

                // Atualizar o status da permissão de acordo com a versão da API
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    temPermissaoGaleria = permissoes[Manifest.permission.READ_MEDIA_IMAGES]
                        ?: temPermissaoGaleria
                } else {
                    temPermissaoGaleria = permissoes[Manifest.permission.READ_EXTERNAL_STORAGE]
                        ?: temPermissaoGaleria
                }

            }
            gerenciadorPermissoes.launch( listaPermissoesNegadas.toTypedArray() )

        }

    }

    private fun inicializarToolbar() {
        val toolbar = binding.includeToolbarPerfil.tbPrincipal
        setSupportActionBar( toolbar )
        supportActionBar?.apply {
            title = "Editar perfil"
            setDisplayHomeAsUpEnabled(true)
        }
    }

}
