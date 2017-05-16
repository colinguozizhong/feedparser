package com.github.tuuzed.feedparser;

import okhttp3.OkHttpClient;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

public class FeedParserTest {
    @Test
    public void feedParserReaderTest() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
        FeedParser feedParser = new FeedParser.Builder()
                .setDebug(true)
                .setHttpClient(httpClient)
                .build();
        TestFeedCallback feedCallback = new TestFeedCallback(true);
        feedCallback.entryCallback = new TestEntryCallback(true);
        feedCallback.imageCallback = new TestImageCallback(true);
        feedCallback.textInputCallback = new TestTextInputCallback(true);
        feedParser.parse("http://news.qq.com/newsgn/rss_newsgn.xml", feedCallback);
    }

    @Test
    public void feedLocalTest() throws FileNotFoundException {
        FeedParser feedParser = new FeedParser.Builder()
                .setDebug(true)
                .build();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String resource = classLoader.getResource("atom_1_0.xml").getFile();
        FileReader reader = new FileReader(resource);
        TestFeedCallback feedCallback = new TestFeedCallback(true);
        feedCallback.entryCallback = new TestEntryCallback(true);
        feedCallback.imageCallback = new TestImageCallback(true);
        feedCallback.textInputCallback = new TestTextInputCallback(true);
        feedParser.parse("http://tuuzed.top/atom.xml", feedCallback);
    }

}
