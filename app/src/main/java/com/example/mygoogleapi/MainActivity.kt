package com.example.mygoogleapi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mygoogleapi.ui.main.SearchViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: SearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val editText: EditText = findViewById(R.id.kensaku)
        val button: Button = findViewById(R.id.kensaku_button)
        val text: TextView = findViewById(R.id.textView)
        button.setOnClickListener {
            viewModel.getBookData(editText.text.toString())
        }

        viewModel.searchResult.observe(this, Observer {
            it.onSuccess {
                text.text = it.items?.getOrNull(0)?.volumeInfo?.title ?: "未取得"
            }
            it.onFailure {
                text.text = "エラー"
            }

        })
    }
}