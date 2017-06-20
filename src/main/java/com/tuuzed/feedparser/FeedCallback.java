package com.tuuzed.feedparser;

import java.util.Date;
import java.util.List;

public interface FeedCallback {
    /**
     * 开始解析
     */
    void begin();

    /**
     * /rss/channel/title
     * /atom10:feed/atom10:title
     */
    void title(String title);

    /**
     * /atom10:feed/atom10:subtitle
     * /rss/channel/description
     */
    void subtitle(String subtitle);

    /**
     * /atom10:feed/atom10:link
     * /rss/channel/link
     */
    void link(String type, String href, String title);

    /**
     * 解析结束
     */
    void end();

    /**
     * 错误
     */
    void error(Throwable throwable);

    /**
     * 致命的错误
     */
    void fatalError(Throwable throwable);

    // skipDays
    void skipDays(List<String> skipDays);

    // skipHours
    void skipHours(List<String> skipHours);


    void entryBegin();

    /**
     * /atom10:feed/atom10:entry/atom10:author
     * /rss/channel/item/author
     */
    void entryAuthor(String name, String uri, String email);

    /**
     * /rss/channel/item/comments
     */
    void entryComments(String comments);

    /**
     * /atom10:feed/atom10:entry/atom10:content
     * /rss/channel/item/body
     */
    void entryContent(String type, String language, String content);

    /**
     * /atom10:feed/atom10:entry/atom10:contributor
     * /rss/channel/item/contributor
     */
    void entryContributor(String name, String href, String email);

    /**
     * /rss/channel/item/enclosure
     */
    void entryEnclosure(String length, String type, String url);

    /**
     * /rss/channel/item/expirationDate
     */
    void entryExpired(Date expired, String strExpired);

    /**
     * /atom10:feed/atom10:entry/atom10:id
     * /rss/channel/item/guid
     */
    void entryId(String id);

    /**
     * /atom10:feed/atom10:entry/atom10:link@href
     * /rss/channel/item/link
     */
    void entryLink(String type, String href, String title);

    /**
     * /atom10:feed/atom10:entry/atom10:published
     * /rss/channel/item/pubDate
     */
    void entryPublished(Date published, String strPublished);

    /**
     * /rss/channel/item/source
     */
    void entrySource(String source);

    /**
     * /atom10:feed/atom10:entry/atom10:summary
     * /rss/channel/item/description
     */
    void entrySummary(String type, String language, String summary);

    /**
     * /atom10:feed/atom10:entry/category
     * /rss/channel/item/category
     */
    void entryTags(String term, String scheme, String tag);

    /**
     * /atom10:feed/atom10:entry/atom10:title
     * /rss/channel/item/title
     */
    void entryTitle(String title);

    /**
     * /atom10:feed/atom10:entry/atom10:updated
     */
    void entryUpdated(Date updated, String strUpdated);

    void entryEnd();
}
