package com.github.tuuzed.feedparser;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class FeedParserTest {
    @Test
    public void rssFeedTest() throws FileNotFoundException {
        FeedParser feedParser = new FeedParser.Builder().build();
        TestFeedCallback feedCallback = new TestFeedCallback(true);
        feedCallback.entryCallback = new TestEntryCallback(false);
        feedCallback.imageCallback = new TestImageCallback(false);
        feedCallback.textInputCallback = new TestTextInputCallback(false);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String resource = classLoader.getResource("rss_2_0.xml").getFile();
        FileReader reader = new FileReader(resource);
//        feedParser.parse("http://news.qq.com/newsgn/rss_newsgn.xml", feedCallback);
        feedParser.parse(reader, feedCallback);
    }

    @Test
    public void atomFeedTest() throws FileNotFoundException {
        FeedParser feedParser = new FeedParser.Builder().build();
        TestFeedCallback feedCallback = new TestFeedCallback(true);
        feedCallback.entryCallback = new TestEntryCallback(true);
        feedCallback.imageCallback = new TestImageCallback(true);
        feedCallback.textInputCallback = new TestTextInputCallback(true);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String resource = classLoader.getResource("atom_1_0.xml").getFile();
        FileReader reader = new FileReader(resource);
//        feedParser.parse("http://tuuzed.top/atom.xml", feedCallback);
        feedParser.parse(reader, feedCallback);
    }

}
