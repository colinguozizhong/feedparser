package com.tuuzed.feedparser.xml

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

object XmlParser {
    fun parse(pullParser: XmlPullParser, callback: Callback) {
        try {
            var eventType = pullParser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> callback.startTag(pullParser, pullParser.name)
                    XmlPullParser.END_TAG -> callback.endTag(pullParser.name)
                }
                eventType = pullParser.next()
            }
        } catch (e: XmlPullParserException) {
            callback.error(e)
        } catch (e: IOException) {
            callback.error(e)
        }
    }

    interface Callback {
        fun startTag(pullParser: XmlPullParser, tagName: String)

        fun endTag(tagName: String)

        fun error(throwable: Throwable)
    }
}