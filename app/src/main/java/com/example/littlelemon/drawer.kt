package com.example.littlelemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun drawer(scaffoldState: ScaffoldState,scope:CoroutineScope){
    var tog by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()){
        Row (modifier = Modifier.fillMaxWidth(0.9f).padding(10.dp), horizontalArrangement = Arrangement.Center) {
            Text("dark mode")
            Spacer(modifier = Modifier.width(16.dp))
            Switch(checked = tog, onCheckedChange ={tog = it} )
        }

        IconButton(onClick = {scope.launch { scaffoldState.drawerState.close() }}
            ,Modifier.padding(10.dp).fillMaxWidth(.9f)
        ) {
            Icon(Icons.Filled.ExitToApp, contentDescription = "")
        }
    }

}