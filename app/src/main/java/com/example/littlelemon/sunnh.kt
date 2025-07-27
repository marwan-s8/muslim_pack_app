package com.example.littlelemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.ui.unit.sp


@Composable
fun SunnahScreen() {
    var hadith by remember { mutableStateOf<HadithResponse?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var count by remember { mutableStateOf(3) }
    LaunchedEffect(count) {
        isLoading = true
        try {
            hadith = SunnahApi.getOneHadith(count)
        } catch (e: Exception) {
            error = e.message ?: "Failed to load Hadith"
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (Modifier.fillMaxSize()){
            when {
                isLoading -> CircularProgressIndicator(color = Color(0xFF0F9D58))
                error != null -> Text("Error: $error", color = Color.Red)
                hadith != null -> {HadithContent(hadith!!)
                    Button(onClick = {count++
                                     }, colors = ButtonDefaults.buttonColors(Color(0xFF0F9D58))
                             , modifier = Modifier.size(160.dp,80.dp).align(alignment = Alignment.BottomCenter).offset(0.dp,-20.dp))
                    {
                        Text("التالي")
                    }}
                else -> Text("No Hadith loaded")
            }
        }
    }
}

@Composable
fun HadithContent(hadith: HadithResponse) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = hadith.data.contents.arab,
            style = MaterialTheme.typography.bodyLarge, fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Hadith #${hadith.data.contents.number}",
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
    }
}

object SunnahApi {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun getOneHadith(hadithNum: Int): HadithResponse {
        return client.get("https://api.hadith.gading.dev/books/muslim/$hadithNum").body()
    }
}

// Correct data classes matching the API response
data class HadithResponse(
    val data: HadithData
)

data class HadithData(
    val contents: HadithContents
)

data class HadithContents(
    val number: Int,
    val arab: String
)