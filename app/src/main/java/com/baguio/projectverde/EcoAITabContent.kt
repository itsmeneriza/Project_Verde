package com.baguio.projectverde

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

// Data model for a single message
data class ChatMessage(val text: String, val isUser: Boolean)

// --- 2. Main Composable for the AI Chat Feature ---
@Composable
fun EcoAITabContent() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Initialize the Gemini Manager (runs once)
    val geminiManager = remember { GeminiManager(context) }

    // State for chat history
    val messages = remember {
        mutableStateListOf(
            ChatMessage("Hello! I am your real Eco Assistant. Ask me about recycling or waste collection schedules in Baguio.", isUser = false)
        )
    }

    // State for user input and loading indicator
    var inputText by rememberSaveable { mutableStateOf("") }
    var isWaitingForResponse by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Chat History Area
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 16.dp),
            reverseLayout = false
        ) {
            items(messages) { message ->
                MessageBubble(message)
            }
        }

        // Input Field Area
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text("Ask your Eco Question...") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (inputText.isNotBlank() && !isWaitingForResponse) {
                        val userQuery = inputText.trim()

                        // Add User Message and clear input
                        messages.add(ChatMessage(userQuery, isUser = true))
                        inputText = ""
                        isWaitingForResponse = true

                        // Launch the real API call
                        coroutineScope.launch {
                            try {
                                val response = geminiManager.generativeModel.generateContent(userQuery)
                                val aiResponseText = response.text ?: "Sorry, I couldn't process that query."
                                messages.add(ChatMessage(aiResponseText, isUser = false))
                            } catch (e: Exception) {
                                messages.add(ChatMessage("Error: Could not connect to AI. Please check Internet and API setup.", isUser = false))
                            } finally {
                                isWaitingForResponse = false
                            }
                        }
                    }
                },
                enabled = inputText.isNotBlank() && !isWaitingForResponse,
                colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0xFF2E8B57))
            ) {
                if (isWaitingForResponse) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White)
                }
            }
        }
    }
}

// Composable for a single chat bubble (FIXED type error)
@Composable
fun MessageBubble(message: ChatMessage) {
    // Explicitly define as Horizontal alignment to fix the error
    val horizontalAlignment: Alignment.Horizontal =
        if (message.isUser) Alignment.End else Alignment.Start

    val bubbleColor = if (message.isUser) Color(0xFFC8E6C9) else Color(0xFFF0F0F0)

    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalAlignment = horizontalAlignment
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = bubbleColor),
            elevation = CardDefaults.cardElevation(1.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                color = Color.Black
            )
        }
    }
}