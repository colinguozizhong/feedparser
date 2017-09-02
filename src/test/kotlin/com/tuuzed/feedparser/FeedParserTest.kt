package com.tuuzed.feedparser

import org.junit.Test
import java.io.FileReader


class FeedParserTest {

    @Test
    @Throws(Exception::class)
    fun rss() {
        FeedParser.parse("http://epayment.blog.sohu.com/rss", FeedCallbackImpl())
        FeedParser.parse("http://paynews.net/portal.php?mod=rss", FeedCallbackImpl())
        FeedParser.parse("http://news.qq.com/newsgn/rss_newsgn.xml", FeedCallbackImpl())
    }

    @Test
    @Throws(Exception::class)
    fun atom() {
        FeedParser.parse("https://www.v2ex.com/feed/tab/tech.xml", FeedCallbackImpl())
    }


    @Test
    @Throws(Exception::class)
    fun localRss() {
        val url = FeedParserTest::class.java.classLoader.getResource("rss20.xml")
        FeedParser.parse(FileReader(url!!.file), FeedCallbackImpl())
    }

    @Test
    @Throws(Exception::class)
    fun localAtom() {
        val url = FeedParserTest::class.java.classLoader.getResource("atom10.xml")
        FeedParser.parse(FileReader(url!!.file), FeedCallbackImpl())
    }

}