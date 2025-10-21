package com.baguio.projectverde

import com.baguio.projectverde.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig

class GeminiManager {

    private val systemPrompt = """
        You are Eco Assistant — an expert on recycling, waste management, and sustainability in Baguio City, Philippines.
        Keep your answers helpful, concise, and environmentally focused.
        Include local practices, schedules, or eco-friendly advice whenever possible.
    """.trimIndent()

    private val model = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY,
        generationConfig = generationConfig {
            temperature = 0.7f
        }
    )

    suspend fun ask(question: String): String {
        return try {
            val result = model.generateContent("$systemPrompt\n\nUser: $question")
            result.text ?: "Sorry, I couldn't generate a response."
        } catch (e: Exception) {
            "⚠️ Connection error: ${e.localizedMessage}"
        }
    }
}
