package com.tuuzed.feedparser

import org.xmlpull.v1.XmlPullParser

internal abstract class GenericParser {

    abstract fun startTag(tagName: String, pullParser: XmlPullParser, handler: FeedHandler)

    abstract fun endTag(tagName: String, handler: FeedHandler)
}

