package com.baguio.projectverde

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DashboardScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .padding(16.dp)
    ) {
        // --- Header ---
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(shape = CircleShape, color = Color(0xFF00C853), modifier = Modifier.size(24.dp)) {
                Box(contentAlignment = Alignment.Center) { Text("V", color = Color.White, fontSize = 12.sp) }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("Verde Baguio", fontWeight = FontWeight.SemiBold, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Profile Card ---
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                // CHANGED: Added fillMaxWidth() so SpaceBetween works correctly
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Avatar Placeholder
                    Surface(modifier = Modifier.size(48.dp), shape = CircleShape, color = Color(0xFFDCFCE7)) {
                        Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.padding(8.dp), tint = Color(0xFF00C853))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Neriza Saldo", fontWeight = FontWeight.Bold, color = Color.Black)
                        Text("1250 pts", color = Color(0xFF00C853), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
                OutlinedButton(onClick = {}) { Text("Profile") }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Action Buttons (Scan & AI) ---
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            DashboardActionButton(
                icon = AppIcons.QrCode,
                label = "Scan QR",
                color = Color(0xFF3B82F6), // Blue
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate("Scan") }
            )
            DashboardActionButton(
                icon = Icons.Default.Info,
                label = "Eco AI",
                color = Color(0xFFA855F7), // Purple
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate("InfoHub") }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Challenges Section ---
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Section Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.ThumbUp, contentDescription = null, tint = Color(0xFF00C853), modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Challenges", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                    Spacer(modifier = Modifier.weight(1f))

                    // View All Link
                    Text(
                        text = "View All",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.clickable { navController.navigate("Challenges") }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Challenge 1
                DashboardChallengeItem(
                    title = "Burnham Park Community Cleanup",
                    date = "Sat, Nov 15 • 8:00 AM",
                    daysLeft = "5 days left",
                    tag = "Community Event",
                    points = "+30",
                    icon = Icons.Default.Face,
                    themeColor = Color(0xFF3B82F6), // Blue
                    bgColor = Color(0xFFEFF6FF)     // Light Blue BG
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Challenge 2
                DashboardChallengeItem(
                    title = "E-Waste Collection Drive",
                    date = "Sat, Nov 18 • 9:00 AM",
                    daysLeft = "8 days left",
                    tag = "Special Collection",
                    points = "+40",
                    icon = Icons.Default.Star,
                    themeColor = Color(0xFFA855F7), // Purple
                    bgColor = Color(0xFFFAF5FF)     // Light Purple BG
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Footer
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("Challenges") },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "See all challenges and events →",
                        color = Color(0xFF00C853),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardActionButton(icon: ImageVector, label: String, color: Color, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        modifier = modifier.height(120.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(shape = CircleShape, color = color, modifier = Modifier.size(48.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(label, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

// --- Helper Component for the Challenge List Items ---
@Composable
fun DashboardChallengeItem(
    title: String,
    date: String,
    daysLeft: String,
    tag: String,
    points: String,
    icon: ImageVector,
    themeColor: Color,
    bgColor: Color
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = bgColor),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                // Icon
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = themeColor,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Title & Date
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937), lineHeight = 18.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(date, fontSize = 11.sp, color = Color.Gray)
                    }
                }

                // Points Badge
                Surface(
                    color = Color(0xFFFEF9C3), // Light Yellow
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = points,
                        color = Color(0xFFA16207), // Dark Yellow/Brown
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom Row (Timer & Tag)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(daysLeft, fontSize = 11.sp, color = Color.Gray)
                }

                // Tag
                Surface(
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = tag,
                        color = Color.Black,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}