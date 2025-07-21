package com.example.littlelemon


import android.content.Context
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun sebhaScreen(){
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("sebha_prefs", Context.MODE_PRIVATE) }


    var counter by remember {
        mutableStateOf(sharedPrefs.getInt("tasbeeh_counter", 0))
    }


    fun saveCounter(value: Int) {
        sharedPrefs.edit().putInt("tasbeeh_counter", value).apply()}

        Column {
       Box (modifier = Modifier.fillMaxSize()) {
           Button(onClick = {
               counter++
               saveCounter(counter)
           }, modifier = Modifier.padding(15.dp).fillMaxWidth().height(300.dp).align(Alignment.Center),
               elevation = ButtonDefaults.elevation(
                   defaultElevation = 4.dp,
                   pressedElevation = 16.dp
               ),
               shape = CircleShape,
               colors = ButtonDefaults.buttonColors(Color(0xFF0F9D58))
           )
           {
               Text("${counter} : عدد التسبيحات", fontSize = 20.sp)
           }

           Button(onClick = {
               counter=0
               saveCounter(counter)
           },
               modifier = Modifier.size(160.dp,80.dp).align(Alignment.BottomCenter).offset(0.dp,-20.dp),
               colors = ButtonDefaults.buttonColors(Color.Black)) {
               Text("البداء من جديد ", color = Color.White)
           }

       }
   }



}

