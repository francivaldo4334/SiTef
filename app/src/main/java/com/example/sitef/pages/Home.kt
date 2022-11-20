package com.example.sitef.pages

import android.content.Context
import androidx.compose.foundation.layout.*
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
fun Home(){
    val sitef = CliSiTef.getInstance()
    val context = LocalContext.current as MainActivity
    var valueTF by remember{
        mutableStateOf("1,00")
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = valueTF, onValueChange = {valueTF = it}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        Button(onClick = {
            val sdf = SimpleDateFormat("yyyyMMDDhhmmss")
            val currentDate = sdf.format(Date())
            val date = currentDate.substring(0,8)
            val time = currentDate.substring(8,14)
            sitef.startTransaction(
                context,
                0,
                valueTF,
                "0",
                date,
                time,
                "Operador",
                ""
            )
            MainActivity.navController.navigate(MainActivity.route_Operation)
        }) {
            Text("Iniciar transacao")
        }
        Button(onClick = {MainActivity.navController.navigate(MainActivity.route_Configure)}) {
            Text("Configurar SiTef")
        }
    }
}