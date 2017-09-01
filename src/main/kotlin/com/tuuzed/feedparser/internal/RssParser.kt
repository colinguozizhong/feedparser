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
import com.tuuzed.feedparser.ext.attrs
import com.tuuzed.feedparser.internal.util.DateParser
import org.xmlpull.v1.XmlPullParser

internal class RssParser(private val callback: FeedCallback) : GenericParser() {
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
                callback.begin()
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
                callback.entryBegin()
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
                tmpList.add(xmlPullParser.nextText()!!)
            } else if (isBeginSkipHours && "hour" == tag) {
                // skipHours
                tmpList.add(xmlPullParser.nextText()!!)
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
            }
            "skipHours" -> {
                isBeginSkipHours = false
                callback.skipHours(tmpList)
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
                callback.title(xmlPullParser.nextText())
            }
            "description" -> {
                callback.subtitle(xmlPullParser.nextText())
            }
            "link" -> {
                callback.link(href = xmlPullParser.nextText())
            }
        }
    }

    private fun item(tag: String, xmlPullParser: XmlPullParser) {
        when (tag) {
            "author" -> {
                callback.entryAuthor(name = xmlPullParser.nextText())
            }
            "comments" -> {
                callback.entryComments(xmlPullParser.nextText())
            }
            "body" -> {
                val attrs = xmlPullParser.attrs()
                callback.entryContent(attrs["type"], attrs["language"], attrs["content"])
            }
            "contributor" -> {
                val attrs = xmlPullParser.attrs()
                callback.entryContributor(attrs["name"], attrs["href"], attrs["email"])
            }
            "enclosure" -> {
                val attrs = xmlPullParser.attrs()
                callback.entryEnclosure(attrs["length"], attrs["type"], attrs["url"])
            }
            "expirationDate" -> {
                val text = xmlPullParser.nextText()
                callback.entryPublished(DateParser.parse(text), text)
            }
            "guid" -> {
                callback.entryId(xmlPullParser.nextText())
            }
            "link" -> {
                callback.entryLink(href = xmlPullParser.nextText())
            }
            "pubDate" -> {
                val text = xmlPullParser.nextText()
                callback.entryPublished(DateParser.parse(text), text)
            }
            "source" -> {
                callback.entrySource(xmlPullParser.nextText())
            }
            "description" -> {
                callback.entrySummary(summary = xmlPullParser.nextText())
            }
            "category" -> {
                callback.entryTags(tag = xmlPullParser.nextText())
            }
            "title" -> {
                callback.entryTitle(xmlPullParser.nextText())
            }
        }
    }
}