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

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * 日期解析工具
 */
internal object DateUtils {
    // 自定义日期格式
    private val dateFormats: ArrayList<SimpleDateFormat>

    init {
        val possibleDateFormats = arrayOf(
                /* RFC 1123 with 2-digit Year */"EEE, dd MMM yy HH:mm:ss z",
                /* RFC 1123 with 4-digit Year */"EEE, dd MMM yyyy HH:mm:ss z",
                /* RFC 1123 with no Timezone */"EEE, dd MMM yy HH:mm:ss",
                /* Variant of RFC 1123 */"EEE, MMM dd yy HH:mm:ss",
                /* RFC 1123 with no Seconds */"EEE, dd MMM yy HH:mm z",
                /* Variant of RFC 1123 */"EEE dd MMM yyyy HH:mm:ss",
                /* RFC 1123 with no Day */"dd MMM yy HH:mm:ss z",
                /* RFC 1123 with no Day or Seconds */"dd MMM yy HH:mm z",
                /* ISO 8601 slightly modified */"yyyy-MM-dd'T'HH:mm:ssZ",
                /* ISO 8601 slightly modified */"yyyy-MM-dd'T'HH:mm:ss'Z'",
                /* ISO 8601 slightly modified */"yyyy-MM-dd'T'HH:mm:sszzzz",
                /* ISO 8601 slightly modified */"yyyy-MM-dd'T'HH:mm:ss z",
                /* ISO 8601 */"yyyy-MM-dd'T'HH:mm:ssz",
                /* ISO 8601 slightly modified */"yyyy-MM-dd'T'HH:mm:ss.SSSz",
                /* ISO 8601 slightly modified */"yyyy-MM-dd'T'HHmmss.SSSz",
                /* ISO 8601 slightly modified */"yyyy-MM-dd'T'HH:mm:ss",
                /* ISO 8601 w/o seconds */"yyyy-MM-dd'T'HH:mmZ",
                /* ISO 8601 w/o seconds */"yyyy-MM-dd'T'HH:mm'Z'",
                /* RFC 1123 without Day Name */"dd MMM yyyy HH:mm:ss z",
                /* RFC 1123 without Day Name and Seconds */"dd MMM yyyy HH:mm z",
                /* Common DateFormat */"yyyy-MM-dd HH:mm:ss",
                /* Common DateFormat */"yyyy-MM-dd",
                /* Common DateFormat */"MMM dd, yyyy")
        dateFormats = possibleDateFormats.mapTo(ArrayList<SimpleDateFormat>(possibleDateFormats.size),
                { SimpleDateFormat(it.replace(":", ""), Locale.ENGLISH) })
    }

    fun parse(strDate: String?, timeZone: TimeZone = TimeZone.getTimeZone("Asia/Shanghai")): Date? {
        if (strDate == null) return null
        val _strDate = strDate.trim().replace(":", "")
        var date: Date? = null
        dateFormats.forEach {
            try {
                it.timeZone = timeZone
                date = it.parse(_strDate)
                return date
            } catch (e: Exception) {
            }
        }
        return date
    }

    fun addDateFormat(format: SimpleDateFormat) {
        dateFormats.add(format)
    }
}