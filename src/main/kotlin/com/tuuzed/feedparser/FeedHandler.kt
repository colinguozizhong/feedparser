package com.tuuzed.feedparser

import java.util.Date

interface FeedHandler {
    /**
     * 开始解析
     */
    fun begin()

    /**
     * /rss/channel/title
     * /atom10:feed/atom10:title
     */
    fun title(title: String?)

    /**
     * /atom10:feed/atom10:subtitle
     * /rss/channel/description
     */
    fun subtitle(subtitle: String?)

    /**
     * /atom10:feed/atom10:link
     * /rss/channel/link
     */
    fun link(type: String?, href: String?, title: String?)

    /**
     * 解析结束
     */
    fun end()

    /**
     * 错误
     */
    fun error(throwable: Throwable?)

    /**
     * 致命的错误
     */
    fun fatalError(throwable: Throwable?)

    // skipDays
    fun skipDays(skipDays: List<String>)

    // skipHours
    fun skipHours(skipHours: List<String>)


    fun entryBegin()

    /**
     * /atom10:feed/atom10:entry/atom10:author
     * /rss/channel/item/author
     */
    fun entryAuthor(name: String?, uri: String?, email: String?)

    /**
     * /rss/channel/item/comments
     */
    fun entryComments(comments: String?)

    /**
     * /atom10:feed/atom10:entry/atom10:content
     * /rss/channel/item/body
     */
    fun entryContent(type: String?, language: String?, content: String?)

    /**
     * /atom10:feed/atom10:entry/atom10:contributor
     * /rss/channel/item/contributor
     */
    fun entryContributor(name: String?, href: String?, email: String?)

    /**
     * /rss/channel/item/enclosure
     */
    fun entryEnclosure(length: String?, type: String?, url: String?)

    /**
     * /rss/channel/item/expirationDate
     */
    fun entryExpired(expired: Date?, strExpired: String?)

    /**
     * /atom10:feed/atom10:entry/atom10:id
     * /rss/channel/item/guid
     */
    fun entryId(id: String?)

    /**
     * /atom10:feed/atom10:entry/atom10:link@href
     * /rss/channel/item/link
     */
    fun entryLink(type: String?, href: String?, title: String?)

    /**
     * /atom10:feed/atom10:entry/atom10:published
     * /rss/channel/item/pubDate
     */
    fun entryPublished(published: Date?, strPublished: String?)

    /**
     * /rss/channel/item/source
     */
    fun entrySource(source: String?)

    /**
     * /atom10:feed/atom10:entry/atom10:summary
     * /rss/channel/item/description
     */
    fun entrySummary(type: String?, language: String?, summary: String?)

    /**
     * /atom10:feed/atom10:entry/category
     * /rss/channel/item/category
     */
    fun entryTags(term: String?, scheme: String?, tag: String?)

    /**
     * /atom10:feed/atom10:entry/atom10:title
     * /rss/channel/item/title
     */
    fun entryTitle(title: String?)

    /**
     * /atom10:feed/atom10:entry/atom10:updated
     */
    fun entryUpdated(updated: Date?, strUpdated: String?)

    fun entryEnd()
}