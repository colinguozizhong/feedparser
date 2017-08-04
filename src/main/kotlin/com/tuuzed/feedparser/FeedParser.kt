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

import com.tuuzed.feedparser.ext.getReader
import com.tuuzed.feedparser.internal.AtomParser
import com.tuuzed.feedparser.internal.GenericParser
import com.tuuzed.feedparser.internal.RssParser
import com.tuuzed.feedparser.internal.XmlParser
import com.tuuzed.feedparser.internal.util.CloseableUtils
import com.tuuzed.feedparser.internal.util.DateUtils
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL
import java.text.DateFormat

object FeedParser {
    // 日志
    private val logger = Logger.getLogger(javaClass)
    private var httpClient: OkHttpClient? = null


    fun setHttpClient(httpClient: OkHttpClient) {
        FeedParser.httpClient = httpClient
    }

    fun appendDateFormat(format: DateFormat) {
        DateUtils.addDateFormat(format)
    }

    fun parse(url: String, handler: FeedHandler, defCharSet: String = "utf-8") {
        var connection: HttpURLConnection? = null
        var inputStream: InputStream? = null
        try {
            if (httpClient != null) {
                // 使用okHttp请求
                val request = Request.Builder().url(url).get().build()
                val call = httpClient!!.newCall(request)
                val response = call.execute()
                val body = response.body() ?: throw IOException("连接失败")
                val reader = body.charStream()
                parse(reader, handler)
            } else {
                connection = URL(url).openConnection() as HttpURLConnection
                inputStream = connection.inputStream
                parse(inputStream, handler, defCharSet)
            }
        } catch (e: IOException) {
            handler.fatalError(e)
            logger.error(e.message, e)
        } finally {
            CloseableUtils.safeClose(inputStream)
            if (connection != null) {
                connection.disconnect()
            }
        }
    }

    fun parse(inputStream: InputStream, handler: FeedHandler, defCharSet: String = "utf-8") {
        try {
            parse(inputStream.getReader(defCharSet), handler)
        } catch (e: IOException) {
            handler.fatalError(e)
            logger.error(e.message, e)
        } finally {
            CloseableUtils.safeClose(inputStream)
        }
    }

    fun parse(reader: Reader, handler: FeedHandler) {
        val xmlPullParserFactory: XmlPullParserFactory
        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance()
            val xmlPullParser = xmlPullParserFactory.newPullParser()
            xmlPullParser.setInput(reader)
            XmlParser.parse(xmlPullParser, object : XmlParser.Callback {
                var parser: GenericParser? = null

                override fun startTag(pullParser: XmlPullParser, tagName: String) {
                    if (parser == null && "rss" == tagName) {
                        parser = RssParser()
                    } else if (parser == null && "feed" == tagName) {
                        parser = AtomParser()
                    }
                    parser?.startTag(tagName, pullParser, handler)
                }

                override fun endTag(tagName: String) {
                    parser?.endTag(tagName, handler)
                }

                override fun error(throwable: Throwable) {
                    handler.error(throwable)
                }
            })
        } catch (e: XmlPullParserException) {
            handler.fatalError(e)
        }

    }
}