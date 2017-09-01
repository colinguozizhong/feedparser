package com.tuuzed.feedparser

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import java.io.FileReader


class FeedParserTest {
    private lateinit var httpClient: OkHttpClient
    @Before
    fun setup() {
        httpClient = OkHttpClient.Builder().build()
    }


    private fun getResponseBody(url: String): ResponseBody? {
        val request = Request.Builder()
                .url(url)
                .get()
                .build()
        val call = httpClient.newCall(request)
        val response = call.execute()
        return response.body()
    }

    @Test
    @Throws(Exception::class)
    fun rss() {
        val rss = "http://news.qq.com/newsgn/rss_newsgn.xml"
        val responseBody = getResponseBody(rss)
        if (responseBody != null) {
            FeedParser.parse(responseBody.charStream(), FeedCallbackImpl())
        }
    }

    @Test
    @Throws(Exception::class)
    fun localRss() {
        val url = FeedParserTest::class.java.classLoader.getResource("rss20.xml")
        FeedParser.parse(FileReader(url!!.file), FeedCallbackImpl())
    }

    @Test
    @Throws(Exception::class)
    fun atom() {
        val atom = "https://www.v2ex.com/feed/tab/tech.xml"
        val responseBody = getResponseBody(atom)
        if (responseBody != null) {
            FeedParser.parse(responseBody.charStream(), FeedCallbackImpl())
        }
    }

    @Test
    @Throws(Exception::class)
    fun localAtom() {
        val url = FeedParserTest::class.java.classLoader.getResource("atom10.xml")
        FeedParser.parse(FileReader(url!!.file), FeedCallbackImpl())
    }

}