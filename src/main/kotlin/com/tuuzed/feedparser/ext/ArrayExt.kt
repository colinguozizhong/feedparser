package com.tuuzed.feedparser.ext

fun <T> Array<T?>.clear() = this.indices.forEach({ this[it] = null })