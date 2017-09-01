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
import com.tuuzed.feedparser.ext.clear
import com.tuuzed.feedparser.ext.getNextText
import com.tuuzed.feedparser.util.DateParser
import org.xmlpull.v1.XmlPullParser

internal class AtomParser(callback: FeedCallback) : GenericParser(callback) {
    private var isBeginEntry = false
    private var isBeginEntryAuthor = false
    private var isBeginEntryContributor = false
    private val tmpStrArr = arrayOfNulls<String>(3)
    override fun startTag(tag: String, xmlPullParser: XmlPullParser) {
        when (tag) {
            "feed" -> {
                callback.start()
            }
            "entry" -> {
                isBeginEntry = true
                callback.entryStart()
            }
        }
        if (isBeginEntry) entry(tag, xmlPullParser)
        else feed(tag, xmlPullParser)
    }

    private fun feed(tagName: String, xmlPullParser: XmlPullParser) {
        when (tagName) {
            "title" -> {
                val title = xmlPullParser.getNextText()
                if (title != null) {
                    callback.title(title)
                }
            }
            "subtitle" -> {
                val subtitle = xmlPullParser.getNextText()
                if (subtitle != null) {
                    callback.subtitle(subtitle)
                }
            }
            "link" -> {
                val attrs = xmlPullParser.getAttrs()
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
                val attrs = xmlPullParser.getAttrs()
                callback.entryContent(attrs["type"], attrs["language"], xmlPullParser.getNextText())
            }
            "contributor" -> {
                isBeginEntryContributor = true
            }
            "id" -> {
                val id = xmlPullParser.getNextText()
                if (id != null) {
                    callback.entryId(id)
                }
            }
            "link" -> {
                val attrs = xmlPullParser.getAttrs()
                callback.entryLink(attrs["type"], attrs["href"], attrs["title"])
            }
            "published" -> {
                val published = xmlPullParser.getNextText()
                if (published != null) {
                    callback.entryPublished(DateParser.parse(published), published)
                }
            }
            "summary" -> {
                val attrs = xmlPullParser.getAttrs()
                callback.entrySummary(attrs["type"], attrs["language"], xmlPullParser.getNextText())
            }
            "category" -> {
                val attrs = xmlPullParser.getAttrs()
                callback.entryTags(attrs["term"], attrs["scheme"], xmlPullParser.getNextText())
            }
            "title" -> {
                val title = xmlPullParser.getNextText()
                if (title != null) {
                    callback.entryTitle(title)
                }
            }
            "updated" -> {
                val updated = xmlPullParser.getNextText()
                if (updated != null) {
                    callback.entryUpdated(DateParser.parse(updated), updated)
                }
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