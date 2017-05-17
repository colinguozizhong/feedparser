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
package com.github.tuuzed.feedparser.atom;

import com.github.tuuzed.feedparser.callback.EntryCallback;
import com.github.tuuzed.feedparser.callback.FeedCallback;
import com.github.tuuzed.feedparser.util.DateUtils;
import com.github.tuuzed.feedparser.xml.AbstractParser;
import org.xmlpull.v1.XmlPullParser;

import java.util.Map;

public class AtomParser extends AbstractParser {
    private boolean isBeginEntry = false;
    private static final String DEF_TYPE = "text/plain";
    private static final String DEF_LANGUAGE = null;

    @Override
    public void startTag(String tagName, XmlPullParser xmlPullParser, FeedCallback callback) {
        switch (tagName) {
            case "feed":
                callback.begin();
                break;
            case "entry":
                isBeginEntry = true;
                if (callback.entryCallback != null) {
                    callback.entryCallback.begin();
                }
                break;
        }
        if (isBeginEntry) {
            entry(tagName, xmlPullParser, callback.entryCallback);
            return;
        }
        switch (tagName) {
            case "title": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    callback.title(attrs.get("type"), DEF_LANGUAGE, nextText(xmlPullParser));
                } else {
                    callback.title(DEF_TYPE, DEF_LANGUAGE, nextText(xmlPullParser));
                }
                break;
            }
            case "subtitle": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    callback.subtitle(attrs.get("type"), DEF_LANGUAGE, nextText(xmlPullParser));
                } else {
                    callback.subtitle(DEF_TYPE, DEF_LANGUAGE, nextText(xmlPullParser));
                }
                break;
            }
            case "link": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    callback.links(attrs.get("rel"), attrs.get("type"), attrs.get("href"));
                } else {
                    callback.links(null, DEF_TYPE, null);
                }
                break;
            }
            case "rights": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    callback.rights(attrs.get("type"), DEF_LANGUAGE, nextText(xmlPullParser));
                } else {
                    callback.rights(DEF_TYPE, DEF_LANGUAGE, nextText(xmlPullParser));
                }
                break;
            }
        }
    }

    private void entry(String tagName, XmlPullParser xmlPullParser, EntryCallback callback) {
        if (callback == null) {
            return;
        }
        switch (tagName) {
            case "title":
                callback.title(nextText(xmlPullParser));
                break;
            case "link": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    callback.link(attrs.get("href"));
                } else {
                    callback.link(null);
                }
                break;
            }
            case "id":
                callback.id(nextText(xmlPullParser), false);
                break;
            case "published": {
                String text = nextText(xmlPullParser);
                callback.published(DateUtils.parse(text), text);
                break;
            }
            case "updated": {
                String text = nextText(xmlPullParser);
                callback.updated(DateUtils.parse(text), text);
                break;
            }
            case "summary": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    callback.summary(attrs.get("type"), attrs.get("language"), nextText(xmlPullParser));
                } else {
                    callback.summary(DEF_TYPE, DEF_LANGUAGE, nextText(xmlPullParser));
                }
                break;
            }
            case "content": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    callback.content(attrs.get("type"), attrs.get("language"), nextText(xmlPullParser));
                } else {
                    callback.content(DEF_TYPE, DEF_LANGUAGE, nextText(xmlPullParser));
                }
                break;
            }
        }
    }

    @Override
    public void endTag(String tagName, FeedCallback callback) {
        if ("feed".equals(tagName)) {
            callback.end();
        }
        if ("entry".equals(tagName)) {
            isBeginEntry = false;
            if (callback.entryCallback != null) {
                callback.entryCallback.end();
            }
        }
    }
}
