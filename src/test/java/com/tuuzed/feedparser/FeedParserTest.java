package com.tuuzed.feedparser;

import okhttp3.*;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;


public class FeedParserTest {
    private OkHttpClient httpClient;

    @Before
    public void setup() {
        httpClient = new OkHttpClient.Builder().build();
    }

    private ResponseBody getResponseBody(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = httpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body();
        } catch (IOException e) {
            return null;
        }
    }

    @Test
    public void rss() throws Exception {
        String rss = "http://news.qq.com/newsgn/rss_newsgn.xml";
        ResponseBody responseBody = getResponseBody(rss);
        if (responseBody != null) {
            FeedParser.parse(responseBody.charStream(), new FeedCallbackImpl());
        }
    }

    @Test
    public void localRss() throws Exception {
        URL url = FeedParserTest.class.getClassLoader().getResource("rss20.xml");
        if (url != null) {
            FeedParser.parse(new FileReader(url.getFile()), new FeedCallbackImpl());
        }
    }

    @Test
    public void atom() throws Exception {
        String atom = "https://www.v2ex.com/feed/tab/tech.xml";
        ResponseBody responseBody = getResponseBody(atom);
        if (responseBody != null) {
            FeedParser.parse(responseBody.charStream(), new FeedCallbackImpl());
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