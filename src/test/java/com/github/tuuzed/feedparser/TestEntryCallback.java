package com.github.tuuzed.feedparser;

import com.github.tuuzed.feedparser.callback.EntryCallback;
import com.github.tuuzed.feedparser.util.Logger;

import java.util.Date;

public class TestEntryCallback implements EntryCallback {

    private final Logger logger = Logger.getLogger(this.getClass());

    public TestEntryCallback(boolean debug) {
        logger.setDebug(debug);
    }

    @Override
    public void begin() {
        logger.info("begin");
    }

    @Override
    public void end() {
        logger.info("end");
    }


    @Override
    public void title(String value) {
        logger.info("title: " + value);
    }


    @Override
    public void link(String value) {
        logger.info("link: " + value);
    }

    @Override
    public void authors(String name) {
        logger.info("authors: " + name);
    }

    @Override
    public void tags(String tag, String domain) {
        logger.info("tags: tag: " + tag
                + ",domain: " + domain
        );
    }

    @Override
    public void published(Date date, String strDate) {
        logger.info("published: date: " + date
                + ",strDate: " + strDate
        );
    }

    @Override
    public void updated(Date date, String strDate) {
        logger.info("updated: date: " + date
                + ",strDate: " + strDate
        );
    }

    @Override
    public void comments(String value) {
        logger.info("comments: " + value);
    }

    @Override
    public void id(String value, Boolean isPermaLink) {
        logger.info("id: value: " + value
                + ",isPermaLink: " + isPermaLink
        );
    }

    @Override
    public void enclosure(String length, String type, String url) {
        logger.info("enclosure: length: " + length
                + ",type: " + type
                + ",url: " + url
        );
    }

    @Override
    public void source(String value, String link) {
        logger.info("source: value: " + value
                + ",link: " + link);
    }

    @Override
    public void summary(String type, String language, String value) {
        logger.info("summary: type: " + type
                + ",language: " + language
                + ",value: " + value
        );
    }

    @Override
    public void content(String type, String language, String value) {
        logger.info("content: type: " + type
                + ",language: " + language
                + ",value: " + value
        );
    }

}
