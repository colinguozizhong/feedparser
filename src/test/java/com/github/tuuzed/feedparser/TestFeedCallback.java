package com.github.tuuzed.feedparser;

import com.github.tuuzed.feedparser.callback.FeedCallback;
import com.github.tuuzed.feedparser.util.Logger;

import java.util.Date;
import java.util.List;

public class TestFeedCallback extends FeedCallback {
    private final Logger logger = Logger.getLogger(this.getClass());

    public TestFeedCallback(boolean debug) {
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
    public void feed(String type, String version) {
        logger.info("feed: type: " + type + ",version: " + version);
    }

    @Override
    public void title(String type, String language, String value) {
        logger.info("title: type: " + type
                + ",language: " + language
                + ",value: " + value
        );
    }

    @Override
    public void subtitle(String type, String language, String value) {
        logger.info("subtitle: type: " + type
                + ",language: " + language
                + ",value: " + value
        );
    }

    @Override
    public void links(String rel, String type, String href) {
        logger.info("links: rel: " + rel
                + ",type: " + type
                + ",href: " + href
        );
    }

    @Override
    public void link(String value) {
        logger.info("link: " + value);
    }

    @Override
    public void rights(String type, String language, String value) {
        logger.info("rights: type: " + type
                + ",language: " + language
                + ",value: " + value
        );
    }

    @Override
    public void language(String value) {
        logger.info("language: " + value);
    }

    @Override
    public void generator(String value) {
        logger.info("generator: " + value);
    }

    @Override
    public void skipDays(List<String> skipDays) {
        logger.info("skipDays: " + skipDays);
    }

    @Override
    public void skipHours(List<String> skipHours) {
        logger.info("skipHours: " + skipHours);
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
    public void ttl(String ttl) {
        logger.info("ttl: " + ttl);
    }

    @Override
    public void webMaster(String webMaster) {
        logger.info("webMaster: " + webMaster);
    }

    @Override
    public void rating(String rating) {
        logger.info("rating: " + rating);
    }

    @Override
    public void authors(String value) {
        logger.info("authors: " + value);
    }


    @Override
    public void docs(String docs) {
        logger.info("docs: " + docs);
    }

    @Override
    public void cloud(String domain, String port, String path, String registerProcedure, String protocol) {
        logger.info("cloud: domain: " + domain
                + ",port: " + port
                + ",path: " + path
                + ",registerProcedure: " + registerProcedure
                + ",protocol: " + protocol
        );
    }

    @Override
    public void tags(String tag, String domain) {
        logger.info("tags: tag: " + tag
                + ",domain: " + domain
        );
    }


    @Override
    public void error(Throwable throwable) {
        logger.error("error");
        logger.error(throwable);
    }

    @Override
    public void lethalError(Throwable throwable) {
        logger.error("lethalError");
        logger.error(throwable);
    }
}
