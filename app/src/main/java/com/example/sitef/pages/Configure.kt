package com.example.sitef.pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.softwareexpress.sitef.android.CliSiTef
import com.example.sitef.MainActivity
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Configure(){
    val sitef = CliSiTef.getInstance()
    val context = LocalContext.current as MainActivity
    var IP by remember{
        mutableStateOf("192.168.0.1")
    }
    var COMERCIO by remember{
        mutableStateOf("00000000")
    }
    var TERMINAL by remember{
        mutableStateOf("SE000001")
    }
    var PARAM by remember{
        mutableStateOf("[ParmsClient=1=31406434895111;2=12523654185985;TrataPontoFlutuante=1;TipoComunicacaoExterna=SSL]")
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = IP, onValueChange = {IP = it})
        TextField(value = COMERCIO, onValueChange = {COMERCIO = it})
        TextField(value = TERMINAL, onValueChange = {TERMINAL = it})
        TextField(value = PARAM, onValueChange = {PARAM = it})
        Button(onClick = {
            val log = when(sitef!!.configure (
                IP,
                COMERCIO,
                TERMINAL,
                PARAM
            )){
                0 -> "Não ocorreu erro"
                1 -> "Endereço IP inválido ou não resolvido"
                2 -> "Código da loja inválido"
                3 -> "Código de terminal inválido"
                6 -> "Erro na inicialização do Tcp/Ip"
                7 -> "Falta de memória"
                8 -> "Não encontrou a CliSiTef ou ela está com problemas"
                9 -> "Configuração de servidores SiTef foi excedida."
                10 -> "Erro de acesso na pasta CliSiTef (possível falta de permissão para escrita)"
                11 -> "Dados inválidos passados pela automação."
                12 -> "Modo seguro não ativo (possível falta de configuração no servidor SiTef do arquivo .cha)."
                13 -> "Caminho DLL inválido (o caminho completo das bibliotecas está muito grande)."
                else -> "NULL"
            }
            Log.i(MainActivity.TAG,log)
            Toast.makeText(context,log, Toast.LENGTH_LONG).show()
        }) {
            Text("Configurar")
        }
    }
}