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

import com.tuuzed.feedparser.ext.silenceClose
import com.tuuzed.feedparser.internal.AtomParser
import com.tuuzed.feedparser.internal.GenericParser
import com.tuuzed.feedparser.internal.RssParser
import com.tuuzed.feedparser.util.DateParser
import com.tuuzed.feedparser.util.FastXmlPullParser
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.*
import java.net.URL
import java.text.DateFormat
import java.util.regex.Pattern


object FeedParser {
    private val CHARSET_PATTERN = Pattern.compile(
            "(encoding|charset)=.*(GB2312|UTF-8|GBK).*",
            Pattern.CASE_INSENSITIVE)

    fun appendDateFormat(format: DateFormat) {
        DateParser.appendDateFormat(format)
    }

    fun parse(url: String, callback: FeedCallback, defCharset: String = "utf-8") {
        var input: InputStream? = null
        var reader: InputStreamReader? = null
        try {
            val connection = URL(url).openConnection()
            val contentType = connection.contentType
            val matcher = CHARSET_PATTERN.matcher(contentType)
            var charset: String? = null
            if (matcher.find()) {
                charset = matcher.group(2)
            }
            input = connection.getInputStream()
            // 没有匹配到编码
            if (charset == null) parse(input, callback, defCharset)
            // 匹配到编码
            else {
                reader = InputStreamReader(input, charset)
                parse(reader, callback)
            }
        } catch (e: Exception) {
            callback.fatalError(e)
        } finally {
            reader?.silenceClose()
            input?.silenceClose()
        }
    }

    fun parse(input: InputStream, callback: FeedCallback, defCharset: String = "utf-8") {
        var charset: String? = null
        val output = ByteArrayOutputStream()
        var reader: Reader? = null
        try {

            input.copyTo(output)
            val content = output.toString()
            val matcher = CHARSET_PATTERN.matcher(content)
            if (matcher.find()) {
                charset = matcher.group(2)
            }
            reader = InputStreamReader(ByteArrayInputStream(output.toByteArray()),
                    if (charset == null) defCharset else charset)
            parse(reader, callback)
        } catch (e: Exception) {
            callback.fatalError(e)
        } finally {
            reader?.silenceClose()
            output.silenceClose()
        }
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
        } finally {
            try {
                input.close()
            } catch (e: IOException) {
                // pass
            }
        }
    }
}