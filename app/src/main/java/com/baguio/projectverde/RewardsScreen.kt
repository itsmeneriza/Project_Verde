package com.baguio.projectverde

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.runtime.mutableIntStateOf

// 1. UPDATED DATA MODEL: Includes the cost (points)
val sampleRewards = listOf(
    RewardItem("Free Coffee at Foam Coffee", "Enjoy a premium coffee from a local partner.", 80),
    RewardItem("Eco-Friendly Tote Bag", "Sustainable cotton tote bag.", 120),
    RewardItem("10% Discount at Local Grocer", "Discount on fresh produce.", 150)
)

@Composable
fun RewardsScreen() {
    // 2. STATE MANAGEMENT: Define the user's current points as mutable state
    // remember Saveable ensures the value is kept if the screen recomposes/navigates away
    val currentPoints = rememberSaveable { mutableIntStateOf(1250) } // Initial defined points

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        // Pass the current points to the header to display
        RewardsHeader(points = currentPoints.intValue)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Featured Rewards", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(contentPadding = PaddingValues(bottom = 60.dp)) {
            items(sampleRewards) { rewardItem ->
                // Pass the currentPoints state setter to the card
                RewardCard(
                    reward = rewardItem,
                    currentPointsState = currentPoints
                )
            }
        }
    }
}

@Composable
fun RewardsHeader(points: Int) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2E8B57))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Your Points", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                // Display the dynamic point total
                Text(
                    text = points.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Text("Available Points", color = Color.White, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun RewardCard(reward: RewardItem, currentPointsState: androidx.compose.runtime.MutableState<Int>) {
    val context = LocalContext.current
    val isRedeemable = currentPointsState.value >= reward.points

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(reward.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("${reward.points} pts", color = Color(0xFF2E8B57), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(reward.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))

            // 3. IMPLEMENT DEDUCTION LOGIC
            Button(
                onClick = {
                    if (isRedeemable) {
                        // Deduct points and update the state
                        currentPointsState.value -= reward.points
                        Toast.makeText(context, "Redeemed! ${reward.title}. Points deducted.", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Not enough points to redeem ${reward.title}!", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isRedeemable, // Button is disabled if points are insufficient
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRedeemable) Color(0xFF2E8B57) else Color.Gray
                )
            ) {
                Text(if (isRedeemable) "Redeem Now" else "Insufficient Points", color = Color.White)
            }
        }
    }
}