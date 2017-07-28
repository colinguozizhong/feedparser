package com.tuuzed.feedparser

import com.tuuzed.feedparser.ext.getAttrs
import com.tuuzed.feedparser.ext.getNextText
import com.tuuzed.feedparser.util.DateUtils
import org.xmlpull.v1.XmlPullParser

internal class RssParser : GenericParser() {
    private var isBeginChannel = false
    private var isBeginImage = false
    private var isBeginTextInput = false
    private var isBeginSkipDays = false
    private var isBeginSkipHours = false
    private var isBeginItem = false
    private var tempList: MutableList<String>? = null

    override fun startTag(tagName: String, pullParser: XmlPullParser, handler: FeedHandler) {
        when (tagName) {
            "rss" -> handler.begin()
            "channel" -> isBeginChannel = true
            "image" -> isBeginImage = true
            "skipDays" -> {
                tempList = ArrayList<String>()
                isBeginSkipDays = true
            }
            "skipHours" -> {
                tempList = ArrayList<String>()
                isBeginSkipHours = true
            }
            "textInput" -> isBeginTextInput = true
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
            } else if (isBeginSkipDays && tempList != null && "day" == tagName) {
                // skipDays
                tempList!!.add(pullParser.getNextText()!!)
            } else if (isBeginSkipHours && tempList != null && "hour" == tagName) {
                // skipHours
                tempList!!.add(pullParser.getNextText()!!)
            } else if (isBeginTextInput) {
                // textInput
            } else {
                // channel
                channel(tagName, pullParser, handler)
            }
        }
    }

    private fun channel(tagName: String, xmlPullParser: XmlPullParser, handler: FeedHandler) {
        when (tagName) {
            "title" -> handler.title(xmlPullParser.getNextText())
            "description" -> handler.subtitle(xmlPullParser.getNextText())
            "link" -> handler.link(null, xmlPullParser.getNextText(), null)
        }
    }

    private fun item(tagName: String, xmlPullParser: XmlPullParser, handler: FeedHandler) {
        when (tagName) {
            "author" -> handler.entryAuthor(xmlPullParser.getNextText(), null, null)
            "comments" -> handler.entryComments(xmlPullParser.getNextText())
            "body" -> {
                val attrs = xmlPullParser.getAttrs()
                if (attrs != null) {
                    handler.entryContent(attrs["type"], attrs["language"], attrs["content"])
                } else {
                    handler.entryContent(null, null, null)
                }
            }
            "contributor" -> {
                val attrs = xmlPullParser.getAttrs()
                if (attrs != null) {
                    handler.entryContributor(attrs["name"], attrs["href"], attrs["email"])
                } else {
                    handler.entryContributor(null, null, null)
                }
            }
            "enclosure" -> {
                val attrs = xmlPullParser.getAttrs()
                if (attrs != null) {
                    handler.entryEnclosure(attrs["length"], attrs["type"], attrs["url"])
                } else {
                    handler.entryEnclosure(null, null, null)
                }
            }
            "expirationDate" -> {
                val text = xmlPullParser.getNextText()
                handler.entryPublished(DateUtils.parse(text), text)
            }
            "guid" -> handler.entryId(xmlPullParser.getNextText())
            "link" -> handler.entryLink(null, xmlPullParser.getNextText(), null)
            "pubDate" -> {
                val text = xmlPullParser.getNextText()
                handler.entryPublished(DateUtils.parse(text), text)
            }
            "source" -> handler.entrySource(xmlPullParser.getNextText())
            "description" -> handler.entrySummary(null, null, xmlPullParser.getNextText())
            "category" -> handler.entryTags(null, null, xmlPullParser.getNextText())
            "title" -> handler.entryTitle(xmlPullParser.getNextText())
        }
    }

    override fun endTag(tagName: String, handler: FeedHandler) {
        when (tagName) {
            "rss" -> handler.end()
            "channel" -> isBeginChannel = false
            "image" -> isBeginImage = false
            "skipDays" -> {
                isBeginSkipDays = false
                if (tempList != null) {
                    handler.skipDays(tempList!!)
                } else {
                    handler.skipDays(ArrayList(0))
                }
            }
            "skipHours" -> {
                isBeginSkipHours = false
                if (tempList != null) {
                    handler.skipHours(tempList!!)
                } else {
                    handler.skipHours(ArrayList(0))
                }
            }
            "textInput" -> isBeginTextInput = false
            "item" -> {
                handler.entryEnd()
                isBeginItem = false
            }
        }
    }
}