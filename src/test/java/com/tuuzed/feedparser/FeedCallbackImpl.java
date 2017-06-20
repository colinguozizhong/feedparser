package com.tuuzed.feedparser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;


public class FeedCallbackImpl implements FeedCallback {
    public final Logger logger = LoggerFactory.getLogger(FeedParserTest.class);


    @Override
    public void begin() {
        print("begin");
    }

    @Override
    public void title(String title) {
        print("title: {}", title);
    }

    @Override
    public void subtitle(String subtitle) {
        print("subtitle: {}", subtitle);
    }

    @Override
    public void link(String type, String href, String title) {
        print("type: {}, href: {}, title: {}", type, href, title);
    }

    @Override
    public void end() {
        print("end");
    }

    @Override
    public void error(Throwable throwable) {
        logger.info(throwable.getMessage(), throwable);
    }

    @Override
    public void fatalError(Throwable throwable) {
        logger.info(throwable.getMessage(), throwable);
    }

    @Override
    public void skipDays(List<String> skipDays) {
        print("skipDays: {}", skipDays);
    }

    @Override
    public void skipHours(List<String> skipHours) {
        print("skipHours: {}", skipHours);
    }

    @Override
    public void entryBegin() {
        print("\n\nbegin\n\n");
    }

    @Override
    public void entryAuthor(String name, String uri, String email) {
        print("name: {}, uri: {}, email: {}", name, uri, email);
    }

    @Override
    public void entryTitle(String title) {
        print("title: {}", title);
    }

    @Override
    public void entryId(String id) {
        print("id: {}", id);
    }

    @Override
    public void entryLink(String type, String href, String title) {
        print("type: {}, href: {}, title: {}", type, href, title);
    }

    @Override
    public void entryPublished(Date published, String strPublished) {
        print("published: {} , strPublished: {}", published, strPublished);
    }

    @Override
    public void entrySource(String source) {
        print("source: {}", source);
    }

    @Override
    public void entryUpdated(Date updated, String strUpdated) {
        print("updated: {}, strUpdated: {}", updated, strUpdated);
    }

    @Override
    public void entryComments(String comments) {
        print("comments: {}", comments);
    }

    @Override
    public void entrySummary(String type, String language, String summary) {
        print("type: {}, language: {}, summary: {}", type, language, summary);
    }

    @Override
    public void entryTags(String term, String scheme, String tag) {
        print("term: {}, scheme: {}, tag: {}", term, scheme, tag);
    }

    @Override
    public void entryContent(String type, String language, String content) {
        print("type: {}, language: {}, content: {}", type, language, content);
    }

    @Override
    public void entryContributor(String name, String href, String email) {
        print("name: {}, href: {}, email: {}", name, href, email);

    }

    @Override
    public void entryEnclosure(String length, String type, String url) {
        print("length: {}, type: {}, url: {}", length, type, type);
    }

    @Override
    public void entryExpired(Date expired, String strExpired) {
        print("expired: {}, strExpired: {}", expired, strExpired);
    }

    @Override
    public void entryEnd() {
        print("end");
    }

    private String delWrap(String text) {
        if (text == null) return null;
        return text.replaceAll("\r|\n", "");
    }

    private void print(String format, Object... objects) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof String) {
                objects[i] = delWrap((String) objects[i]);
            }
        }
        logger.info(format, objects);
    }
}
