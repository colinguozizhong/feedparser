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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RssParser extends GenericParser {
    private boolean isBeginChannel = false;
    private boolean isBeginImage = false;
    private boolean isBeginTextInput = false;
    private boolean isBeginSkipDays = false;
    private boolean isBeginSkipHours = false;
    private boolean isBeginItem = false;
    private List<String> tempList = null;

    @Override
    public void startTag(String tagName, XmlPullParser xmlPullParser, FeedHandler handler) {
        switch (tagName) {
            case "rss":
                handler.begin();
                break;
            case "channel":
                isBeginChannel = true;
                break;
            case "image":
                isBeginImage = true;
                break;
            case "skipDays":
                tempList = new ArrayList<>();
                isBeginSkipDays = true;
                break;
            case "skipHours":
                tempList = new ArrayList<>();
                isBeginSkipHours = true;
                break;
            case "textInput":
                isBeginTextInput = true;
                break;
            case "item":
                isBeginItem = true;
                handler.entryBegin();
                break;
        }
        if (isBeginChannel) {
            if (isBeginItem) {
                // item
                item(tagName, xmlPullParser, handler);
            } else if (isBeginImage) {
                // image
            } else if (isBeginSkipDays && tempList != null && "day".equals(tagName)) {
                // skipDays
                tempList.add(nextText(xmlPullParser));
            } else if (isBeginSkipHours && tempList != null && "hour".equals(tagName)) {
                // skipHours
                tempList.add(nextText(xmlPullParser));
            } else if (isBeginTextInput) {
                // textInput
            } else {
                // channel
                channel(tagName, xmlPullParser, handler);
            }
        }
    }

    private void channel(String tagName, XmlPullParser xmlPullParser, FeedHandler handler) {
        switch (tagName) {
            case "title": {
                String text = nextText(xmlPullParser);
                handler.title(text);
                break;
            }
            case "description": {
                String text = nextText(xmlPullParser);
                handler.subtitle(text);
                break;
            }
            case "link": {
                String text = nextText(xmlPullParser);
                handler.link(null, text, null);
                break;
            }
        }
    }

    private void item(String tagName, XmlPullParser xmlPullParser, FeedHandler handler) {
        switch (tagName) {
            case "author": {
                String text = nextText(xmlPullParser);
                handler.entryAuthor(text, null, null);
                break;
            }
            case "comments": {
                String text = nextText(xmlPullParser);
                handler.entryComments(text);
                break;
            }
            case "body": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    handler.entryContent(attrs.get("type"), attrs.get("language"), attrs.get("content"));
                } else {
                    handler.entryContent(null, null, null);
                }
                break;
            }
            case "contributor": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    handler.entryContributor(attrs.get("name"), attrs.get("href"), attrs.get("email"));
                } else {
                    handler.entryContributor(null, null, null);
                }
                break;
            }
            case "enclosure": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    handler.entryEnclosure(attrs.get("length"), attrs.get("type"), attrs.get("url"));
                } else {
                    handler.entryEnclosure(null, null, null);
                }
                break;
            }
            case "expirationDate": {
                String text = nextText(xmlPullParser);
                handler.entryPublished(DateUtils.parse(text), text);
                break;
            }
            case "guid": {
                String text = nextText(xmlPullParser);
                handler.entryId(text);
                break;
            }
            case "link": {
                String text = nextText(xmlPullParser);
                handler.entryLink(null, text, null);
                break;
            }
            case "pubDate": {
                String text = nextText(xmlPullParser);
                handler.entryPublished(DateUtils.parse(text), text);
                break;
            }
            case "source": {
                String text = nextText(xmlPullParser);
                handler.entrySource(text);
                break;
            }
            case "description": {
                String text = nextText(xmlPullParser);
                handler.entrySummary(null, null, text);
                break;
            }
            case "category": {
                String text = nextText(xmlPullParser);
                handler.entryTags(null, null, text);
                break;
            }
            case "title": {
                String text = nextText(xmlPullParser);
                handler.entryTitle(text);
                break;
            }
        }
    }

    @Override
    public void endTag(String tagName, FeedHandler handler) {
        switch (tagName) {
            case "rss":
                handler.end();
                break;
            case "channel":
                isBeginChannel = false;
                break;
            case "image":
                isBeginImage = false;
                break;
            case "skipDays":
                isBeginSkipDays = false;
                handler.skipDays(tempList);
                break;
            case "skipHours":
                isBeginSkipHours = false;
                handler.skipHours(tempList);
                break;
            case "textInput":
                isBeginTextInput = false;
                break;
            case "item":
                handler.entryEnd();
                isBeginItem = false;
                break;
        }
    }
}
