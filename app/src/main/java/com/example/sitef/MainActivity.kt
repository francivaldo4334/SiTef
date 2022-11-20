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