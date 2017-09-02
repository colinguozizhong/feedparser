package com.tuuzed.feedparser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;

public interface FeedCallback {
    /**
     * 开始解析
     */
    void start();

    /**
     * /feed/title
     * /rss/channel/title
     */
    void title(@NotNull String title, @Nullable String type);

    /**
     * /feed/subtitle
     * /rss/channel/description
     */
    void subtitle(@NotNull String subtitle, @Nullable String type);

    /**
     * /feed/link
     * /rss/channel/link
     */
    void link(@NotNull String link, @Nullable String type, @Nullable String title);

    /**
     * /feed/rights
     * /rss/channel/copyright
     */
    void copyright(@NotNull String copyright, @Nullable String type);

    /**
     * /feed/generator
     * /rss/channel/generator
     */
    void generator(@NotNull String generator, @Nullable String uri, @Nullable String version);

    /**
     * 解析结束
     */
    void end();

    /**
     * 错误
     */
    void error(@Nullable Throwable throwable);

    /**
     * 致命的错误
     */
    void fatalError(@Nullable Throwable throwable);

    /**
     * /rss/channel/skipDays
     */
    void skipDays(@NotNull List<String> skipDays);

    /**
     * /rss/channel/skipHours
     */
    void skipHours(@NotNull List<String> skipHours);


    void itemStart();

    /**
     * /feed/entry/author
     * /rss/channel/item/author
     */
    void itemAuthor(@NotNull String author, @Nullable String uri, @Nullable String email);

    /**
     * /rss/channel/item/comments
     */
    void itemComments(@NotNull String comments);

    /**
     * /feed/entry/content
     * /rss/channel/item/body
     */
    void itemContent(@NotNull String content, @Nullable String type, @Nullable String language);

    /**
     * /feed/entry/contributor
     * /rss/channel/item/contributor
     */
    void itemContributor(@NotNull String contributor, @Nullable String href, @Nullable String email);

    /**
     * /rss/channel/item/enclosure
     */
    void itemEnclosure(@Nullable String length, @Nullable String type, @Nullable String url);

    /**
     * /rss/channel/item/expirationDate
     */
    void itemExpired(@Nullable Date expired, @NotNull String rawExpired);

    /**
     * /feed/entry/id
     * /rss/channel/item/guid
     */
    void itemId(@NotNull String id);

    /**
     * /feed/entry/link@href
     * /rss/channel/item/link
     */
    void itemLink(@NotNull String link, @Nullable String type, @Nullable String title);

    /**
     * /feed/entry/published
     * /rss/channel/item/pubDate
     */
    void itemPublished(@Nullable Date published, @NotNull String rawPublished);

    /**
     * /rss/channel/item/source
     */
    void itemSource(@NotNull String source);

    /**
     * /feed/entry/summary
     * /rss/channel/item/description
     */
    void itemSummary(@NotNull String summary, @Nullable String type, @Nullable String language);

    /**
     * /feed/entry/category
     * /rss/channel/item/category
     */
    void itemCategory(@NotNull String category, @Nullable String term, @Nullable String scheme);

    /**
     * /feed/entry/title
     * /rss/channel/item/title
     */
    void itemTitle(@NotNull String title);

    /**
     * /feed/entry/updated
     */
    void itemUpdated(@Nullable Date updated, @NotNull String rawUpdated);

    void itemEnd();
}
