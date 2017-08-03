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

import com.tuuzed.feedparser.FeedHandler
import com.tuuzed.feedparser.ext.getAttrs
import com.tuuzed.feedparser.ext.getNextText
import com.tuuzed.feedparser.internal.util.DateUtils
import org.xmlpull.v1.XmlPullParser

internal class RssParser : GenericParser() {
    private var isBeginChannel = false
    private var isBeginImage = false
    private var isBeginTextInput = false
    private var isBeginSkipDays = false
    private var isBeginSkipHours = false
    private var isBeginItem = false
    private var tmpList = mutableListOf<String>()

    override fun startTag(tagName: String, pullParser: XmlPullParser, handler: FeedHandler) {
        when (tagName) {
            "rss" -> {
                handler.begin()
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
                handler.entryBegin()
            }
        }
        if (isBeginChannel) {
            if (isBeginItem) {
                // item
                item(tagName, pullParser, handler)
            } else if (isBeginImage) {
                // image
            } else if (isBeginSkipDays && "day" == tagName) {
                // skipDays
                tmpList.add(pullParser.getNextText()!!)
            } else if (isBeginSkipHours && "hour" == tagName) {
                // skipHours
                tmpList.add(pullParser.getNextText()!!)
            } else if (isBeginTextInput) {
                // textInput
            } else {
                // channel
                channel(tagName, pullParser, handler)
            }
        }
    }

    override fun endTag(tagName: String, handler: FeedHandler) {
        when (tagName) {
            "rss" -> {
                handler.end()
            }
            "channel" -> {
                isBeginChannel = false
            }
            "image" -> {
                isBeginImage = false
            }
            "skipDays" -> {
                isBeginSkipDays = false
                handler.skipDays(tmpList)
            }
            "skipHours" -> {
                isBeginSkipHours = false
                handler.skipHours(tmpList)
            }
            "textInput" -> {
                isBeginTextInput = false
            }
            "item" -> {
                handler.entryEnd()
                isBeginItem = false
            }
        }
    }

    private fun channel(tagName: String, xmlPullParser: XmlPullParser, handler: FeedHandler) {
        when (tagName) {
            "title" -> {
                handler.title(xmlPullParser.getNextText())
            }
            "description" -> {
                handler.subtitle(xmlPullParser.getNextText())
            }
            "link" -> {
                handler.link(href = xmlPullParser.getNextText())
            }
        }
    }

    private fun item(tagName: String, xmlPullParser: XmlPullParser, handler: FeedHandler) {
        when (tagName) {
            "author" -> {
                handler.entryAuthor(name = xmlPullParser.getNextText())
            }
            "comments" -> {
                handler.entryComments(xmlPullParser.getNextText())
            }
            "body" -> {
                val attrs = xmlPullParser.getAttrs()
                handler.entryContent(attrs["type"], attrs["language"], attrs["content"])
            }
            "contributor" -> {
                val attrs = xmlPullParser.getAttrs()
                handler.entryContributor(attrs["name"], attrs["href"], attrs["email"])
            }
            "enclosure" -> {
                val attrs = xmlPullParser.getAttrs()
                handler.entryEnclosure(attrs["length"], attrs["type"], attrs["url"])
            }
            "expirationDate" -> {
                val text = xmlPullParser.getNextText()
                handler.entryPublished(DateUtils.parse(text), text)
            }
            "guid" -> {
                handler.entryId(xmlPullParser.getNextText())
            }
            "link" -> {
                handler.entryLink(href = xmlPullParser.getNextText())
            }
            "pubDate" -> {
                val text = xmlPullParser.getNextText()
                handler.entryPublished(DateUtils.parse(text), text)
            }
            "source" -> {
                handler.entrySource(xmlPullParser.getNextText())
            }
            "description" -> {
                handler.entrySummary(summary = xmlPullParser.getNextText())
            }
            "category" -> {
                handler.entryTags(tag = xmlPullParser.getNextText())
            }
            "title" -> {
                handler.entryTitle(xmlPullParser.getNextText())
            }
        }
    }
}