package com.tuuzed.feedparser.ext

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.util.HashMap

fun XmlPullParser.getAttrs(): Map<String, String>? {
    var attrs: MutableMap<String, String>? = null
    val count = this.attributeCount
    if (count > 0) {
        attrs = HashMap<String, String>()
        for (i in 0..count - 1) {
            attrs.put(this.getAttributeName(i), this.getAttributeValue(i).trim { it <= ' ' })
        }
    }
    return attrs
}

fun XmlPullParser.getNextText(): String? {
    try {
        return this.nextText()
    } catch (e: XmlPullParserException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}