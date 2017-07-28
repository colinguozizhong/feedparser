package com.tuuzed.feedparser

import com.tuuzed.feedparser.ext.clear
import com.tuuzed.feedparser.ext.getAttrs
import com.tuuzed.feedparser.ext.getNextText
import com.tuuzed.feedparser.util.DateUtils
import org.xmlpull.v1.XmlPullParser

internal class AtomParser : GenericParser() {
    private var isBeginEntry = false
    private var isBeginEntryAuthor = false
    private var isBeginEntryContributor = false
    private val tempStrArr = arrayOfNulls<String>(3)

    override fun startTag(tagName: String, pullParser: XmlPullParser, handler: FeedHandler) {
        when (tagName) {
            "feed" -> handler.begin()
            "entry" -> {
                isBeginEntry = true
                handler.entryBegin()
            }
        }
        if (isBeginEntry) {
            entry(tagName, pullParser, handler)
        } else {
            feed(tagName, pullParser, handler)
        }
    }

    private fun feed(tagName: String, xmlPullParser: XmlPullParser, handler: FeedHandler) {
        when (tagName) {
            "title" -> handler.title(xmlPullParser.getNextText())
            "subtitle" -> handler.subtitle(xmlPullParser.getNextText())
            "link" -> {
                val attrs = xmlPullParser.getAttrs()
                if (attrs != null) {
                    handler.link(attrs["type"], attrs["href"], attrs["title"])
                } else {
                    handler.link(null, null, null)
                }
            }
        }
    }

    private fun entry(tagName: String, xmlPullParser: XmlPullParser, handler: FeedHandler) {
        when (tagName) {
            "author" -> isBeginEntryAuthor = true
            "name" -> if (isBeginEntryAuthor || isBeginEntryContributor) {
                tempStrArr[0] = xmlPullParser.getNextText()
            }
            "uri" -> if (isBeginEntryAuthor || isBeginEntryContributor) {
                tempStrArr[1] = xmlPullParser.getNextText()
            }
            "email" -> if (isBeginEntryAuthor || isBeginEntryContributor) {
                tempStrArr[1] = xmlPullParser.getNextText()
            }
            "content" -> {
                val attrs = xmlPullParser.getAttrs()
                if (attrs != null) {
                    handler.entryContent(attrs["type"], attrs["language"], xmlPullParser.getNextText())
                } else {
                    handler.entryContent(null, null, xmlPullParser.getNextText())
                }
            }
            "contributor" -> isBeginEntryContributor = true
            "id" -> handler.entryId(xmlPullParser.getNextText())
            "link" -> {
                val attrs = xmlPullParser.getAttrs()
                if (attrs != null) {
                    handler.entryLink(attrs["type"], attrs["href"], attrs["title"])
                } else {
                    handler.entryLink(null, null, null)
                }
            }
            "published" -> {
                val text = xmlPullParser.getNextText()
                handler.entryPublished(DateUtils.parse(text), text)
            }
            "summary" -> {
                val attrs = xmlPullParser.getAttrs()
                val text = xmlPullParser.getNextText()
                if (attrs != null) {
                    handler.entrySummary(attrs["type"], attrs["language"], text)
                } else {
                    handler.entrySummary(null, null, text)
                }
            }
            "category" -> {
                val attrs = xmlPullParser.getAttrs()
                val text = xmlPullParser.getNextText()
                if (attrs != null) {
                    handler.entryTags(attrs["term"], attrs["scheme"], text)
                } else {
                    handler.entryTags(null, null, text)
                }
            }
            "title" -> {
                val text = xmlPullParser.getNextText()
                handler.entryTitle(text!!)
            }
            "updated" -> {
                val text = xmlPullParser.getNextText()
                handler.entryUpdated(DateUtils.parse(text)!!, text!!)
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
                handler.entryAuthor(tempStrArr[0], tempStrArr[1], tempStrArr[2])
                tempStrArr.clear()
                isBeginEntryAuthor = false
            }
            "contributor" -> {
                handler.entryContributor(tempStrArr[0], tempStrArr[1], tempStrArr[2])
                tempStrArr.clear()
                isBeginEntryContributor = false
            }
        }
    }
}