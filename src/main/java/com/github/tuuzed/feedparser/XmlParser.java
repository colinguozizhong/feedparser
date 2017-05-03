package com.github.tuuzed.feedparser;
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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

class XmlParser {
    public static void parse(XmlPullParser xmlPullParser, Callback callback) {
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
        } catch (XmlPullParserException e) {
            callback.error(e);
        } catch (IOException e) {
            callback.error(e);
        }
    }

    public interface Callback {
        void startTag(XmlPullParser xmlPullParser, String tagName);

        void endTag(String tagName);

        void error(Throwable throwable);
    }
}
