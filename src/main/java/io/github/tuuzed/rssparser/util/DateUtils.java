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
package io.github.tuuzed.rssparser.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期解析工具
 */
public class DateUtils {
    // 自定义日期格式
    private static final List<DateFormat> CUSTOM_DATE_FORMATS;

    public static Date parse(String strDate) {
        return parse(strDate, TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public static Date parse(String strDate, TimeZone timeZone) {
        if (strDate == null || strDate.length() == 0) return null;
        strDate = strDate.trim();
        if (strDate.length() > 10) {
            if ((strDate.substring(strDate.length() - 5).indexOf("+") == 0
                    || strDate.substring(strDate.length() - 5).indexOf("-") == 0)
                    && strDate.substring(strDate.length() - 5).indexOf(":") == 2) {
                String sign = strDate.substring(strDate.length() - 5, strDate.length() - 4);
                strDate = strDate.substring(0, strDate.length() - 5)
                        + sign + "0" + strDate.substring(strDate.length() - 4);
            }
            String dateEnd = strDate.substring(strDate.length() - 6);
            if ((dateEnd.indexOf("-") == 0 || dateEnd.indexOf("+") == 0) && dateEnd.indexOf(":") == 3) {
                if (!"GMT".equals(strDate.substring(strDate.length() - 9, strDate.length() - 6))) {
                    String oldDate = strDate;
                    String newEnd = dateEnd.substring(0, 3) + dateEnd.substring(4);
                    strDate = oldDate.substring(0, oldDate.length() - 6) + newEnd;
                }
            }
        }
        int i = 0;
        while (i < CUSTOM_DATE_FORMATS.size()) {
            try {
                synchronized (CUSTOM_DATE_FORMATS.get(i)) {
                    CUSTOM_DATE_FORMATS.get(i).setTimeZone(timeZone);
                    return CUSTOM_DATE_FORMATS.get(i).parse(strDate);
                }
            } catch (ParseException | NumberFormatException e) {
                i++;
            }
        }
        return null;
    }

    public static String format(Date date, DateFormat df) {
        return df.format(date);
    }

    public static void addDateFormat(DateFormat format) {
        CUSTOM_DATE_FORMATS.add(format);
    }

    static {
        final String[] possibleDateFormats = {
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

        CUSTOM_DATE_FORMATS = new ArrayList<>();
        for (String s : possibleDateFormats) {
            CUSTOM_DATE_FORMATS.add(new SimpleDateFormat(s, Locale.ENGLISH));
        }
    }
}

