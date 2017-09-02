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
package com.tuuzed.feedparser.util;


import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期解析工具
 */
public class DateParser {
    private static final List<DateFormat> DATE_FORMATS;

    static {
        final String[] patterns = {
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
                /* Common DateFormat */"MMM dd, yyyy"};
        DATE_FORMATS = new LinkedList<>();
        for (String pattern : patterns) {
            DATE_FORMATS.add(new SimpleDateFormat(pattern, Locale.ENGLISH));
        }
    }

    public static Date parse(@NotNull String source) {
        return parse(source, TimeZone.getDefault());
    }

    public static Date parse(@NotNull String source, @NotNull TimeZone timeZone) {
        source = source.trim();
        Date date = null;
        for (DateFormat dateFormat : DATE_FORMATS) {
            dateFormat.setTimeZone(timeZone);
            try {
                date = dateFormat.parse(source);
                break;
            } catch (ParseException e) {
                // pass
            }
        }
        return date;
    }

    public static void appendDateFormat(@NotNull DateFormat format) {
        DATE_FORMATS.add(format);
    }


}

