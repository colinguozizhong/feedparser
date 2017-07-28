package com.tuuzed.feedparser.util

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 日期解析工具
 */
object DateUtils {
    // 自定义日期格式
    private val CUSTOM_DATE_FORMATS: MutableList<DateFormat>

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

        CUSTOM_DATE_FORMATS = possibleDateFormats.mapTo(ArrayList<DateFormat>()) { SimpleDateFormat(it, Locale.ENGLISH) }
    }

    fun parse(strDate: String?, timeZone: TimeZone = TimeZone.getTimeZone("Asia/Shanghai")): Date? {
        var varStrDate = strDate
        if (varStrDate == null || varStrDate.isEmpty()) return null
        varStrDate = varStrDate.trim { it <= ' ' }
        if (varStrDate.length > 10) {
            if ((varStrDate.substring(varStrDate.length - 5).indexOf("+") == 0 || varStrDate.substring(varStrDate.length - 5).indexOf("-") == 0) && varStrDate.substring(varStrDate.length - 5).indexOf(":") == 2) {
                val sign = varStrDate.substring(varStrDate.length - 5, varStrDate.length - 4)
                varStrDate = varStrDate.substring(0, varStrDate.length - 5) + sign + "0" + varStrDate.substring(varStrDate.length - 4)
            }
            val dateEnd = varStrDate.substring(varStrDate.length - 6)
            if ((dateEnd.indexOf("-") == 0 || dateEnd.indexOf("+") == 0) && dateEnd.indexOf(":") == 3) {
                if ("GMT" != varStrDate.substring(varStrDate.length - 9, varStrDate.length - 6)) {
                    val oldDate = varStrDate
                    val newEnd = dateEnd.substring(0, 3) + dateEnd.substring(4)
                    varStrDate = oldDate.substring(0, oldDate.length - 6) + newEnd
                }
            }
        }
        var i = 0
        while (i < CUSTOM_DATE_FORMATS.size) {
            try {
                synchronized(CUSTOM_DATE_FORMATS[i]) {
                    CUSTOM_DATE_FORMATS[i].timeZone = timeZone
                    return CUSTOM_DATE_FORMATS[i].parse(varStrDate)
                }
            } catch (e: ParseException) {
                i++
            } catch (e: NumberFormatException) {
                i++
            }

        }
        return null
    }

    fun addDateFormat(format: DateFormat) {
        CUSTOM_DATE_FORMATS.add(format)
    }
}