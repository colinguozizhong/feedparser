package io.tuuzed.tuuzed.rssparser.callback;

import java.util.Date;

/**
 * @author TuuZed
 */
public abstract class DefaultItemCallback implements RssParserCallback.ItemCallback {
    @Override
    public void begin() {

    }

    @Override
    public void title(String title) {

    }

    @Override
    public void description(String description) {

    }

    @Override
    public void link(String link) {

    }

    @Override
    public void author(String author) {

    }

    @Override
    public void category(String category, String domain) {

    }

    @Override
    public void comments(String comments) {

    }

    @Override
    public void enclosure(String length, String type, String url) {

    }

    @Override
    public void guid(String guid, boolean isPermaLink) {

    }

    @Override
    public void source(String source, String url) {

    }

    @Override
    public void pubDate(Date pubDate) {

    }

    @Override
    public void end() {

    }
}
