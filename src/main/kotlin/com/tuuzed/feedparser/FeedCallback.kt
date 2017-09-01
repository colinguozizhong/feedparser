/* Copyright 2017 TuuZed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tuuzed.feedparser

import java.util.Date

interface FeedCallback {
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
    fun link(type: String? = null, href: String? = null, title: String? = null)

    /**
     * 解析结束
     */
    fun end()

    /**
     * 错误
     */
    fun error(throwable: Throwable? = null)

    /**
     * 致命的错误
     */
    fun fatalError(throwable: Throwable? = null)

    // skipDays
    fun skipDays(skipDays: List<String> = emptyList())

    // skipHours
    fun skipHours(skipHours: List<String> = emptyList())


    fun entryBegin()

    /**
     * /atom10:feed/atom10:entry/atom10:author
     * /rss/channel/item/author
     */
    fun entryAuthor(name: String? = null, uri: String? = null, email: String? = null)

    /**
     * /rss/channel/item/comments
     */
    fun entryComments(comments: String? = null)

    /**
     * /atom10:feed/atom10:entry/atom10:content
     * /rss/channel/item/body
     */
    fun entryContent(type: String? = null, language: String? = null, content: String? = null)

    /**
     * /atom10:feed/atom10:entry/atom10:contributor
     * /rss/channel/item/contributor
     */
    fun entryContributor(name: String? = null, href: String? = null, email: String? = null)

    /**
     * /rss/channel/item/enclosure
     */
    fun entryEnclosure(length: String? = null, type: String? = null, url: String? = null)

    /**
     * /rss/channel/item/expirationDate
     */
    fun entryExpired(expired: Date? = null, strExpired: String? = null)

    /**
     * /atom10:feed/atom10:entry/atom10:id
     * /rss/channel/item/guid
     */
    fun entryId(id: String? = null)

    /**
     * /atom10:feed/atom10:entry/atom10:link@href
     * /rss/channel/item/link
     */
    fun entryLink(type: String? = null, href: String? = null, title: String? = null)

    /**
     * /atom10:feed/atom10:entry/atom10:published
     * /rss/channel/item/pubDate
     */
    fun entryPublished(published: Date? = null, strPublished: String? = null)

    /**
     * /rss/channel/item/source
     */
    fun entrySource(source: String? = null)

    /**
     * /atom10:feed/atom10:entry/atom10:summary
     * /rss/channel/item/description
     */
    fun entrySummary(type: String? = null, language: String? = null, summary: String? = null)

    /**
     * /atom10:feed/atom10:entry/category
     * /rss/channel/item/category
     */
    fun entryTags(term: String? = null, scheme: String? = null, tag: String? = null)

    /**
     * /atom10:feed/atom10:entry/atom10:title
     * /rss/channel/item/title
     */
    fun entryTitle(title: String? = null)

    /**
     * /atom10:feed/atom10:entry/atom10:updated
     */
    fun entryUpdated(updated: Date? = null, strUpdated: String? = null)

    fun entryEnd()
}