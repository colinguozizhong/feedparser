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
import com.tuuzed.feedparser.ext.clear
import com.tuuzed.feedparser.ext.getAttrs
import com.tuuzed.feedparser.ext.getNextText
import com.tuuzed.feedparser.internal.util.DateUtils
import org.xmlpull.v1.XmlPullParser

internal class AtomParser : GenericParser() {
    private var isBeginEntry = false
    private var isBeginEntryAuthor = false
    private var isBeginEntryContributor = false
    private val tmpStrArr = arrayOfNulls<String>(3)

    override fun startTag(tagName: String, pullParser: XmlPullParser, handler: FeedHandler) {
        when (tagName) {
            "feed" -> {
                handler.begin()
            }
            "entry" -> {
                isBeginEntry = true
                handler.entryBegin()
            }
        }
        if (isBeginEntry) entry(tagName, pullParser, handler)
        else feed(tagName, pullParser, handler)
    }

    private fun feed(tagName: String, xmlPullParser: XmlPullParser, handler: FeedHandler) {
        when (tagName) {
            "title" -> {
                handler.title(xmlPullParser.getNextText())
            }
            "subtitle" -> {
                handler.subtitle(xmlPullParser.getNextText())
            }
            "link" -> {
                val attrs = xmlPullParser.getAttrs()
                handler.link(attrs["type"], attrs["href"], attrs["title"])
            }
        }
    }

    private fun entry(tagName: String, xmlPullParser: XmlPullParser, handler: FeedHandler) {
        when (tagName) {
            "author" -> {
                isBeginEntryAuthor = true
            }
            "name" -> {
                if (isBeginEntryAuthor || isBeginEntryContributor) tmpStrArr[0] = xmlPullParser.getNextText()
            }
            "uri" -> {
                if (isBeginEntryAuthor || isBeginEntryContributor) tmpStrArr[1] = xmlPullParser.getNextText()
            }
            "email" -> {
                if (isBeginEntryAuthor || isBeginEntryContributor) tmpStrArr[2] = xmlPullParser.getNextText()
            }
            "content" -> {
                val attrs = xmlPullParser.getAttrs()
                handler.entryContent(attrs["type"], attrs["language"], xmlPullParser.getNextText())
            }
            "contributor" -> {
                isBeginEntryContributor = true
            }
            "id" -> {
                handler.entryId(xmlPullParser.getNextText())
            }
            "link" -> {
                val attrs = xmlPullParser.getAttrs()
                handler.entryLink(attrs["type"], attrs["href"], attrs["title"])
            }
            "published" -> {
                val text = xmlPullParser.getNextText()
                handler.entryPublished(DateUtils.parse(text), text)
            }
            "summary" -> {
                val attrs = xmlPullParser.getAttrs()
                handler.entrySummary(attrs["type"], attrs["language"], xmlPullParser.getNextText())
            }
            "category" -> {
                val attrs = xmlPullParser.getAttrs()
                handler.entryTags(attrs["term"], attrs["scheme"], xmlPullParser.getNextText())
            }
            "title" -> {
                handler.entryTitle(xmlPullParser.getNextText())
            }
            "updated" -> {
                val text = xmlPullParser.getNextText()
                handler.entryUpdated(DateUtils.parse(text), text)
            }
        }
    }

    override fun endTag(tagName: String, handler: FeedHandler) {
        when (tagName) {
            "feed" -> handler.end()
            "entry" -> {
                isBeginEntry = false
                handler.entryEnd()
            }
            "author" -> {
                handler.entryAuthor(tmpStrArr[0], tmpStrArr[1], tmpStrArr[2])
                tmpStrArr.clear()
                isBeginEntryAuthor = false
            }
            "contributor" -> {
                handler.entryContributor(tmpStrArr[0], tmpStrArr[1], tmpStrArr[2])
                tmpStrArr.clear()
                isBeginEntryContributor = false
            }
        }
    }
}