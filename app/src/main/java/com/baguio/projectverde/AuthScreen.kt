package com.baguio.projectverde

import androidx.compose.foundation.Image // Add this import
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
// REMOVED: import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource // Add this import
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AuthScreen(navController: NavController) {
    var isLogin by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(32.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo
                Image(
                    // Ensure this matches your actual file name in res/drawable (e.g., R.drawable.logo)
                    painter = painterResource(id = R.drawable.verde_logo),
                    contentDescription = "Verde Baguio Logo",
                    modifier = Modifier.size(80.dp)
                )

                Text("Verde Baguio", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))
                Text("Building a cleaner City of Pines, together.", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 24.dp))

                // Toggle Switch
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF3F4F6), CircleShape)
                        .padding(4.dp)
                ) {
                    AuthTabButton(text = "Login", isSelected = isLogin, onClick = { isLogin = true })
                    AuthTabButton(text = "Register", isSelected = !isLogin, onClick = { isLogin = false })
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Form Fields
                if (!isLogin) {
                    AuthTextField(label = "Full Name")
                    Spacer(modifier = Modifier.height(12.dp))
                    AuthTextField(label = "Phone Number")
                    Spacer(modifier = Modifier.height(12.dp))
                }
                AuthTextField(label = "Email or Phone Number")
                Spacer(modifier = Modifier.height(12.dp))
                AuthTextField(label = "Password", isPassword = true)

                Spacer(modifier = Modifier.height(24.dp))

                // Action Button
                Button(
                    onClick = {
                        // On success, navigate to Home
                        navController.navigate("Home") {
                            popUpTo("Auth") { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = if (isLogin) "Sign In" else "Create Account")
                }
            }
        }
    }
}

@Composable
fun RowScope.AuthTabButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.weight(1f),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.White else Color.Transparent,
            contentColor = if (isSelected) Color.Black else Color.Gray
        ),
        elevation = if (isSelected) ButtonDefaults.buttonElevation(defaultElevation = 2.dp) else null,
        shape = CircleShape
    ) {
        Text(text, fontWeight = FontWeight.Bold)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTextField(label: String, isPassword: Boolean = false) {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text(label, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF3F4F6),
            unfocusedContainerColor = Color(0xFFF3F4F6),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp)
    )
}