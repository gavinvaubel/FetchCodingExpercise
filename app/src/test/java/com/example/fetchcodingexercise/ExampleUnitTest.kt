package com.example.fetchcodingexercise

import org.junit.Test

import org.junit.Assert.*
import org.junit.BeforeClass
import java.net.URL
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    companion object {
        @JvmStatic
        private lateinit var resCon: ResponseController
        private lateinit var lock: CountDownLatch

        @BeforeClass
        @JvmStatic
        fun initialize() {
            lock = CountDownLatch(1);
            resCon = ResponseController()
            resCon.urlResponse(URL("https://fetch-hiring.s3.amazonaws.com/hiring.json"))
        }
    }

    @Test
    fun assert_get_response() {
        lock.await(1000, TimeUnit.MILLISECONDS)
        val response = resCon.getResponse()
        assertNotEquals("", response)
    }

    @Test
    fun assert_received_items() {
        lock.await(1000, TimeUnit.MILLISECONDS)
        assertTrue(resCon.parseResponse())
        val items = resCon.getItems()
        assertNotEquals(0, items.size)
    }

    @Test
    fun assert_first_and_last_items() {
        lock.await(1000, TimeUnit.MILLISECONDS)
        assertTrue(resCon.parseResponse())
        resCon.sortList()
        assertEquals(ListItem(0, 1, "Item 0"), resCon.getItems()[0])
        assertEquals(ListItem(969, 4, "Item 969"), resCon.getItems()[resCon.getItems().size - 1])
    }
}