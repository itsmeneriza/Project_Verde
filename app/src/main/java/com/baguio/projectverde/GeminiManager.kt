package com.baguio.projectverde

import android.content.Context
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig

class GeminiManager(context: Context) {
    private val apiKey = context.getString(R.string.gemini_api_key)

    private val systemInstruction = """
        You are Eco Assistant, a helpful and knowledgeable expert on waste management, recycling, and sustainability specifically for Baguio City, Philippines.
        Your tone should be encouraging, formal, and informative.
        Keep answers concise. Always prioritize information related to responsible consumption, segregation, local schedules, or eco-friendly tips.
    """.trimIndent()

    private val generationConfig = generationConfig {
        systemInstruction = this@GeminiManager.systemInstruction
    }

    // FIX: Using 'lazy' to defer initialization until the property is first accessed.
    val generativeModel by lazy {
        GenerativeModel(
            modelName = "gemini-2.5-flash",
            apiKey = apiKey,
            config = generationConfig
        )
    }
}