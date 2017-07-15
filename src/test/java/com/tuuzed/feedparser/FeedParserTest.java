package com.tuuzed.feedparser;

import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.net.URL;


public class FeedParserTest {

    @Before
    public void setup() {
        FeedParser.setHttpClient(new OkHttpClient());
    }

    @Test
    public void rss() throws Exception {
        String rss = "http://news.qq.com/newsgn/rss_newsgn.xml";
        FeedParser.parse(rss, new FeedHandlerImpl());
    }

    @Test
    public void localRss() throws Exception {
        URL url = FeedParserTest.class.getClassLoader().getResource("rss20.xml");
        System.out.print(url);
        FeedParser.parse(new FileReader(url.getFile()), new FeedHandlerImpl());
    }

    @Test
    public void atom() throws Exception {
        String atom = "https://www.v2ex.com/feed/tab/tech.xml";
        FeedParser.parse(atom, new FeedHandlerImpl());
    }

    @Test
    public void localAtom() throws Exception {
        URL url = FeedParserTest.class.getClassLoader().getResource("atom10.xml");
        FeedParser.parse(new FileReader(url.getFile()), new FeedHandlerImpl());
    }

}