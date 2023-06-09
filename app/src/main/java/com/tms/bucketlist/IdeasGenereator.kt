package com.tms.bucketlist
import android.annotation.SuppressLint
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.tms.bucketlist.databinding.FragmentIdeasGenereatorBinding
import com.tms.bucketlist.ui.profile.ProfileViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONException
import java.io.IOException
import java.util.concurrent.TimeUnit

class IdeasGenereator : Fragment() {

    private var _binding: FragmentIdeasGenereatorBinding? = null
    private val binding get() = _binding!!

    private var textOutput : TextView? = null
    private var textInput : EditText? = null
    private var button : ImageButton? = null

    private val savedInput = "IdeasGenereatorInput"
    private val savedOutput = "IdeasGenereatorOutput"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ideas_genereator, container, false)
        textOutput = view.findViewById<TextView>(R.id.GPTanswer)
        textInput = view.findViewById<EditText>(R.id.GPTinput)
        button = view.findViewById<ImageButton>(R.id.GPTsend)

        button?.setOnClickListener {
            if (textInput?.text.toString() == "") {
                textOutput?.text = "Введите запрос!"
                return@setOnClickListener
            }
            textOutput?.text = "Загрузка..."
            sendChatGPTRequest()
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) { // Here You have to save count value
        super.onSaveInstanceState(outState)
        outState.putString(savedOutput, textOutput?.text.toString())
        outState.putString(savedInput, textInput?.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (savedInstanceState != null){
            textOutput?.text = savedInstanceState.getString(savedOutput, "")
            textInput?.setText(savedInstanceState.getString(savedInput, ""))
        }
    }

    private fun sendChatGPTRequest(){
        //val apiKey = "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"
        val apiKey = ""
        if (apiKey == "")
            throw Exception("Введи ключ")
        val url = "https://api.openai.com/v1/engines/text-davinci-003/completions"
        val client = OkHttpClient.Builder()
            .callTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .build()

        val prompt = "Ответь как советник bucket list: " +
                textInput?.text

        val jsonObject = JSONObject()
        jsonObject.put("prompt", prompt)
        jsonObject.put("max_tokens", 4000)

        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val requestBody = jsonObject.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer ${apiKey}")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.toString())
                textOutput?.post {
                    e.printStackTrace()
                    textOutput?.text = "Что-то пошло не так... FAIL"
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                println(responseData)
                try {
                    val jsonObject = JSONObject(responseData)
                    var text = jsonObject.getJSONArray("choices").getJSONObject(0).getString("text")
                    text = text.substring(text.indexOfFirst { c -> c.isWhitespace() })
                    text = text.substring(text.indexOfFirst { c -> c.isLetterOrDigit() })
                    textOutput?.post {
                        textOutput?.text = text
                    }
                    println(text.length)
                } catch (e: JSONException) {
                    e.printStackTrace()
                    textOutput?.post {
                        textOutput?.text = "Что-то пошло не так..."
                    }
                }
            }
        })
    }
}