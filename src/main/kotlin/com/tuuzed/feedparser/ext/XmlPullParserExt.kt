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
package com.tuuzed.feedparser.ext

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

fun XmlPullParser.getAttrs(): Map<String, String> {
    val attrs = mutableMapOf<String, String>()
    val count = this.attributeCount
    if (count > 0) {
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