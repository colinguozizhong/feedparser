package com.tuuzed.feedparser;

import okhttp3.OkHttpClient;
import org.junit.Test;

import java.io.FileReader;
import java.net.URL;


public class FeedParserTest {

    @Test
    public void rss() throws Exception {
        String rss = "http://news.qq.com/newsgn/rss_newsgn.xml";
        FeedParser.setHttpClient(new OkHttpClient());
        FeedParser.parse(rss, new FeedCallbackImpl());
    }

    @Test
    public void localRss() throws Exception {
        URL url = FeedParserTest.class.getResource("/rss20.xml");
        FeedParser.parse(new FileReader(url.getFile()), new FeedCallbackImpl());
    }

    @Test
    public void atom() throws Exception {
        String atom = "https://www.v2ex.com/feed/tab/tech.xml";
        FeedParser.setHttpClient(new OkHttpClient());
        FeedParser.parse(atom, new FeedCallbackImpl());
    }

    @Test
    public void localAtom() throws Exception {
        URL url = FeedParserTest.class.getResource("/atom10.xml");
        FeedParser.parse(new FileReader(url.getFile()), new FeedCallbackImpl());
    }

}