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