package com.github.tuuzed.feedparser;

import okhttp3.OkHttpClient;
import org.junit.Test;

import java.util.Date;
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
        feedParser.parse("http://news.qq.com/newsgn/rss_newsgn.xml", new FeedCallback() {
            @Override
            public void rss(String version) {
                super.rss(version);
                System.out.println("version = " + version);
            }

            @Override
            public void itemBegin() {
                super.itemBegin();
                System.out.println("----------------- itemBegin -----------------");
            }

            @Override
            public void itemTitle(String title) {
                super.itemTitle(title);
                System.out.println("title = " + title);
            }

            @Override
            public void itemLink(String link) {
                super.itemLink(link);
                System.out.println("link = " + link);
            }

            @Override
            public void itemAuthor(String author) {
                super.itemAuthor(author);
                System.out.println("author = " + author);
            }

            @Override
            public void itemCategory(String category, String domain) {
                super.itemCategory(category, domain);
                System.out.println("category = " + category + "; domain = " + domain);
            }

            @Override
            public void itemDescription(String description) {
                super.itemDescription(description);
                System.out.println("description = " + description);
            }

            @Override
            public void itemPubDate(Date pubDate, String strPubDate) {
                super.itemPubDate(pubDate, strPubDate);
                System.out.println("pubDate = " + pubDate + "; strPubDate = " + strPubDate);

            }


            @Override
            public void itemComments(String comments) {
                super.itemComments(comments);
                System.out.println("comments = " + comments);
            }

            @Override
            public void itemEnclosure(String length, String type, String url) {
                super.itemEnclosure(length, type, url);
                System.out.println("length = " + length + "; type = " + type + "; url = " + url);
            }

            @Override
            public void itemGuid(String guid, boolean isPermaLink) {
                super.itemGuid(guid, isPermaLink);
                System.out.println("guid = " + guid + "; isPermaLink = " + isPermaLink);
            }

            @Override
            public void itemSource(String source, String url) {
                super.itemSource(source, url);
                System.out.println("source = " + source + "; url = " + url);
            }

            @Override
            public void itemEnd() {
                super.itemEnd();
                System.out.println("----------------- itemEnd -----------------");
                System.out.println();
            }
        });
    }
}
