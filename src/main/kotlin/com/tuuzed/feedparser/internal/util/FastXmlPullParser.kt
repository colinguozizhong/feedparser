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
package com.tuuzed.feedparser.internal.util

import org.xmlpull.v1.XmlPullParser

internal object FastXmlPullParser {
    fun parse(xmlPullParser: XmlPullParser, callback: Callback) {
        try {
            var eventType = xmlPullParser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> callback.startTag(xmlPullParser, xmlPullParser.name)
                    XmlPullParser.END_TAG -> callback.endTag(xmlPullParser.name)
                }
                eventType = xmlPullParser.next()
            }
        } catch (e: Exception) {
            callback.error(e)
        }
    }

    interface Callback {
        fun startTag(xmlPullParser: XmlPullParser, tag: String)

        fun endTag(tag: String)

        fun error(throwable: Throwable)
    }
}