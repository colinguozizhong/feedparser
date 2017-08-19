package com.tuuzed.feedparser.internal.util

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat

class DateUtilsTest {
    @Test
    fun parse() {
        val date = DateUtils.parse("2017-07-18T06:52:21+08:00")
        println(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date))
    }

}