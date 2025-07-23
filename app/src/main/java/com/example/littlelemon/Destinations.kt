package com.example.littlelemon

import android.graphics.drawable.Icon
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.res.painterResource

interface Destinations {
    val route: String
    val title: String
    val iconResId: Int
}


object Home : Destinations {
    override val route = "Home"
    override val title = "Home"
    override val iconResId = R.drawable.home
}


object quran : Destinations {
    override val route = "quran"
    override val title = "quran"
    override val iconResId = R.drawable.book
}

object sunnah : Destinations{
    override val route = "sunnah"
    override val title = "sunnah"
    override val iconResId = R.drawable.muhammad
}

object azkar : Destinations{
    override val route = "azkar"
    override val title = "Azkar"
    override val iconResId = R.drawable.ramadan
}

object sebha : Destinations{
    override val route = "sebha"
    override val title = "sebha"
    override val iconResId = R.drawable.tasbih1
}