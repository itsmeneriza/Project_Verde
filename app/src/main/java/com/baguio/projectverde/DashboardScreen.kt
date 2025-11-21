package com.baguio.projectverde

import androidx.compose.foundation.background
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
        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(shape = CircleShape, color = Color(0xFF00C853), modifier = Modifier.size(24.dp)) {
                Box(contentAlignment = Alignment.Center) { Text("V", color = Color.White, fontSize = 12.sp) }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("Verde Baguio", fontWeight = FontWeight.SemiBold, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Profile Card
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
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

        // Action Buttons (Scan & AI)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            DashboardActionButton(
                icon = AppIcons.QrCode,
                label = "Scan QR",
                color = Color(0xFF3B82F6), // Blue
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate("Scan") }
            )
            DashboardActionButton(
                icon = Icons.Default.Info, // Brain icon for AI
                label = "Eco AI",
                color = Color(0xFFA855F7), // Purple
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate("InfoHub") } // <--- Navigates to your AI Hub
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Challenges Section Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ThumbUp, contentDescription = null, tint = Color(0xFF00C853))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Challenges", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.weight(1f))
            Text("View All", color = Color.Gray, fontSize = 12.sp)
        }

        // Add Challenge Cards here...
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
