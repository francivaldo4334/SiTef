package com.example.sitef.pages

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
fun Operation(){
    val sitef = CliSiTef.getInstance()
    val context = LocalContext.current as MainActivity
    var valueTF by remember{
        mutableStateOf("")
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = MainActivity.response)
        TextField(value = valueTF, onValueChange = {valueTF = it}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        Button(onClick = {
            sitef.continueTransaction(valueTF)
        }) {
            Text("Continuar operacao")
        }
    }
}