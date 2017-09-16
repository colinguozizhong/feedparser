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
package com.tuuzed.feedparser.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XmlPullParserUtils {

    @NotNull
    public static Map<String, String> getAttrs(@NotNull XmlPullParser xmlPullParser) {
        Map<String, String> attrs = new HashMap<>();
        int count = xmlPullParser.getAttributeCount();
        for (int i = 0; i < count; i++) {
            attrs.put(xmlPullParser.getAttributeName(i), xmlPullParser.getAttributeValue(i).trim());
        }
        return attrs;
    }

    @Nullable
    public static String getNextText(@NotNull XmlPullParser xmlPullParser) {
        try {
            return xmlPullParser.nextText().trim();
        } catch (XmlPullParserException | IOException e) {
            return null;
        }
    }
}
