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
package com.tuuzed.feedparser

import com.tuuzed.feedparser.internal.AtomParser
import com.tuuzed.feedparser.internal.GenericParser
import com.tuuzed.feedparser.internal.RssParser
import com.tuuzed.feedparser.internal.util.DateParser
import com.tuuzed.feedparser.internal.util.FastXmlPullParser
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.Reader
import java.text.DateFormat

object FeedParser {

    fun appendDateFormat(format: DateFormat) {
        DateParser.addDateFormat(format)
    }

    fun parse(input: Reader, callback: FeedCallback) {
        val xmlPullParserFactory: XmlPullParserFactory
        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance()
            val xmlPullParser = xmlPullParserFactory.newPullParser()
            xmlPullParser.setInput(input)
            FastXmlPullParser.parse(xmlPullParser, object : FastXmlPullParser.Callback {
                var parser: GenericParser? = null

                override fun startTag(xmlPullParser: XmlPullParser, tag: String) {
                    if (parser == null) {
                        if ("rss" == tag) {
                            parser = RssParser(callback)
                        } else if ("feed" == tag) {
                            parser = AtomParser(callback)
                        }
                    }
                    parser?.startTag(tag, xmlPullParser)
                }

                override fun endTag(tag: String) {
                    parser?.endTag(tag)
                }

                override fun error(throwable: Throwable) {
                    callback.error(throwable)
                }
            })
        } catch (e: XmlPullParserException) {
            callback.fatalError(e)
        }

    }
}