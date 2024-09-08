package br.edu.ifsp.dmo.whatsapp.utils

import android.app.Activity
import android.widget.Toast

fun Activity.exibirMensagem(mensagem : String){

    Toast.makeText(
        this,
        mensagem,
        android.widget.Toast.LENGTH_LONG
    ).show()

}