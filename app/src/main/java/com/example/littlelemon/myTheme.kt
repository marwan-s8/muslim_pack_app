package com.example.littlelemon


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.littlelemon.ui.theme.DarkColors
import com.example.littlelemon.ui.theme.LightColors

@Composable
fun myTheme(darkmode:Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit){

    val color= if(darkmode) DarkColors else LightColors
    MaterialTheme  (colorScheme = color,content=content)
}