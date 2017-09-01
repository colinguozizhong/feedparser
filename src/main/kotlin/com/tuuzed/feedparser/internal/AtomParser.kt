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
import com.tuuzed.feedparser.ext.clear
import com.tuuzed.feedparser.ext.getNextText
import com.tuuzed.feedparser.internal.util.DateParser
import org.xmlpull.v1.XmlPullParser

internal class AtomParser(private val callback: FeedCallback) : GenericParser() {
    private var isBeginEntry = false
    private var isBeginEntryAuthor = false
    private var isBeginEntryContributor = false
    private val tmpStrArr = arrayOfNulls<String>(3)
    override fun startTag(tag: String, xmlPullParser: XmlPullParser) {
        when (tag) {
            "feed" -> {
                callback.begin()
            }
            "entry" -> {
                isBeginEntry = true
                callback.entryBegin()
            }
        }
        if (isBeginEntry) entry(tag, xmlPullParser)
        else feed(tag, xmlPullParser)
    }

    private fun feed(tagName: String, xmlPullParser: XmlPullParser) {
        when (tagName) {
            "title" -> {
                callback.title(xmlPullParser.getNextText())
            }
            "subtitle" -> {
                callback.subtitle(xmlPullParser.getNextText())
            }
            "link" -> {
                val attrs = xmlPullParser.attrs()
                callback.link(attrs["type"], attrs["href"], attrs["title"])
            }
        }
    }

    private fun entry(tag: String, xmlPullParser: XmlPullParser) {
        when (tag) {
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
                val attrs = xmlPullParser.attrs()
                callback.entryContent(attrs["type"], attrs["language"], xmlPullParser.getNextText())
            }
            "contributor" -> {
                isBeginEntryContributor = true
            }
            "id" -> {
                callback.entryId(xmlPullParser.getNextText())
            }
            "link" -> {
                val attrs = xmlPullParser.attrs()
                callback.entryLink(attrs["type"], attrs["href"], attrs["title"])
            }
            "published" -> {
                val text = xmlPullParser.getNextText()
                callback.entryPublished(DateParser.parse(text), text)
            }
            "summary" -> {
                val attrs = xmlPullParser.attrs()
                callback.entrySummary(attrs["type"], attrs["language"], xmlPullParser.getNextText())
            }
            "category" -> {
                val attrs = xmlPullParser.attrs()
                callback.entryTags(attrs["term"], attrs["scheme"], xmlPullParser.getNextText())
            }
            "title" -> {
                callback.entryTitle(xmlPullParser.getNextText())
            }
            "updated" -> {
                val text = xmlPullParser.getNextText()
                callback.entryUpdated(DateParser.parse(text), text)
            }
        }
    }

    override fun endTag(tag: String) {
        when (tag) {
            "feed" -> callback.end()
            "entry" -> {
                isBeginEntry = false
                callback.entryEnd()
            }
            "author" -> {
                callback.entryAuthor(tmpStrArr[0], tmpStrArr[1], tmpStrArr[2])
                tmpStrArr.clear()
                isBeginEntryAuthor = false
            }
            "contributor" -> {
                callback.entryContributor(tmpStrArr[0], tmpStrArr[1], tmpStrArr[2])
                tmpStrArr.clear()
                isBeginEntryContributor = false
            }
        }
    }
}