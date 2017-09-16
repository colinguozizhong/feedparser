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
package com.tuuzed.feedparser;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

class FastXmlPullParser {
    static void parse(@NotNull XmlPullParser xmlPullParser, @NotNull Callback callback) {
        try {
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        callback.startTag(xmlPullParser, xmlPullParser.getName());
                        break;
                    case XmlPullParser.END_TAG:
                        callback.endTag(xmlPullParser.getName());
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            callback.error(e);
        }
    }

    public interface Callback {
        void startTag(@NotNull XmlPullParser xmlPullParser, @NotNull String tag);

        void endTag(@NotNull String tag);

        void error(@NotNull Throwable throwable);
    }
}
