package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.Home
import com.example.littlelemon.mainscreen
import com.example.littlelemon.quran
import com.example.littlelemon.sunnah

@Composable
fun navigation (context: Context,navcon: NavHostController){


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
        composable(sebha.route){
            sebhaScreen()
        }
    }

}

@Composable
fun BotNav (navController: NavHostController){

    val selist = listOf<Destinations>(
    quran,
    sunnah,
        Home,
    sebha,
    azkar
)
   // val navBackStackEntry by navController.currentBackStackEntryAsState()
   // val currentRoute = navBackStackEntry?.destination?.route
    //if (currentRoute == Home.route) return       //this for hide navbot while be in home
    val indexsel = rememberSaveable { mutableStateOf(0)  }

    BottomNavigation(modifier = Modifier.height(56.dp)) {
        selist.fastForEachIndexed{index,item ->
        BottomNavigationItem(
            label = { Text(item.title,fontSize = 10.sp,
                modifier = Modifier.padding(top = 2.dp)) },
            icon = { Icon(painterResource(id=item.iconResId), contentDescription = "",modifier = Modifier.size(20.dp)) },
            selected =index==indexsel.value ,
            onClick = {indexsel.value=index
            navController.navigate(selist[index].route){
                popUpTo(Home.route)
                launchSingleTop=true
            }
            }

        )
    }
    }
}