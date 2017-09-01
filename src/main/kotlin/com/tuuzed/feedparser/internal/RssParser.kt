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
    private var isBeginChannel = false
    private var isBeginImage = false
    private var isBeginTextInput = false
    private var isBeginSkipDays = false
    private var isBeginSkipHours = false
    private var isBeginItem = false
    private var tmpList = mutableListOf<String>()

    override fun startTag(tag: String, xmlPullParser: XmlPullParser) {
        when (tag) {
            "rss" -> {
                callback.start()
            }
            "channel" -> {
                isBeginChannel = true
            }
            "image" -> {
                isBeginImage = true
            }
            "skipDays" -> {
                tmpList.clear()
                isBeginSkipDays = true
            }
            "skipHours" -> {
                tmpList.clear()
                isBeginSkipHours = true
            }
            "textInput" -> {
                isBeginTextInput = true
            }
            "item" -> {
                isBeginItem = true
                callback.entryStart()
            }
        }
        if (isBeginChannel) {
            if (isBeginItem) {
                // item
                item(tag, xmlPullParser)
            } else if (isBeginImage) {
                // image
            } else if (isBeginSkipDays && "day" == tag) {
                // skipDays
                val skipDay = xmlPullParser.getNextText()
                if (skipDay != null) {
                    tmpList.add(skipDay)
                }
                tmpList.add(xmlPullParser.getNextText()!!)
            } else if (isBeginSkipHours && "hour" == tag) {
                // skipHours
                val skipHour = xmlPullParser.getNextText()
                if (skipHour != null) {
                    tmpList.add(skipHour)
                }
            } else if (isBeginTextInput) {
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
                isBeginChannel = false
            }
            "image" -> {
                isBeginImage = false
            }
            "skipDays" -> {
                isBeginSkipDays = false
                callback.skipDays(tmpList)
                tmpList.clear()
            }
            "skipHours" -> {
                isBeginSkipHours = false
                callback.skipHours(tmpList)
                tmpList.clear()
            }
            "textInput" -> {
                isBeginTextInput = false
            }
            "item" -> {
                callback.entryEnd()
                isBeginItem = false
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
                callback.link(href = xmlPullParser.getNextText())
            }
        }
    }

    private fun item(tag: String, xmlPullParser: XmlPullParser) {
        when (tag) {
            "author" -> {
                callback.entryAuthor(name = xmlPullParser.getNextText())
            }
            "comments" -> {
                val comments = xmlPullParser.getNextText()
                if (comments != null) {
                    callback.entryComments(comments)
                }
            }
            "body" -> {
                val attrs = xmlPullParser.getAttrs()
                callback.entryContent(attrs["type"], attrs["language"], attrs["content"])
            }
            "contributor" -> {
                val attrs = xmlPullParser.getAttrs()
                callback.entryContributor(attrs["name"], attrs["href"], attrs["email"])
            }
            "enclosure" -> {
                val attrs = xmlPullParser.getAttrs()
                callback.entryEnclosure(attrs["length"], attrs["type"], attrs["url"])
            }
            "expirationDate" -> {
                val published = xmlPullParser.getNextText()
                if (published != null) {
                    callback.entryPublished(DateParser.parse(published), published)
                }
            }
            "guid" -> {
                val guid = xmlPullParser.getNextText()
                if (guid != null) {
                    callback.entryId(guid)
                }
            }
            "link" -> {
                callback.entryLink(href = xmlPullParser.getNextText())
            }
            "pubDate" -> {
                val published = xmlPullParser.getNextText()
                if (published != null) {
                    callback.entryPublished(DateParser.parse(published), published)
                }
            }
            "source" -> {
                val source = xmlPullParser.getNextText()
                if (source != null) {
                    callback.entrySource(source)
                }
            }
            "description" -> {
                callback.entrySummary(summary = xmlPullParser.getNextText())
            }
            "category" -> {
                callback.entryTags(tag = xmlPullParser.getNextText())
            }
            "title" -> {
                val title = xmlPullParser.getNextText()
                if (title != null) {
                    callback.entryTitle(title)
                }
            }
        }
    }
}