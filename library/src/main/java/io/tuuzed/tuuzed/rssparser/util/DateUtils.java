/* MIT License
 *
 * Copyright (c) 2016 TuuZed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *         OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.tuuzed.tuuzed.rssparser.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期解析工具
 */
public class DateUtils {
    // 自定义日期格式
    private static final SimpleDateFormat[] CUSTOM_DATE_FORMATS;

    public static Date parseDate(String strDate) {
        return parseDate(strDate, TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public static Date parseDate(String strDate, TimeZone timeZone) {
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
        while (i < CUSTOM_DATE_FORMATS.length) {
            try {
                synchronized (CUSTOM_DATE_FORMATS[i]) {
                    CUSTOM_DATE_FORMATS[i].setTimeZone(timeZone);
                    return CUSTOM_DATE_FORMATS[i].parse(strDate);
                }
            } catch (ParseException | NumberFormatException e) {
                i++;
            }
        }
        return null;
    }

    public static String formatDate(Date date, DateFormat df) {
        return df.format(date);
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
                /* Simple Date Format */"yyyy-MM-dd HH:mm:ss",
                /* Simple Date Format */"yyyy-MM-dd",
                /* Simple Date Format */"MMM dd, yyyy"};

        CUSTOM_DATE_FORMATS = new SimpleDateFormat[possibleDateFormats.length];

        for (int i = 0; i < possibleDateFormats.length; i++) {
            CUSTOM_DATE_FORMATS[i] = new SimpleDateFormat(possibleDateFormats[i], Locale.ENGLISH);
        }
    }
}

