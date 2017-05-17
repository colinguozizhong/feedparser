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
package com.github.tuuzed.feedparser.util;

public final class Logger {
    public static Logger getLogger(Class clazz) {
        return new Logger(clazz.getName());
    }

    private String name;
    private boolean debug;

    private Logger(String name) {
        this.name = name;
        this.debug = false;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void info(String msg) {
        if (debug) {
            System.out.println(name + "\tINFO:\t" + msg.trim());
        }
    }

    public void info(Throwable throwable) {
        if (debug) {
            throwable.printStackTrace();
            System.out.println(name + "\tINFO:\t" + throwable);
        }
    }

    public void error(String msg) {
        if (debug)
            System.out.println(name + "\tERROR:\t" + msg.trim());
    }

    public void error(Throwable throwable) {
        if (debug) {
            throwable.printStackTrace();
            System.out.println(name + "\tERROR:\t" + throwable);
        }
    }
}
