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

import com.tuuzed.feedparser.util.Logger;
import com.tuuzed.feedparser.util.LoggerFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

abstract class GenericParser {
    private static final Logger logger = LoggerFactory.getLogger(GenericParser.class);

    public abstract void startTag(String tagName, XmlPullParser xmlPullParser, FeedHandler callback);

    public abstract void endTag(String tagName, FeedHandler handler);

    protected Map<String, String> getAttrs(XmlPullParser xmlPullParser) {
        Map<String, String> attrs = null;
        int count = xmlPullParser.getAttributeCount();
        if (count > 0) {
            attrs = new HashMap<>();
            for (int i = 0; i < count; i++) {
                attrs.put(xmlPullParser.getAttributeName(i), xmlPullParser.getAttributeValue(i).trim());
            }
        }
        return attrs;
    }

    protected String nextText(XmlPullParser xmlPullParser) {
        try {
            return xmlPullParser.nextText();
        } catch (XmlPullParserException | IOException e) {
            logger.info("nextText: " + e.getMessage(), e);
            return null;
        }
    }
}
