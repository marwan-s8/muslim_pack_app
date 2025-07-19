package com.example.littlelemon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.datatransport.runtime.logging.Logging
import com.google.gson.annotations.SerializedName
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.*
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.logging.Logger


@Composable
fun QuranScreen() {
    val scope = rememberCoroutineScope()
    var surahs by remember { mutableStateOf<List<Surah>>(emptyList()) }
    var currentSurah by remember { mutableStateOf<SurahContent?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        isLoading = true
        try {
            surahs = QuranApi.getAllSurahs()
        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }
    }

    if (error != null) {
        ErrorScreen(error!!) {
            error = null
            scope.launch {
                isLoading = true
                surahs = QuranApi.getAllSurahs()
                isLoading = false
            }
        }
    } else if (isLoading) {
        FullScreenLoading()
    } else if (currentSurah != null) {
        SurahDetailScreen(
            surah = currentSurah!!,
            onBack = { currentSurah = null }
        )
    } else {
        SurahListScreen(
            surahs = surahs,
            onSurahSelected = { surahNumber ->
                scope.launch {
                    isLoading = true
                    try {
                        currentSurah = QuranApi.getSurahContent(surahNumber)
                    } catch (e: Exception) {
                        error = e.message
                    } finally {
                        isLoading = false
                    }
                }
            }
        )
    }
}

@Composable
fun SurahListScreen(
    surahs: List<Surah>,
    onSurahSelected: (Int) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(surahs) { surah ->
            SurahItem(
                surah = surah,
                onClick = { onSurahSelected(surah.number) }
            )
        }
    }
}

@Composable
fun SurahItem(surah: Surah, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${surah.number}. ${surah.name}",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = surah.englishName,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Verses: ${surah.numberOfAyahs} | ${surah.revelationType}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahDetailScreen(surah: SurahContent, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${surah.number}. ${surah.name}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(surah.ayahs) { ayah ->
                AyahItem(ayah = ayah)
            }
        }
    }
}

@Composable
fun AyahItem(ayah: Ayah) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = ayah.text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 24.sp,
                textAlign = TextAlign.Right
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Ayah ${ayah.numberInSurah} (Juz ${ayah.juz}, Page ${ayah.page})",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.align(Alignment.End)
        )

    }
}

@Composable
fun FullScreenLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Error: $message",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

object QuranApi {
    private val client = HttpClient(Android) {
        // 1. Install GSON for JSON serialization
        install(ContentNegotiation) {
            gson {
                disableHtmlEscaping()
            }
        }


    }

    suspend fun getAllSurahs(): List<Surah> {
        return client.get("https://api.alquran.cloud/v1/surah").body<QuranResponse>().data
    }


    suspend fun getSurahContent(surahNumber: Int): SurahContent {
        return client.get("https://api.alquran.cloud/v1/surah/$surahNumber/ar.alafasy")
            .body<SurahContentResponse>().data
    }

}
data class QuranResponse(
    @SerializedName("data") val data: List<Surah>
)

data class Surah(
    @SerializedName("number") val number: Int,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("englishNameTranslation") val englishNameTranslation: String,
    @SerializedName("numberOfAyahs") val numberOfAyahs: Int,
    @SerializedName("revelationType") val revelationType: String
)

data class SurahContentResponse(
    @SerializedName("data") val data: SurahContent
)

data class SurahContent(
    @SerializedName("number") val number: Int,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("ayahs") val ayahs: List<Ayah>
)

data class Ayah(
    @SerializedName("number") val number: Int,
    @SerializedName("text") val text: String,
    @SerializedName("numberInSurah") val numberInSurah: Int,
    @SerializedName("juz") val juz: Int,
    @SerializedName("manzil") val manzil: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("ruku") val ruku: Int,
    @SerializedName("hizbQuarter") val hizbQuarter: Int,
    @SerializedName("sajda") val sajda: Boolean
)