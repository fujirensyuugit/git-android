package com.example.mygoogleapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import androidx.core.os.HandlerCompat
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.SocketException
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private var URL = "https://www.googleapis.com/books/v1/volumes?q=Android"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editText: EditText = findViewById(R.id.kensaku)
        val button: Button = findViewById(R.id.kensaku_button)
        //  button.setOnClickListener(this)
        receveBookInfo(URL)
    }

    @UiThread
    private fun receveBookInfo(urlFull: String) {
        val handler = HandlerCompat.createAsync(mainLooper)
        val backgroundReceber = BookAPIReceber(handler, urlFull)
        val executieService = Executors.newSingleThreadExecutor()
        executieService.submit(backgroundReceber)
    }

    private inner class BookAPIReceber(handler: Handler, url: String) : Runnable {
        private val _handler = handler
        private val _url = url

        @WorkerThread
        override fun run() {
            var result = ""

            val url = java.net.URL(_url)

            val con = url.openConnection() as? HttpURLConnection
            con?.let {
                try {
                    it.connectTimeout = 1000

                    it.readTimeout = 1000

                    it.requestMethod = "GET"

                    it.connect()

                    val stream = it.inputStream

                          result = isString(stream)


                    stream.close()
                } catch (ex: SocketException) {
                             Log.w("hoge","通信タイムアウト",ex)
                }
                it.disconnect()
            }
               val postExcuter = BookPostExecuter(result)
                _handler.post(postExcuter)
        }
    }

    private fun isString(stream: InputStream):String{
        val sb = StringBuilder()
        val reader = BufferedReader(InputStreamReader(stream,"UTF-8"))
        var line = reader.readLine()
        while (line !=null){
            sb.append(line)
            line = reader.readLine()
        }
        reader.close()
        return sb.toString()
    }

    private inner class BookPostExecuter(result:String) :Runnable{
        private val _result = result
        @UiThread
        override fun run() {
            val rootJSON = JSONObject(_result)

            val item = rootJSON.getJSONArray("items")

            val itemJSON = item.getJSONObject(0)

            val info = itemJSON.getJSONObject("volumeInfo")

            val title = info.getString("title")

            val text: TextView = findViewById(R.id.textView)
            text.text = title
        }
    }
}

