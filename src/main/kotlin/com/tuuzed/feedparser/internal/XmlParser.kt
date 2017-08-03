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

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

internal object XmlParser {
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