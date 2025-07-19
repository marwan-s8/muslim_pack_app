package com.example.littlelemon

interface Destinations {
    val route: String
}

object Home : Destinations {
    override val route = "Home"
}

object quran : Destinations {
    override val route = "quran"
}

object sunnah : Destinations{
    override val route = "sunnah"
}

object azkar : Destinations{
    override val route = "azkar"
}