package com.newton.storycompleter.ui.api

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException


interface AiCallback {
    fun onResponse(response: String)
    fun onFailure(e: Exception)
}
class AiRepository {
    fun generateText(prompt: String,callback: AiCallback) {
        val client = OkHttpClient()

        val body =
            """
            {
                "model": "gpt-3.5-turbo-instruct",
                "prompt": "$prompt",
                "temperature": 0.5,
                "max_tokens": 50,
                "n": 1,
                "stop": "\n"
            }
            """.trimIndent()

        val request = Request.Builder()
            .url("https://api.openai.com/v1/completions")
            .addHeader("Content-Type","application/json")
            .addHeader("Authorization", "Bearer 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")
            .post(body.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseText = response.body?.string() ?: ""
                callback.onResponse(responseText)
            }
        })


       /* val response = client.newCall(request).execute()
        Log.d("API Call", "${response.body?.string()}")
        return response.body?.string() ?: ""*/

    }
}