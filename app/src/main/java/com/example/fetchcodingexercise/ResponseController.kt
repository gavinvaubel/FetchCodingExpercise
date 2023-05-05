package com.example.fetchcodingexercise

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.URL

class ResponseController {
    private lateinit var items: MutableList<ListItem>
    private var response = ""

    // Gets response from API asynchronously
    @OptIn(DelicateCoroutinesApi::class)
    fun urlResponse(url: URL) {

        runBlocking {
            GlobalScope.launch {
                response = url.readText()
            }
        }
    }

    // returns data class list items from the JSON returned as a new list
    fun parseResponse(): Boolean {
        if(response != "") {
            val gson = Gson()
            val listItems = object : TypeToken<List<ListItem>>() {}.type

            items = gson.fromJson(response, listItems)
            return true
        }
        return false
    }

    // removes unwanted names from list, sorts by listId, then sorts by name
    fun sortList() {
        items.removeIf{ it.name == ""}
        items.removeIf{ it.name == null}
        val sortedItems: MutableList<ListItem> = items.sortedWith(compareBy<ListItem>{ it.listId }.thenBy { it.name }) as MutableList<ListItem>
        items = sortedItems
    }

    // converts results to list of strings for easy access from the TextView
    fun convertToString(): MutableList<String> {
        val stringList = mutableListOf<String>()

        items.forEach{
            val str = "List Id: " + it.listId + " Name: " + it.name
            println(str)
            stringList.add(str)
        }

        return stringList
    }

    fun getItems(): MutableList<ListItem> {
        return items
    }

    fun getResponse(): String {
        return response
    }
}