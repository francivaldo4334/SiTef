package com.example.sitef

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.softwareexpress.sitef.android.CliSiTef
import br.com.softwareexpress.sitef.android.ICliSiTefListener
import com.example.sitef.pages.Configure
import com.example.sitef.pages.Home
import com.example.sitef.pages.Operation
import com.example.sitef.ui.theme.SiTefTheme

class MainActivity : ComponentActivity(),ICliSiTefListener {
    private var sitef:CliSiTef? = null
    companion object{
        lateinit var navController:NavHostController
        var response by mutableStateOf("")
        val TAG = "TRANSACTION"
        val route_Home = "HOME"
        val route_Operation = "OPERATION"
        val route_Configure = "CONFIGURE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(sitef == null){
            sitef = CliSiTef(applicationContext)
            configureSiTef()
        }
        sitef!!.setActivity(this)
        setContent {
            navController = rememberNavController()
            SiTefTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(navController = navController, startDestination = route_Home){
                        composable(route_Home){ Home()}
                        composable(route_Operation){ Operation()}
                        composable(route_Configure){ Configure()}
                    }
                }
            }
        }
    }

    private fun configureSiTef() {
        val log = when(sitef!!.configure (
            "192.168.0.1",
            "00000000",
            "SE000001",
            "[ParmsClient=1=31406434895111;2=12523654185985;TrataPontoFlutuante=1;TipoComunicacaoExterna=SSL]"
        )){
            0 -> "N??o ocorreu erro"
            1 -> "Endere??o IP inv??lido ou n??o resolvido"
            2 -> "C??digo da loja inv??lido"
            3 -> "C??digo de terminal inv??lido"
            6 -> "Erro na inicializa????o do Tcp/Ip"
            7 -> "Falta de mem??ria"
            8 -> "N??o encontrou a CliSiTef ou ela est?? com problemas"
            9 -> "Configura????o de servidores SiTef foi excedida."
            10 -> "Erro de acesso na pasta CliSiTef (poss??vel falta de permiss??o para escrita)"
            11 -> "Dados inv??lidos passados pela automa????o."
            12 -> "Modo seguro n??o ativo (poss??vel falta de configura????o no servidor SiTef do arquivo .cha)."
            13 -> "Caminho DLL inv??lido (o caminho completo das bibliotecas est?? muito grande)."
            else -> "NULL"
        }
        Log.i(TAG,log)
        Toast.makeText(this,log,Toast.LENGTH_LONG).show()
    }

    override fun onData(currentStage: Int, command: Int, fieldId: Int, minLength: Int, maxLength: Int, input: ByteArray?) {
        val log = "onData($currentStage,$command,$fieldId,$minLength,$maxLength,$input)"
        Log.i(TAG,log)
        Toast.makeText(this,log,Toast.LENGTH_LONG).show()
        response = String(input!!)
    }

    override fun onTransactionResult(currentStage: Int, resultCode: Int) {
        val log = "onTransationResult($currentStage,$resultCode)"
        Log.i(TAG,log)
        Toast.makeText(this,log,Toast.LENGTH_LONG).show()
        if (currentStage == 1 && resultCode == 0) { // Confirm the transaction
            try {
                sitef!!.finishTransaction(1)
            } catch (e: Exception) {}
        } else {
            if (resultCode == 0) {
            } else {
            }
        }

    }
}