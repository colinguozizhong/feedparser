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

import java.util.*

interface FeedCallback {
    /**
     * 开始解析
     */
    fun start()

    /**
     * /feed/title
     * /rss/channel/title
     */
    fun title(title: String, type: String? = null)

    /**
     * /feed/subtitle
     * /rss/channel/description
     */
    fun subtitle(subtitle: String, type: String? = null)

    /**
     * /feed/link
     * /rss/channel/link
     */
    fun link(link: String, type: String? = null, title: String? = null)

    /**
     *  /feed/rights
     *  /rss/channel/copyright
     */
    fun copyright(copyright: String, type: String? = null)

    /**
     *  /feed/generator
     *  /rss/channel/generator
     */
    fun generator(generator: String, uri: String? = null, version: String? = null)

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

    /**
     *  /rss/channel/skipDays
     */
    fun skipDays(skipDays: List<String>)

    /**
     * /rss/channel/skipHours
     */
    fun skipHours(skipHours: List<String>)


    fun itemStart()

    /**
     * /feed/entry/author
     * /rss/channel/item/author
     */
    fun itemAuthor(author: String, uri: String? = null, email: String? = null)

    /**
     * /rss/channel/item/comments
     */
    fun itemComments(comments: String)

    /**
     * /feed/entry/content
     * /rss/channel/item/body
     */
    fun itemContent(content: String, type: String? = null, language: String? = null)

    /**
     * /feed/entry/contributor
     * /rss/channel/item/contributor
     */
    fun itemContributor(contributor: String, href: String? = null, email: String? = null)

    /**
     * /rss/channel/item/enclosure
     */
    fun itemEnclosure(length: String? = null, type: String? = null, url: String? = null)

    /**
     * /rss/channel/item/expirationDate
     */
    fun itemExpired(expired: Date?, rawExpired: String)

    /**
     * /feed/entry/id
     * /rss/channel/item/guid
     */
    fun itemId(id: String)

    /**
     * /feed/entry/link@href
     * /rss/channel/item/link
     */
    fun itemLink(link: String, type: String? = null, title: String? = null)

    /**
     * /feed/entry/published
     * /rss/channel/item/pubDate
     */
    fun itemPublished(published: Date?, rawPublished: String)

    /**
     * /rss/channel/item/source
     */
    fun itemSource(source: String)

    /**
     * /feed/entry/summary
     * /rss/channel/item/description
     */
    fun itemSummary(summary: String, type: String? = null, language: String? = null)

    /**
     * /feed/entry/category
     * /rss/channel/item/category
     */
    fun itemCategory(category: String, term: String? = null, scheme: String? = null)

    /**
     * /feed/entry/title
     * /rss/channel/item/title
     */
    fun itemTitle(title: String)

    /**
     * /feed/entry/updated
     */
    fun itemUpdated(updated: Date?, rawUpdated: String)

    fun itemEnd()
}