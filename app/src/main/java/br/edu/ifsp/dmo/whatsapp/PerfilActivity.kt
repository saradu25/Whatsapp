package br.edu.ifsp.dmo.whatsapp

import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import br.edu.ifsp.dmo.whatsapp.databinding.ActivityPerfilBinding
import br.edu.ifsp.dmo.whatsapp.databinding.ActivityCadastroBinding

class PerfilActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPerfilBinding.inflate( layoutInflater )
    }
    private var temPermissaoCamera = false
    private var temPermissaoGaleria = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( binding.root )
        inicializarToolbar()
        solicitarPermissoes()
    }

    private fun solicitarPermissoes() {

        //Verifico se usuário já tem permissão
        temPermissaoCamera = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        temPermissaoGaleria = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED

        //PERMISSÕES NEGADAS
        val listaPermissoesNegadas = mutableListOf<String>()
        if( !temPermissaoCamera )
            listaPermissoesNegadas.add( Manifest.permission.CAMERA )
        if( !temPermissaoGaleria )
            listaPermissoesNegadas.add( Manifest.permission.READ_MEDIA_IMAGES )

        if( listaPermissoesNegadas.isNotEmpty() ){

            //Solicitar multiplas permissões
            val gerenciadorPermissoes = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ){ permissoes ->

                temPermissaoCamera = permissoes[Manifest.permission.CAMERA]
                    ?: temPermissaoCamera

                temPermissaoGaleria = permissoes[Manifest.permission.READ_MEDIA_IMAGES]
                    ?: temPermissaoGaleria

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