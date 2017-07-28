package com.tuuzed.feedparser.ext

import java.text.DateFormat
import java.util.*

fun Date.format(df: DateFormat): String = df.format(this)