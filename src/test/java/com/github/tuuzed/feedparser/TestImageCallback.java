package com.github.tuuzed.feedparser;

import com.github.tuuzed.feedparser.callback.ImageCallback;
import com.github.tuuzed.feedparser.util.Logger;


public class TestImageCallback implements ImageCallback {

    private final Logger logger = Logger.getLogger(this.getClass());

    public TestImageCallback(boolean debug) {
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
        logger.info("value: " + value);
    }

    @Override
    public void link(String value) {
        logger.info("link: " + value);
    }

    @Override
    public void href(String value) {
        logger.info("href: " + value);
    }

    @Override
    public void description(String value) {
        logger.info("description: " + value);
    }

    @Override
    public void width(String value) {
        logger.info("width: " + value);
    }

    @Override
    public void height(String value) {
        logger.info("height: " + value);
    }


}
