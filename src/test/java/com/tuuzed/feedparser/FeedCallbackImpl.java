package com.tuuzed.feedparser;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;


public class FeedCallbackImpl implements FeedCallback {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void start() {
        logger.info("");
    }

    @Override
    public void title(@NotNull String title, @Nullable String type) {
        logger.info("title: {}, type: {}", title, type);
    }

    @Override
    public void subtitle(@NotNull String subtitle, @Nullable String type) {
        logger.info("subtitle: {}, type: {}", subtitle, type);
    }

    @Override
    public void link(@NotNull String link, @Nullable String type, @Nullable String title) {
        logger.info("link: {}, type: {}, title: {}", link, type, title);
    }

    @Override
    public void copyright(@NotNull String copyright, @Nullable String type) {
        logger.info("copyright: {}, type: {}", copyright, type);
    }

    @Override
    public void generator(@NotNull String generator, @Nullable String uri, @Nullable String version) {
        logger.info("generator: {}, uri: {}, version: {}", generator, uri, version);
    }

    @Override
    public void end() {
        logger.info("");
    }

    @Override
    public void error(@Nullable Throwable throwable) {

    }

    @Override
    public void fatalError(@Nullable Throwable throwable) {

    }

    @Override
    public void skipDays(@NotNull List<String> skipDays) {
        logger.info("skipDays: {}", skipDays);
    }

    @Override
    public void skipHours(@NotNull List<String> skipHours) {
        logger.info("skipHours: {}", skipHours);
    }

    @Override
    public void itemStart() {
        logger.info("");
    }

    @Override
    public void itemAuthor(@NotNull String author, @Nullable String uri, @Nullable String email) {
        logger.info("author: {}, uri: {}, email: {}", author, uri, email);
    }

    @Override
    public void itemComments(@NotNull String comments) {
        logger.info("comments: {}", comments);
    }

    @Override
    public void itemContent(@NotNull String content, @Nullable String type, @Nullable String language) {
        logger.info("content: {}, type: {}, language: {}", content, type, language);

    }

    @Override
    public void itemContributor(@NotNull String contributor, @Nullable String href, @Nullable String email) {
        logger.info("contributor: {}, href: {}, email: {}", contributor, href, email);
    }

    @Override
    public void itemEnclosure(@Nullable String length, @Nullable String type, @Nullable String url) {
        logger.info("length: {}, type: {}, url: {}", length, type, url);
    }

    @Override
    public void itemExpired(@Nullable Date expired, @NotNull String rawExpired) {
        logger.info("expired: {}, rawExpired: {}", expired, rawExpired);
    }

    @Override
    public void itemId(@NotNull String id) {
        logger.info("id: {}", id);
    }

    @Override
    public void itemLink(@NotNull String link, @Nullable String type, @Nullable String title) {
        logger.info("link: {}, type: {}, title: {}", link, type, title);
    }

    @Override
    public void itemPublished(@Nullable Date published, @NotNull String rawPublished) {
        logger.info("published: {}, rawPublished: {}", published, rawPublished);
    }

    @Override
    public void itemSource(@NotNull String source) {
        logger.info("source: {}", source);
    }

    @Override
    public void itemSummary(@NotNull String summary, @Nullable String type, @Nullable String language) {
        logger.info("summary: {}, type: {}, language: {}", summary, type, language);
    }

    @Override
    public void itemCategory(@NotNull String category, @Nullable String term, @Nullable String scheme) {
        logger.info("category: {}, term: {}, scheme: {}", category, term, scheme);
    }

    @Override
    public void itemTitle(@NotNull String title) {
        logger.info("updated: {}", title);
    }

    @Override
    public void itemUpdated(@Nullable Date updated, @NotNull String rawUpdated) {
        logger.info("updated: {}, rawUpdated: {}", updated, rawUpdated);
    }

    @Override
    public void itemEnd() {
        logger.info("");
    }


}
