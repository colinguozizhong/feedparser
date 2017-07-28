package com.tuuzed.feedparser.ext

fun AutoCloseable.safeClose() {
    try {
        this.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}