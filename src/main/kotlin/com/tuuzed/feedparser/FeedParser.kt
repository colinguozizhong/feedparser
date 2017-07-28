package com.tuuzed.feedparser

import com.tuuzed.feedparser.ext.getPossibleEncoding
import com.tuuzed.feedparser.util.CloseableUtils
import com.tuuzed.feedparser.util.LoggerFactory
import com.tuuzed.feedparser.xml.XmlParser
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL

object FeedParser {
    // 日志
    private val logger = LoggerFactory.getLogger(javaClass)

    private var httpClient: OkHttpClient? = null

    fun setHttpClient(httpClient: OkHttpClient) {
        FeedParser.httpClient = httpClient
    }

    fun parse(url: String, callback: FeedHandler) {
        parse(url, "utf-8", callback)
    }

    fun parse(url: String, defCharSet: String, handler: FeedHandler) {
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
                parse(inputStream, defCharSet, handler)
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

    fun parse(inputStream: InputStream, charSet: String, handler: FeedHandler) {
        var input = inputStream
        val charset = arrayOf(charSet)
        try {
            input = input.getPossibleEncoding(charset)
            parse(InputStreamReader(inputStream, charset[0]), handler)
        } catch (e: IOException) {
            handler.fatalError(e)
            logger.error(e.message, e)
        } finally {
            CloseableUtils.safeClose(input)
        }
    }

    fun parse(reader: Reader, handler: FeedHandler) {
        val xmlPullParserFactory: XmlPullParserFactory
        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance()
            val xmlPullParser = xmlPullParserFactory.newPullParser()
            xmlPullParser.setInput(reader)
            XmlParser.parse(xmlPullParser, object : XmlParser.Callback {
                internal var parser: GenericParser? = null

                override fun startTag(pullParser: XmlPullParser, tagName: String) {
                    if (parser == null && "rss" == tagName) {
                        parser = RssParser()
                    } else if (parser == null && "feed" == tagName) {
                        parser = AtomParser()
                    }
                    if (parser != null) {
                        parser!!.startTag(tagName, pullParser, handler)
                    }
                }

                override fun endTag(tagName: String) {
                    if (parser != null) {
                        parser!!.endTag(tagName, handler)
                    }
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