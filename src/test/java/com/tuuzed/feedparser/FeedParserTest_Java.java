package com.tuuzed.feedparser;

import org.junit.Test;

import java.io.FileReader;
import java.net.URL;

public class FeedParserTest_Java {

    @Test
    public void rss() throws Exception {
        FeedParser.INSTANCE.parse("http://epayment.blog.sohu.com/rss",
                new FeedCallbackImpl(), "utf-8");
        FeedParser.INSTANCE.parse("http://paynews.net/portal.php?mod=rss",
                new FeedCallbackImpl(), "utf-8");
        FeedParser.INSTANCE.parse("http://news.qq.com/newsgn/rss_newsgn.xml",
                new FeedCallbackImpl(), "utf-8");
    }

    @Test
    public void atom() throws Exception {
        FeedParser.INSTANCE.parse("https://www.v2ex.com/feed/tab/tech.xml",
                new FeedCallbackImpl(), "utf-8");
    }

    @Test
    public void localRss() throws Exception {
        URL url = FeedParserTest_Java.class.getClassLoader().getResource("rss20.xml");
        if (url != null) {
            FeedParser.INSTANCE.parse(new FileReader(url.getFile()), new FeedCallbackImpl());
        }
    }

    @Test
    public void localAtom() throws Exception {
        URL url = FeedParserTest_Java.class.getClassLoader().getResource("atom10.xml");
        if (url != null) {
            FeedParser.INSTANCE.parse(new FileReader(url.getFile()), new FeedCallbackImpl());
        }
    }
}
