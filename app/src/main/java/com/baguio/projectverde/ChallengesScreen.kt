package com.baguio.projectverde

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

// Dummy Data Model
data class Challenge(
    val id: Int,
    val title: String,
    val date: String,
    val points: Int,
    val type: String, // "Event" or "Solo"
    val joined: Boolean = false
)

@Composable
fun ChallengesScreen() {
    val context = LocalContext.current
    var selectedFilter by remember { mutableStateOf("All") }

    // --- PERMISSION HANDLING FOR NOTIFICATIONS (Android 13+) ---
    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else {
            mutableStateOf(true)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted
            if (isGranted) {
                Toast.makeText(context, "Notifications enabled! Try joining again.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Notifications are disabled.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    // Create Notification Channel (Required for Android 8+)
    LaunchedEffect(Unit) {
        createNotificationChannel(context)
    }

    // Mock Data - POINTS UPDATED
    val challenges = listOf(
        Challenge(1, "Burnham Park Cleanup", "Sat, Nov 15 • 8:00 AM", 30, "Event"),
        Challenge(2, "E-Waste Collection Drive", "Sat, Nov 18 • 9:00 AM", 40, "Event"),
        Challenge(3, "Plastic-Free Week", "Nov 20 - Nov 27", 50, "Solo"),
        Challenge(4, "Tree Planting @ Busol", "Sun, Dec 01 • 7:00 AM", 60, "Event"),
        Challenge(5, "Recycle 10 Bottles", "Anytime", 5, "Solo")
    )

    val filteredList = if (selectedFilter == "All") challenges else challenges.filter {
        if (selectedFilter == "Community") it.type == "Event" else it.type == "Solo"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .padding(16.dp)
    ) {
        // ... (Keep Header Code) ...
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 16.dp)) {
            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFF00C853), modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text("Active Challenges", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))
        }

        // ... (Keep Filter Chips Code) ...
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(selected = selectedFilter == "All", onClick = { selectedFilter = "All" }, label = "All")
            FilterChip(selected = selectedFilter == "Community", onClick = { selectedFilter = "Community" }, label = "Community")
            FilterChip(selected = selectedFilter == "Solo", onClick = { selectedFilter = "Solo" }, label = "Solo")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(filteredList) { challenge ->
                ChallengeCard(challenge) {
                    // --- 1. TOAST IMPLEMENTATION ---
                    Toast.makeText(context, "Joined: ${challenge.title}", Toast.LENGTH_SHORT).show()

                    // --- 2. NOTIFICATION IMPLEMENTATION WITH PERMISSION CHECK ---
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (hasNotificationPermission) {
                            showNotification(context, "Challenge Joined!", "Don't forget: ${challenge.title}")
                        } else {
                            // Request permission if not granted
                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    } else {
                        // For older Android versions, permission is granted at install time
                        showNotification(context, "Challenge Joined!", "Don't forget: ${challenge.title}")
                    }
                }
            }
        }
    }
}

// ... (Keep FilterChip Composable) ...
@Composable
fun FilterChip(selected: Boolean, onClick: () -> Unit, label: String) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFF00C853) else Color.White,
            contentColor = if (selected) Color.White else Color.Gray
        ),
        shape = RoundedCornerShape(50),
        border = if (!selected) androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray) else null,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.height(36.dp)
    ) {
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}

// Pass a click listener (onJoin) to the card
@Composable
fun ChallengeCard(challenge: Challenge, onJoin: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // ... (Keep existing UI rows for badge and points) ...
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Surface(
                    color = if (challenge.type == "Event") Color(0xFFE0F2FE) else Color(0xFFF3E8FF),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = challenge.type,
                        color = if (challenge.type == "Event") Color(0xFF0284C7) else Color(0xFF9333EA),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Surface(
                    color = Color(0xFFFEF9C3),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "+${challenge.points} pts",
                        color = Color(0xFFA16207),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(challenge.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1F2937))
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(challenge.date, fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // The Button triggers the onJoin callback
            Button(
                onClick = onJoin,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Join Challenge",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// --- Notification Helpers ---

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Verde Challenges"
        val descriptionText = "Notifications for joined challenges"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("VERDE_CHANNEL_ID", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun showNotification(context: Context, title: String, content: String) {
    val builder = NotificationCompat.Builder(context, "VERDE_CHANNEL_ID")
        // CHANGED: Using ic_launcher_foreground as requested
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(title)
        .setContentText(content)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    try {
        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    } catch (e: SecurityException) {
        // Handle missing permission gracefully if check fails
        Toast.makeText(context, "Permission needed for notifications", Toast.LENGTH_SHORT).show()
    }
}