package com.github.tuuzed.feedparser;

import com.github.tuuzed.feedparser.callback.TextInputCallback;
import com.github.tuuzed.feedparser.util.Logger;

public class TestTextInputCallback implements TextInputCallback {
    private final Logger logger = Logger.getLogger(this.getClass());

    public TestTextInputCallback(boolean debug) {
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
    public void description(String value) {
        logger.info("description: " + value);
    }

    @Override
    public void name(String value) {
        logger.info("name: " + value);
    }

    @Override
    public void link(String value) {
        logger.info("link: " + value);
    }
}
