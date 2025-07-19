package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FilterChip
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.material.contentColorFor
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color

// Updated data classes to match your JSON structure
data class Adhkar(
    val أذكار_الصباح: List<Dhikr>,
    val أذكار_المساء: List<Dhikr>,
    val أذكار_بعد_السلام_من_الصلاة_المفروضة: List<Dhikr>,
    val تسابيح: List<Dhikr>,
    val أذكار_النوم: List<Dhikr>,
    val أذكار_الاستيقاظ: List<Dhikr>,
    val أدعية_قرآنية: List<Dhikr>,
    val أدعية_الأنبياء: List<Dhikr>
) {
    // Helper function to get all dhikr from all categories
    fun getAllAzkar(): List<Dhikr> {
        return أذكار_الصباح + أذكار_المساء + أذكار_بعد_السلام_من_الصلاة_المفروضة +
                تسابيح + أذكار_النوم + أذكار_الاستيقاظ + أدعية_قرآنية + أدعية_الأنبياء
    }
}

data class Dhikr(
    val category: String,
    val count: String,
    val description: String,
    val reference: String,
    val content: String
)

// Updated API object
object AzkarApi {

    suspend fun loadAdhkarFromAssets(context: Context): Adhkar {
        val jsonString = context.assets.open("azkar.json")
            .bufferedReader()
            .use { it.readText() }
        return Gson().fromJson(jsonString, Adhkar::class.java)
    }
}

// Updated Composable
@Composable
fun AdhkarApp(context: Context) {
    val adhkarData = remember { mutableStateOf<Adhkar?>(null) }
    val selectedCategory = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        adhkarData.value = AzkarApi.loadAdhkarFromAssets(context)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Category Filter Chips
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf(
                "أذكار الصباح",
                "أذكار المساء",
                "تسابيح",
                "أذكار النوم",
                "أذكار الاستيقاظ"
                // Add other categories as needed
            ).forEach { category ->
                FilterChip(
                    selected = selectedCategory.value == category,
                    onClick = {
                        selectedCategory.value = if (selectedCategory.value == category) null else category
                    },
                    label = { Text(category) },
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

        // Filtered Content
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(
                adhkarData.value?.getAllAzkar()?.filter {
                    selectedCategory.value == null || it.category == selectedCategory.value
                } ?: emptyList()
            ) { dhikr ->
                DhikrCard(dhikr)
            }
        }
    }
}

@Composable
fun DhikrCard(dhikr: Dhikr) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            backgroundColor = Color.LightGray
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(dhikr.content, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text("الترتيب: ${dhikr.count}", style = MaterialTheme.typography.bodySmall)
            if (dhikr.reference.isNotEmpty()) {
                Text("المرجع: ${dhikr.reference}", style = MaterialTheme.typography.labelSmall)
            }
            if (dhikr.description.isNotEmpty()) {
                Text("الفضيلة: ${dhikr.description}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Black)
            }
        }
    }
}