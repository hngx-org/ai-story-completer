package com.newton.storycompleter.app.data.remote.api

import android.util.Log
import com.newton.storycompleter.BuildConfig
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


interface AiCallback {
    fun onResponse(response: String)
    fun onFailure(e: Exception)
}

class AiRepository {
    fun generateText(
        prompt: String,
        callback: AiCallback
    ) {
        val client = OkHttpClient()

        val body =
            """
            {
                "model": "gpt-3.5-turbo-instruct",
                "prompt": "$prompt",
                "temperature": 0.5,
                "max_tokens": 50
            }
            """.trimIndent()

        val request = Request.Builder()
            .url("https://api.openai.com/v1/completions")
            .addHeader("Content-Type", "application/json")
            .addHeader(
                "Authorization",
                "Bearer ${BuildConfig.AI_COMPLETION_API_KEY}"
            )
            .post(body.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseText = response.body?.string() ?: ""
                val jsonObject = JSONObject(responseText)
                Log.d("ResponseCheck", responseText)
                val jsonArray: JSONArray = jsonObject.getJSONArray("choices")

                val textResult = jsonArray.getJSONObject(0).getString("text")
                val trimResult = textResult.substringBeforeLast('.')
                callback.onResponse("$trimResult.")
            }
        }
        )
    }
}

