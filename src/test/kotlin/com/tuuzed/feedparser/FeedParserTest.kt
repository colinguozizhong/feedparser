package com.tuuzed.feedparser

import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import java.io.FileReader


class FeedParserTest {

    @Before
    fun setup() {
        // FeedParser.setHttpClient(OkHttpClient())
    }

    @Test
    @Throws(Exception::class)
    fun rss() {
        val rss = "http://news.qq.com/newsgn/rss_newsgn.xml"
        FeedParser.parse(rss, FeedHandlerImpl())
    }

    @Test
    @Throws(Exception::class)
    fun localRss() {
        val url = FeedParserTest::class.java.classLoader.getResource("rss20.xml")
        print(url)
        FeedParser.parse(FileReader(url!!.file), FeedHandlerImpl())
    }

    @Test
    @Throws(Exception::class)
    fun atom() {
        val atom = "http://droidyue.com/atom.xml"
        FeedParser.parse(atom, FeedHandlerImpl())
    }

    @Test
    @Throws(Exception::class)
    fun localAtom() {
        val url = FeedParserTest::class.java.classLoader.getResource("atom10.xml")
        FeedParser.parse(FileReader(url!!.file), FeedHandlerImpl())
    }

}