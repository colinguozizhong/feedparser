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
package com.tuuzed.feedparser.internal

import com.tuuzed.feedparser.FeedCallback
import com.tuuzed.feedparser.ext.getAttrs
import com.tuuzed.feedparser.ext.getNextText
import com.tuuzed.feedparser.util.DateParser
import org.xmlpull.v1.XmlPullParser

internal class RssParser(callback: FeedCallback) : GenericParser(callback) {
    private var isStartChannel = false
    private var isStartImage = false
    private var isStartTextInput = false
    private var isStartSkipDays = false
    private var isStartSkipHours = false
    private var isStartItem = false
    private var tmpList = mutableListOf<String>()

    override fun startTag(tag: String, xmlPullParser: XmlPullParser) {
        when (tag) {
            "rss" -> {
                callback.start()
            }
            "channel" -> {
                isStartChannel = true
            }
            "image" -> {
                isStartImage = true
            }
            "skipDays" -> {
                tmpList.clear()
                isStartSkipDays = true
            }
            "skipHours" -> {
                tmpList.clear()
                isStartSkipHours = true
            }
            "textInput" -> {
                isStartTextInput = true
            }
            "item" -> {
                isStartItem = true
                callback.itemStart()
            }
        }
        if (isStartChannel) {
            if (isStartItem) {
                // item
                item(tag, xmlPullParser)
            } else if (isStartImage) {
                // image
            } else if (isStartSkipDays && "day" == tag) {
                // skipDays
                val skipDay = xmlPullParser.getNextText()
                if (skipDay != null) {
                    tmpList.add(skipDay)
                }
                tmpList.add(xmlPullParser.getNextText()!!)
            } else if (isStartSkipHours && "hour" == tag) {
                // skipHours
                val skipHour = xmlPullParser.getNextText()
                if (skipHour != null) {
                    tmpList.add(skipHour)
                }
            } else if (isStartTextInput) {
                // textInput
            } else {
                // channel
                channel(tag, xmlPullParser)
            }
        }
    }

    override fun endTag(tag: String) {
        when (tag) {
            "rss" -> {
                callback.end()
            }
            "channel" -> {
                isStartChannel = false
            }
            "image" -> {
                isStartImage = false
            }
            "skipDays" -> {
                isStartSkipDays = false
                callback.skipDays(tmpList)
                tmpList.clear()
            }
            "skipHours" -> {
                isStartSkipHours = false
                callback.skipHours(tmpList)
                tmpList.clear()
            }
            "textInput" -> {
                isStartTextInput = false
            }
            "item" -> {
                callback.itemEnd()
                isStartItem = false
            }
        }
    }

    private fun channel(tag: String, xmlPullParser: XmlPullParser) {
        when (tag) {
            "title" -> {
                val title = xmlPullParser.getNextText()
                if (title != null) {
                    callback.title(title)
                }
            }
            "description" -> {
                val subtitle = xmlPullParser.getNextText()
                if (subtitle != null) {
                    callback.subtitle(subtitle)
                }
            }
            "link" -> {
                val link = xmlPullParser.getNextText()
                if (link != null) {
                    callback.link(link)
                }
            }
            "generator" -> {
                val generator = xmlPullParser.getNextText()
                if (generator != null) {
                    callback.generator(generator)
                }
            }
        }
    }

    private fun item(tag: String, xmlPullParser: XmlPullParser) {
        when (tag) {
            "author" -> {
                val author = xmlPullParser.getNextText()
                if (author != null) {
                    callback.itemAuthor(author)
                }
            }
            "comments" -> {
                val comments = xmlPullParser.getNextText()
                if (comments != null) {
                    callback.itemComments(comments)
                }
            }
            "body" -> {
                val attrs = xmlPullParser.getAttrs()
                val content = attrs["content"]
                if (content != null) {
                    callback.itemContent(content, attrs["type"], attrs["language"])
                }
            }
            "contributor" -> {
                val attrs = xmlPullParser.getAttrs()
                val contributor = attrs["name"]
                if (contributor != null) {
                    callback.itemContributor(contributor, attrs["href"], attrs["email"])
                }
            }
            "enclosure" -> {
                val attrs = xmlPullParser.getAttrs()
                callback.itemEnclosure(attrs["length"], attrs["type"], attrs["url"])
            }
            "expirationDate" -> {
                val published = xmlPullParser.getNextText()
                if (published != null) {
                    callback.itemPublished(DateParser.parse(published), published)
                }
            }
            "guid" -> {
                val guid = xmlPullParser.getNextText()
                if (guid != null) {
                    callback.itemId(guid)
                }
            }
            "link" -> {
                val link = xmlPullParser.getNextText()
                if (link != null) {
                    callback.itemLink(link)
                }
            }
            "pubDate" -> {
                val published = xmlPullParser.getNextText()
                if (published != null) {
                    callback.itemPublished(DateParser.parse(published), published)
                }
            }
            "source" -> {
                val source = xmlPullParser.getNextText()
                if (source != null) {
                    callback.itemSource(source)
                }
            }
            "description" -> {
                val summary = xmlPullParser.getNextText()
                if (summary != null) {
                    callback.itemSummary(summary)
                }
            }
            "category" -> {
                val category = xmlPullParser.getNextText()
                if (category != null) {
                    callback.itemCategory(category)
                }
            }
            "title" -> {
                val title = xmlPullParser.getNextText()
                if (title != null) {
                    callback.itemTitle(title)
                }
            }
        }
    }
}