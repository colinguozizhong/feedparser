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

internal class AtomParser(callback: FeedCallback) : GenericParser(callback) {
    private var isStartEntry = false
    private var isStartEntryAuthor = false
    private var isStartEntryContributor = false
    private val tmpMap = hashMapOf<String, String>()
    override fun startTag(tag: String, xmlPullParser: XmlPullParser) {
        when (tag) {
            "feed" -> {
                callback.start()
            }
            "entry" -> {
                isStartEntry = true
                callback.itemStart()
            }
        }
        if (isStartEntry) entry(tag, xmlPullParser)
        else feed(tag, xmlPullParser)
    }

    private fun feed(tagName: String, xmlPullParser: XmlPullParser) {
        when (tagName) {
            "title" -> {
                val attrs = xmlPullParser.getAttrs()
                val title = xmlPullParser.getNextText()
                if (title != null) {
                    callback.title(title, attrs["type"])
                }
            }
            "subtitle" -> {
                val attrs = xmlPullParser.getAttrs()
                val subtitle = xmlPullParser.getNextText()
                if (subtitle != null) {
                    callback.subtitle(subtitle, attrs["type"])
                }
            }
            "link" -> {
                val attrs = xmlPullParser.getAttrs()
                val link = attrs["href"]
                if (link != null) {
                    callback.link(link, attrs["type"], attrs["title"])
                }
            }
            "rights" -> {
                val attrs = xmlPullParser.getAttrs()
                val rights = xmlPullParser.getNextText()
                if (rights != null) {
                    callback.copyright(rights, attrs["type"])
                }
            }
            "generator" -> {
                val attrs = xmlPullParser.getAttrs()
                val generator = xmlPullParser.getNextText()
                if (generator != null) {
                    callback.generator(generator, attrs["uri"], attrs["version"])
                }
            }
        }
    }

    private fun entry(tag: String, xmlPullParser: XmlPullParser) {
        when (tag) {
            "author" -> {
                isStartEntryAuthor = true
            }
            "name" -> {
                if (isStartEntryAuthor || isStartEntryAuthor) {
                    val name = xmlPullParser.getNextText()
                    if (name != null) {
                        tmpMap.put("name", name)
                    }
                }
            }
            "uri" -> {
                if (isStartEntryAuthor || isStartEntryAuthor) {
                    val uri = xmlPullParser.getNextText()
                    if (uri != null) {
                        tmpMap.put("uri", uri)
                    }
                }
            }
            "email" -> {
                if (isStartEntryAuthor || isStartEntryAuthor) {
                    val email = xmlPullParser.getNextText()
                    if (email != null) {
                        tmpMap.put("email", email)
                    }
                }
            }
            "content" -> {
                val attrs = xmlPullParser.getAttrs()
                val content = xmlPullParser.getNextText()
                if (content != null) {
                    callback.itemContent(content, attrs["type"], attrs["language"])
                }
            }
            "contributor" -> {
                isStartEntryContributor = true
            }
            "id" -> {
                val id = xmlPullParser.getNextText()
                if (id != null) {
                    callback.itemId(id)
                }
            }
            "link" -> {
                val attrs = xmlPullParser.getAttrs()
                val link = attrs["href"]
                if (link != null) {
                    callback.itemLink(link, attrs["type"], attrs["title"])
                }
            }
            "published" -> {
                val published = xmlPullParser.getNextText()
                if (published != null) {
                    callback.itemPublished(DateParser.parse(published), published)
                }
            }
            "summary" -> {
                val attrs = xmlPullParser.getAttrs()
                val summary = xmlPullParser.getNextText()
                if (summary != null) {
                    callback.itemSummary(summary, attrs["type"], attrs["language"])
                }
            }
            "category" -> {
                val attrs = xmlPullParser.getAttrs()
                val category = xmlPullParser.getNextText()
                if (category != null) {
                    callback.itemCategory(category, attrs["term"], attrs["scheme"])
                }
            }
            "title" -> {
                val title = xmlPullParser.getNextText()
                if (title != null) {
                    callback.itemTitle(title)
                }
            }
            "updated" -> {
                val updated = xmlPullParser.getNextText()
                if (updated != null) {
                    callback.itemUpdated(DateParser.parse(updated), updated)
                }
            }
        }
    }

    override fun endTag(tag: String) {
        when (tag) {
            "feed" -> callback.end()
            "entry" -> {
                isStartEntry = false
                callback.itemEnd()
            }
            "author" -> {
                val author = tmpMap["name"]
                if (author != null) {
                    callback.itemAuthor(author, tmpMap["uri"], tmpMap["email"])
                }
                tmpMap.clear()
                isStartEntryAuthor = false
            }
            "contributor" -> {
                val contributor = tmpMap["name"]
                if (contributor != null) {
                    callback.itemContributor(contributor, tmpMap["uri"], tmpMap["email"])
                }
                tmpMap.clear()
                isStartEntryContributor = false
            }
        }
    }
}