package com.baguio.projectverde

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List // Replaces Image
import androidx.compose.material.icons.filled.Star // Replaces FlashOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScanScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // 1. Mock Camera Preview (Just a dark gray placeholder for now)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF111111))
        )

        // 2. Overlay UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Flashlight logic */ }) {
                    // CHANGED: Used 'Star' because 'FlashOn' requires the Extended library
                    Icon(Icons.Default.Star, contentDescription = "Flash", tint = Color.White)
                }
                Text(
                    text = "Scan QR Code",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { /* Close logic */ }) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
            }

            // Scanner Frame (Visual only)
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .border(2.dp, Color(0xFF00C853), RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            ) {
                // Corner Accents
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 4.dp.toPx()
                    val lineLength = 30.dp.toPx()
                    val color = Color(0xFF00C853)

                    // Top Left
                    drawLine(color, Offset(0f, 0f), Offset(lineLength, 0f), strokeWidth)
                    drawLine(color, Offset(0f, 0f), Offset(0f, lineLength), strokeWidth)

                    // Top Right
                    drawLine(color, Offset(size.width, 0f), Offset(size.width - lineLength, 0f), strokeWidth)
                    drawLine(color, Offset(size.width, 0f), Offset(size.width, lineLength), strokeWidth)

                    // Bottom Left
                    drawLine(color, Offset(0f, size.height), Offset(lineLength, size.height), strokeWidth)
                    drawLine(color, Offset(0f, size.height), Offset(0f, size.height - lineLength), strokeWidth)

                    // Bottom Right
                    drawLine(color, Offset(size.width, size.height), Offset(size.width - lineLength, size.height), strokeWidth)
                    drawLine(color, Offset(size.width, size.height), Offset(size.width, size.height - lineLength), strokeWidth)
                }
            }

            // Bottom Text & Gallery Button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Align the QR code within the frame to scan",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                androidx.compose.material3.Button(
                    onClick = { /* Open Gallery */ },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f)),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    // CHANGED: Used 'List' because 'Image' requires the Extended library
                    Icon(Icons.Default.List, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Scan from Gallery", color = Color.White)
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}