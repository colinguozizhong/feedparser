package com.tuuzed.feedparser.ext

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.regex.Pattern

private val PATTERN = Pattern.compile("(encoding|charset)=.*(GB2312|UTF-8|GBK).*", Pattern.CASE_INSENSITIVE)

fun String.getPossibleEncoding(): String? {
    val matcher = PATTERN.matcher(this)
    if (matcher.find()) {
        return matcher.group(2)
    }
    return null
}

fun InputStream.getPossibleEncoding(charSet: Array<String>): InputStream {
    val sb = StringBuilder()
    val outputStream = ByteArrayOutputStream()
    val bytes = ByteArray(1024)
    var len = this.read(bytes)
    do {
        sb.append(bytes, 0, len)
        len = this.read(bytes)
        outputStream.write(bytes, 0, len)
    } while (len != -1)
    outputStream.flush()
    val content = sb.toString()
    val matcher = PATTERN.matcher(content)
    if (matcher.find()) {
        charSet[0] = matcher.group(2)
    }
    return ByteArrayInputStream(outputStream.toByteArray())
}