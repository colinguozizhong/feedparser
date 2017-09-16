package com.tuuzed.feedparser;

import org.junit.Test;

import java.io.FileReader;
import java.net.URL;


public class FeedParserTest {

    @Test
    public void rss() throws Exception {
        FeedParser.parse("http://epayment.blog.sohu.com/rss", new FeedCallbackImpl());
        FeedParser.parse("http://paynews.net/portal.php?mod=rss", new FeedCallbackImpl());
        FeedParser.parse("http://news.qq.com/newsgn/rss_newsgn.xml", new FeedCallbackImpl());
        FeedParser.parse("http://feedmaker.kindle4rss.com/feeds/choice.thepaper.xml", new FeedCallbackImpl());
    }

    @Test
    public void redirect_rss() throws Exception {
        FeedParser.parse("http://www.zhihu.com/rss", new FeedCallbackImpl());
    }

    @Test
    public void redirect_atom() throws Exception {
        FeedParser.parse("http://www.v2ex.com/feed/tab/tech.xml", new FeedCallbackImpl());
    }

    @Test
    public void atom() throws Exception {
        FeedParser.parse("https://www.v2ex.com/feed/tab/tech.xml", new FeedCallbackImpl());
    }

    @Test
    public void localRss() throws Exception {
        URL url = FeedParserTest.class.getClassLoader().getResource("rss20.xml");
        if (url != null) {
            FeedParser.parse(new FileReader(url.getFile()), new FeedCallbackImpl());
        }
    }


    @Test
    public void localAtom() throws Exception {
        URL url = FeedParserTest.class.getClassLoader().getResource("atom10.xml");
        if (url != null) {
            FeedParser.parse(new FileReader(url.getFile()), new FeedCallbackImpl());
        }
    }

}