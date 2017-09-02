package com.tuuzed.feedparser.ext

import java.io.InputStream
import java.io.OutputStream
import java.io.Reader
import java.io.Writer

fun Reader.silenceClose() {
    try {
        this.close()
    } catch (e: Exception) {
        // pass
    }
}

fun Writer.silenceClose() {
    try {
        this.close()
    } catch (e: Exception) {
        // pass
    }
}

fun InputStream.silenceClose() {
    try {
        this.close()
    } catch (e: Exception) {
        // pass
    }
}

fun OutputStream.silenceClose() {
    try {
        this.close()
    } catch (e: Exception) {
        // pass
    }
}