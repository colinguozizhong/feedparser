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


import com.tuuzed.feedparser.util.DateUtils;
import org.xmlpull.v1.XmlPullParser;

import java.util.Map;

class AtomParser extends GenericParser {
    private boolean isBeginEntry = false;
    private boolean isBeginEntryAuthor = false;
    private boolean isBeginEntryContributor = false;
    private String[] tempStrArr = new String[3];

    @Override
    public void startTag(String tagName, XmlPullParser xmlPullParser, FeedHandler handler) {
        switch (tagName) {
            case "feed":
                handler.begin();
                break;
            case "entry":
                isBeginEntry = true;
                handler.entryBegin();
                break;
        }
        if (isBeginEntry) {
            entry(tagName, xmlPullParser, handler);
        } else {
            feed(tagName, xmlPullParser, handler);
        }
    }

    private void feed(String tagName, XmlPullParser xmlPullParser, FeedHandler handler) {
        switch (tagName) {
            case "title": {
                String text = nextText(xmlPullParser);
                handler.title(text);
                break;
            }
            case "subtitle": {
                String text = nextText(xmlPullParser);
                handler.subtitle(text);
                break;
            }
            case "link":
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    handler.link(attrs.get("type"), attrs.get("href"), attrs.get("title"));
                } else {
                    handler.link(null, null, null);
                }
                break;
        }
    }

    private void entry(String tagName, XmlPullParser xmlPullParser, FeedHandler handler) {
        switch (tagName) {
            case "author": {
                isBeginEntryAuthor = true;
                break;
            }
            case "name": {
                if (isBeginEntryAuthor || isBeginEntryContributor) {
                    tempStrArr[0] = nextText(xmlPullParser);
                }
                break;
            }
            case "uri": {
                if (isBeginEntryAuthor || isBeginEntryContributor) {
                    tempStrArr[1] = nextText(xmlPullParser);
                }
                break;
            }
            case "email": {
                if (isBeginEntryAuthor || isBeginEntryContributor) {
                    tempStrArr[1] = nextText(xmlPullParser);
                }
                break;
            }
            case "content": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                String text = nextText(xmlPullParser);
                if (attrs != null) {
                    handler.entryContent(attrs.get("type"), attrs.get("language"), text);
                } else {
                    handler.entryContent(null, null, text);
                }
                break;
            }
            case "contributor": {
                isBeginEntryContributor = true;
                break;
            }
            case "id": {
                String text = nextText(xmlPullParser);
                handler.entryId(text);
                break;
            }
            case "link": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    handler.entryLink(attrs.get("type"), attrs.get("href"), attrs.get("title"));
                } else {
                    handler.entryLink(null, null, null);
                }
                break;
            }
            case "published": {
                String text = nextText(xmlPullParser);
                handler.entryPublished(DateUtils.parse(text), text);
                break;
            }
            case "summary": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                String text = nextText(xmlPullParser);
                if (attrs != null) {
                    handler.entrySummary(attrs.get("type"), attrs.get("language"), text);
                } else {
                    handler.entrySummary(null, null, text);
                }
                break;
            }
            case "category": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                String text = nextText(xmlPullParser);
                if (attrs != null) {
                    handler.entryTags(attrs.get("term"), attrs.get("scheme"), text);
                } else {
                    handler.entryTags(null, null, text);
                }
                break;
            }
            case "title": {
                String text = nextText(xmlPullParser);
                handler.entryTitle(text);
                break;
            }
            case "updated": {
                String text = nextText(xmlPullParser);
                handler.entryUpdated(DateUtils.parse(text), text);
                break;
            }
        }
    }

    @Override
    public void endTag(String tagName, FeedHandler handler) {
        switch (tagName) {
            case "feed":
                handler.end();
                break;
            case "entry":
                isBeginEntry = false;
                handler.entryEnd();
                break;
            case "author":
                handler.entryAuthor(tempStrArr[0], tempStrArr[1], tempStrArr[2]);
                emptyTempStrArr();
                isBeginEntryAuthor = false;
                break;
            case "contributor":
                handler.entryContributor(tempStrArr[0], tempStrArr[1], tempStrArr[2]);
                emptyTempStrArr();
                isBeginEntryContributor = false;
                break;
        }
    }

    private void emptyTempStrArr() {
        for (int i = 0; i < tempStrArr.length; i++) {
            tempStrArr[i] = null;
        }
    }
}
