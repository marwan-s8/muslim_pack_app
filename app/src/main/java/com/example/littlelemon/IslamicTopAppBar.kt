import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PureTransparentTopBar(
    title: String,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // Adjust padding as needed
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 1. Back Button
        IconButton(onClick = onBackClick) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Default.Menu,
                contentDescription = "Back",
                tint = Color.White // Or your preferred color
            )
        }

        // 2. Centered Title
        Text(
            text = title,
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 8.dp), fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.White // Or your preferred color
        )

        // 3. Search Button
        IconButton(onClick = onSearchClick) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.White // Or your preferred color
            )
        }
    }
}