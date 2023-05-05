package com.example.fetchcodingexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ArrayAdapter
import android.widget.ListView
import java.net.URL


class MainActivity : AppCompatActivity() {

    private val resCon = ResponseController()

    private val url = URL("https://fetch-hiring.s3.amazonaws.com/hiring.json")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resCon.urlResponse(url)

        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
            if(resCon.parseResponse()) {
                resCon.sortList()

                val stringList = resCon.convertToString()

                val arrayAdapter: ArrayAdapter<*>

                val mListView = findViewById<ListView>(R.id.userlist)

                arrayAdapter = ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, stringList)
                mListView.adapter = arrayAdapter
            }
        }


    }
}