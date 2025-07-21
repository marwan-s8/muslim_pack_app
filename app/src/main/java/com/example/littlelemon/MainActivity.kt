package com.example.littlelemon

import PureTransparentTopBar
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Colors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            LittleLemonTheme {
                val systemUiController = rememberSystemUiController()

                systemUiController.setStatusBarColor(
                    color = Color(0xFF0F9D58), // any color you want
                    darkIcons = true // use true for black icons, false for white
                )
                var context= LocalContext.current
         app(context )
            }
        }
    }
}

@Composable
fun app(context: Context){
    Column (modifier = Modifier.fillMaxWidth()){
        Box(
            modifier = Modifier
                .fillMaxWidth().height(80.dp)
                .background(Color(0xFF0F9D58))
        ) {

            PureTransparentTopBar(
                title = "إسلامي",
                onBackClick = { /* Handle back */ },
                onSearchClick = { /* Handle search */ }
            )
        }
        Spacer(Modifier.width(20.dp))
        navigation(context)
    }

}

@SuppressLint("ResourceAsColor")
@Composable
fun mainscreen(navController: NavController){

    Column (modifier = Modifier.fillMaxSize()) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(15.dp)
                ,contentAlignment = Alignment.Center
            ) {
                Box(  modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(190.dp)
                    .align(Alignment.Center)) {
                    Image(painter=painterResource(id=R.drawable.back),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                        , modifier = Modifier.matchParentSize().clip(RoundedCornerShape(16.dp)))

                   Row (Modifier.padding(10.dp).fillMaxWidth(),
                       horizontalArrangement = Arrangement.SpaceAround ){
                       Column {
                           Text("اخر قرأة",color=Color.White)
                           Spacer(Modifier.height(15.dp))
                           Button(onClick = {
                               navController.navigate(quran.route)
                           }, colors = ButtonDefaults.buttonColors(Color.White))
                           {
                               Text(" تابع القرأة", color = Color.Black)
                           }
                       }
                       Icon(painterResource(id=R.drawable.quran1),
                           contentDescription = "",
                           Modifier.size(50.dp), tint = Color.White
                       )
                   }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Button(
                        onClick = {
                            navController.navigate(quran.route)
                        },
                        modifier = Modifier.padding(5.dp).height(100.dp)
                            .fillMaxWidth(0.4.toFloat()),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                        border = BorderStroke(10.dp, color = Color.Transparent)
                    ) {
                        Text(text = "القران", textAlign = TextAlign.Left, fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(30.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.quran), 
                            contentDescription = "quran",
                            modifier = Modifier.size(36.dp), tint = Color.Unspecified
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(
                        onClick = {
                            navController.navigate(sunnah.route)
                        },
                        modifier = Modifier.padding(5.dp).height(120.dp)
                            .fillMaxWidth(0.7.toFloat()),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                        border = BorderStroke(10.dp, color = Color.Transparent)
                    ) {
                            Text(text = "حديث", textAlign = TextAlign.Start, fontSize = 20.sp,
                                modifier=Modifier.weight(1f))
                                    Spacer(Modifier.width(10.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.rub_el_hizb), // Your custom icon
                                contentDescription = "hadith",
                                modifier = Modifier.size(36.dp).fillMaxWidth(.35f), tint = Color.Unspecified
                            )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            navController.navigate(azkar.route)
                        },
                        modifier = Modifier.padding(5.dp).height(120.dp).fillMaxWidth(0.4.toFloat())
                            .offset(y = (-20).dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                        border = BorderStroke(10.dp, color = Color.Transparent)
                    ) {
                        Text(text = "اذكار", textAlign = TextAlign.Start, fontSize = 20.sp, modifier = Modifier.fillMaxWidth(.6f))
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.open_hands), // Your custom icon
                            contentDescription = "azkar",
                            modifier = Modifier.size(36.dp).fillMaxWidth(.38f), tint = Color.Unspecified
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(
                        onClick = {
                            navController.navigate(sebha.route)
                        },
                        modifier = Modifier.padding(5.dp).height(100.dp)
                            .fillMaxWidth(0.7.toFloat()),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                        border = BorderStroke(10.dp, color = Color.Transparent)
                    ) {
                        Text(text = "السبحة", textAlign = TextAlign.Left, fontSize = 20.sp,
                           modifier = Modifier.fillMaxWidth(.6f) )
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.tasbih),
                            contentDescription = "sebha",
                            modifier = Modifier.size(50.dp).fillMaxWidth(0.4f), tint = Color.Unspecified
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {

                    }, modifier = Modifier.fillMaxWidth().padding(5.dp).height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                    border = BorderStroke(2.dp, color = Color(color = R.color.teal_200))
                ) {
                    Text(text = "الصلاة", textAlign = TextAlign.Start, fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(30.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.islamic), // Your custom icon
                        contentDescription = "praying",
                        modifier = Modifier.size(36.dp), tint = Color.Unspecified
                    )
                }
            }



    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LittleLemonTheme {
        sebhaScreen()
    }
}
