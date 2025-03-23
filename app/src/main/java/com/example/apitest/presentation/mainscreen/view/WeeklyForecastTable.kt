import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apitest.data.remote.response.DailyForecast
import com.example.apitest.presentation.utility.HorizontalSeparator
import com.example.apitest.utils.Result

@SuppressLint("DefaultLocale")
@Composable
fun WeeklyForecastTable(
    weeklyForecast: List<DailyForecast>
) {
    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(color = Color.White)
            .border(1.dp, color = Color.Gray),
        contentPadding = PaddingValues(0.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(color = Color.White)
            ) {
                Text(
                    text = "Дата",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(2f)
                )
                Text(
                    text = "Описание",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(3f)
                )
                Text(
                    text = "Мин.",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1.5f)
                )
                Text(
                    text = "Макс.",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1.5f)
                )
            }
        }
        items(weeklyForecast) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(2f)
                )
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(3f)
                )
                Text(
                    text = "Min: ${String.format("%.0f", item.tempMin)}°C",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1.5f)
                )
                Text(
                    text = "Max: ${String.format("%.0f", item.tempMax)}°C",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1.5f)
                )
            }
            HorizontalSeparator()
        }
    }
}



@Preview(showBackground = true)
@Composable
fun WeeklyForecastTablePreview() {
    val previewDailyForecastList = listOf(
        DailyForecast(
            date = "2023-10-01",
            description = "Ясно",
            tempMin = 10.0,
            tempMax = 20.0
        ),
        DailyForecast(
            date = "2023-10-02",
            description = "Облачно",
            tempMin = 12.0,
            tempMax = 18.0
        ),
        DailyForecast(
            date = "2023-10-03",
            description = "Дождь",
            tempMin = 8.0,
            tempMax = 15.0
        ),
        DailyForecast(
            date = "2023-10-04",
            description = "Снег",
            tempMin = -5.0,
            tempMax = 2.0
        )
    )
    WeeklyForecastTable(weeklyForecast = previewDailyForecastList)
}
