package com.baguio.projectverde

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.baguio.projectverde.ui.theme.ProjectVerdeTheme

// DATA MODELS AND HARD-CODED LISTS

val sampleNews = listOf(
    News("New Recycling Station at Burnham Park", "An electronic waste recycling station has been installed.", "2 days ago"),
    News("Citywide Cleanup Drive Scheduled", "Join the cleanup this Saturday at 8:00 AM.", "1 week ago"),
    News("Updated Recycling Guidelines", "New guidelines for plastic sorting are in effect.", "2 weeks ago")
)

val sampleTips = listOf(
    TipItem("Say NO to Plastic Bags", "Always carry a reusable shopping bag, especially when visiting the public market or grocery stores."),
    TipItem("Recycle Soft Plastics", "Clean and dry all plastic packaging (like chip bags and candy wrappers) before placing them in your recycling bin."),
    TipItem("Compost Kitchen Scraps", "Start a small composting pile for fruit and vegetable waste to create natural fertilizer for your plants."),
    TipItem("Check E-Waste Schedules", "Do not mix old batteries, chargers, or broken electronics with regular trash. Check the InfoHub for special e-waste collection drives.")
)

// MAIN SCREEN COMPOSABLE

@Composable
fun InfoHubScreen() {
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    val tabTitles = listOf("News", "Tips", "Eco AI")

    Column(modifier = Modifier.fillMaxSize()) {
        InfoHubHeader()

        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTabIndex) {
            0 -> NewsTabContent()
            1 -> TipsTabContent()
            2 -> EcoAITabContent()
        }
    }
}

// TAB CONTENT COMPOSABLES

@Composable
fun InfoHubHeader() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2E8B57))
    ) {
        Column(
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("InfoHub", color = Color.White, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
            Text("News, tips, & AI assistant", color = Color.White, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun NewsTabContent() {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(sampleNews) { newsItem ->
            NewsCard(newsItem)
        }
    }
}

@Composable
fun TipsTabContent() {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(sampleTips) { tipItem ->
            TipCard(tipItem)
        }
    }
}

@Composable
fun TipCard(tip: TipItem) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(tip.headline, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF2E8B57))
            Spacer(modifier = Modifier.height(4.dp))
            Text(tip.detail, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun NewsCard(news: News) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(news.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(news.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(news.date, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }
}

// Preview remains the same
@Preview(showBackground = true, name = "1. Info Hub Screen")
@Composable
private fun InfoHubScreenPreview() {
    ProjectVerdeTheme {
        InfoHubScreen()
    }
}