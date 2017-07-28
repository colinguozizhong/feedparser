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
package com.tuuzed.feedparser.util

class Logger(clazz: Class<*>) {
    private val name: String = clazz.name

    fun info(message: String?, throwable: Throwable? = null) {
        System.out.printf("%s INFO: %s%n", name, message)
        throwable?.printStackTrace()
    }

    fun error(message: String?, throwable: Throwable? = null) {
        System.out.printf("%s ERROR: %s%n", name, message)
        throwable?.printStackTrace()
    }
}

object LoggerFactory {
    fun getLogger(clazz: Class<*>): Logger {
        return Logger(clazz)
    }
}