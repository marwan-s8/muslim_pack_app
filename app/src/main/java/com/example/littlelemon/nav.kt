package com.example.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.Home
import com.example.littlelemon.mainscreen
import com.example.littlelemon.quran
import com.example.littlelemon.sunnah

@Composable
fun navigation (context: Context){
   val navcon= rememberNavController()

    NavHost(navcon, startDestination = Home.route){
        composable(Home.route){
            mainscreen(navcon)
        }
        composable(quran.route){
            QuranScreen()
        }
        composable(sunnah.route){
            SunnahScreen()
        }
        composable(azkar.route){
            AdhkarApp(context)
        }
    }

}