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
package com.tuuzed.feedparser.internal;


import com.tuuzed.feedparser.FeedCallback;
import com.tuuzed.feedparser.util.DateParser;
import com.tuuzed.feedparser.util.XmlPullParserUtils;
import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RssParser extends GenericParser {
    private boolean isStartChannel = false;
    private boolean isStartImage = false;
    private boolean isStartTextInput = false;
    private boolean isStartSkipDays = false;
    private boolean isStartSkipHours = false;
    private boolean isStartItem = false;
    private List<String> tmpList = new LinkedList<>();

    public RssParser(@NotNull FeedCallback callback) {
        super(callback);
    }

    @Override
    public void startTag(@NotNull String tag, @NotNull XmlPullParser xmlPullParser) {
        switch (tag) {
            case "rss":
                callback.start();
                break;
            case "channel":
                isStartChannel = true;
                break;
            case "image":
                isStartImage = true;
                break;
            case "skipDays":
                isStartSkipDays = true;
                break;
            case "skipHours":
                isStartSkipHours = true;
                break;
            case "textInput":
                isStartTextInput = true;
                break;
            case "item":
                isStartItem = true;
                callback.itemStart();
                break;
        }
        if (isStartChannel) {
            if (isStartItem) {
                // item
                item(tag, xmlPullParser);
            } else if (isStartImage) {
                // image
            } else if (isStartSkipDays && tmpList != null && "day".equals(tag)) {
                // skipDays
                String skipDay = XmlPullParserUtils.getNextText(xmlPullParser);
                if (skipDay != null) {
                    tmpList.add(skipDay);
                }
            } else if (isStartSkipHours && tmpList != null && "hour".equals(tag)) {
                // skipHours
                String skipHour = XmlPullParserUtils.getNextText(xmlPullParser);
                if (skipHour != null) {
                    tmpList.add(skipHour);
                }
            } else if (isStartTextInput) {
                // textInput
            } else {
                // channel
                channel(tag, xmlPullParser);
            }
        }
    }

    @Override
    public void endTag(@NotNull String tag) {
        switch (tag) {
            case "rss":
                callback.end();
                break;
            case "channel":
                isStartChannel = false;
                break;
            case "image":
                isStartImage = false;
                break;
            case "skipDays":
                isStartSkipDays = false;
                callback.skipDays(tmpList);
                tmpList.clear();
                break;
            case "skipHours":
                isStartSkipHours = false;
                callback.skipHours(tmpList);
                tmpList.clear();
                break;
            case "textInput":
                isStartTextInput = false;
                break;
            case "item":
                callback.itemEnd();
                isStartItem = false;
                break;
        }
    }

    private void channel(@NotNull String tag, @NotNull XmlPullParser xmlPullParser) {
        switch (tag) {
            case "title": {
                String title = XmlPullParserUtils.getNextText(xmlPullParser);
                if (title != null) {
                    callback.title(title, null);
                }
                break;
            }
            case "description": {
                String subtitle = XmlPullParserUtils.getNextText(xmlPullParser);
                if (subtitle != null) {
                    callback.subtitle(subtitle, null);
                }
            }
            case "link": {
                String link = XmlPullParserUtils.getNextText(xmlPullParser);
                if (link != null) {
                    callback.link(link, null, null);
                }
                break;
            }
            case "rights": {
                String rights = XmlPullParserUtils.getNextText(xmlPullParser);
                if (rights != null) {
                    callback.copyright(rights, null);
                }
            }
            case "generator": {
                String generator = XmlPullParserUtils.getNextText(xmlPullParser);
                if (generator != null) {
                    callback.generator(generator, null, null);
                }
            }
        }
    }

    private void item(@NotNull String tag, @NotNull XmlPullParser xmlPullParser) {
        switch (tag) {
            case "author": {
                String author = XmlPullParserUtils.getNextText(xmlPullParser);
                if (author != null) {
                    callback.itemAuthor(author, null, null);
                }
                break;
            }
            case "comments": {
                String comments = XmlPullParserUtils.getNextText(xmlPullParser);
                if (comments != null) {
                    callback.itemComments(comments);
                }
                break;
            }
            case "body": {
                Map<String, String> attrs = XmlPullParserUtils.getAttrs(xmlPullParser);
                String content = attrs.get("content");
                if (content != null) {
                    callback.itemContent(content, attrs.get("type"), attrs.get("language"));
                }
                break;
            }
            case "contributor": {
                Map<String, String> attrs = XmlPullParserUtils.getAttrs(xmlPullParser);
                String contributor = attrs.get("name");
                if (contributor != null) {
                    callback.itemContributor(contributor, attrs.get("href"), attrs.get("email"));
                }
                break;
            }
            case "enclosure": {
                Map<String, String> attrs = XmlPullParserUtils.getAttrs(xmlPullParser);
                callback.itemEnclosure(attrs.get("length"), attrs.get("type"), attrs.get("url"));
                break;
            }
            case "expirationDate": {
                String expired = XmlPullParserUtils.getNextText(xmlPullParser);
                if (expired != null) {
                    callback.itemExpired(DateParser.parse(expired), expired);
                }
                break;
            }
            case "guid": {
                String id = XmlPullParserUtils.getNextText(xmlPullParser);
                if (id != null) {
                    callback.itemId(id);
                }
                break;
            }
            case "link": {
                String link = XmlPullParserUtils.getNextText(xmlPullParser);
                if (link != null) {
                    callback.itemLink(link, null, null);
                }
                break;
            }
            case "pubDate": {
                String published = XmlPullParserUtils.getNextText(xmlPullParser);
                if (published != null) {
                    callback.itemPublished(DateParser.parse(published), published);
                }
                break;
            }
            case "source": {
                String source = XmlPullParserUtils.getNextText(xmlPullParser);
                if (source != null) {
                    callback.itemSource(source);
                }
                break;
            }
            case "description": {
                String summary = XmlPullParserUtils.getNextText(xmlPullParser);
                if (summary != null) {
                    callback.itemSummary(summary, null, null);
                }
                break;
            }
            case "category": {
                String category = XmlPullParserUtils.getNextText(xmlPullParser);
                if (category != null) {
                    callback.itemCategory(category, null, null);
                }
                break;
            }
            case "title": {
                String title = XmlPullParserUtils.getNextText(xmlPullParser);
                if (title != null) {
                    callback.itemTitle(title);
                }
                break;
            }
        }
    }

}
