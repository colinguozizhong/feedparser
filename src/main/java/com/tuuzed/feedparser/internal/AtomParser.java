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

import java.util.HashMap;
import java.util.Map;

public class AtomParser extends GenericParser {
    private boolean isBeginEntry = false;
    private boolean isBeginEntryAuthor = false;
    private boolean isBeginEntryContributor = false;
    private Map<String, String> tmpMap = new HashMap<>();

    public AtomParser(@NotNull FeedCallback callback) {
        super(callback);
    }

    @Override
    public void startTag(@NotNull String tag, @NotNull XmlPullParser xmlPullParser) {
        switch (tag) {
            case "feed":
                callback.start();
                break;
            case "entry":
                isBeginEntry = true;
                callback.itemStart();
                break;
        }
        if (isBeginEntry) {
            entry(tag, xmlPullParser);
        } else {
            feed(tag, xmlPullParser);
        }
    }

    @Override
    public void endTag(@NotNull String tag) {
        switch (tag) {
            case "feed":
                callback.end();
                break;
            case "entry":
                isBeginEntry = false;
                callback.itemEnd();
                break;
            case "author":
                String author = tmpMap.get("name");
                if (author != null) {
                    callback.itemAuthor(author, tmpMap.get("uri"), tmpMap.get("email"));
                }
                tmpMap.clear();
                isBeginEntryAuthor = false;
                break;
            case "contributor":
                String contributor = tmpMap.get("name");
                if (contributor != null) {
                    callback.itemContributor(contributor, tmpMap.get("uri"), tmpMap.get("email"));
                }
                tmpMap.clear();
                isBeginEntryContributor = false;
                break;
        }
    }

    private void feed(@NotNull String tag, @NotNull XmlPullParser xmlPullParser) {
        switch (tag) {
            case "title": {
                Map<String, String> attrs = XmlPullParserUtils.getAttrs(xmlPullParser);
                String title = XmlPullParserUtils.getNextText(xmlPullParser);
                if (title != null) {
                    callback.title(title, attrs.get("type"));
                }
                break;
            }
            case "subtitle": {
                Map<String, String> attrs = XmlPullParserUtils.getAttrs(xmlPullParser);
                String subtitle = XmlPullParserUtils.getNextText(xmlPullParser);
                if (subtitle != null) {
                    callback.subtitle(subtitle, attrs.get("type"));
                }
                break;
            }
            case "link": {
                Map<String, String> attrs = XmlPullParserUtils.getAttrs(xmlPullParser);
                String link = attrs.get("href");
                if (link != null) {
                    callback.link(link, attrs.get("type"), attrs.get("title"));
                }
                break;
            }
            case "rights": {
                Map<String, String> attrs = XmlPullParserUtils.getAttrs(xmlPullParser);
                String rights = XmlPullParserUtils.getNextText(xmlPullParser);
                if (rights != null) {
                    callback.copyright(rights, attrs.get("type"));
                }
            }
            case "generator": {
                Map<String, String> attrs = XmlPullParserUtils.getAttrs(xmlPullParser);
                String generator = XmlPullParserUtils.getNextText(xmlPullParser);
                if (generator != null) {
                    callback.copyright(generator, attrs.get("type"));
                }
            }
        }
    }

    private void entry(@NotNull String tag, @NotNull XmlPullParser xmlPullParser) {
        switch (tag) {
            case "author": {
                isBeginEntryAuthor = true;
                break;
            }
            case "name": {
                if (isBeginEntryAuthor || isBeginEntryContributor) {
                    tmpMap.put("name", XmlPullParserUtils.getNextText(xmlPullParser));
                }
                break;
            }
            case "uri": {
                if (isBeginEntryAuthor || isBeginEntryContributor) {
                    tmpMap.put("uri", XmlPullParserUtils.getNextText(xmlPullParser));
                }
                break;
            }
            case "email": {
                if (isBeginEntryAuthor || isBeginEntryContributor) {
                    tmpMap.put("email", XmlPullParserUtils.getNextText(xmlPullParser));
                }
                break;
            }
            case "content": {
                Map<String, String> attrs = XmlPullParserUtils.getAttrs(xmlPullParser);
                String content = XmlPullParserUtils.getNextText(xmlPullParser);
                if (content != null) {
                    callback.itemContent(content, attrs.get("type"), attrs.get("language"));
                }

                break;
            }
            case "contributor": {
                isBeginEntryContributor = true;
                break;
            }
            case "id": {
                String id = XmlPullParserUtils.getNextText(xmlPullParser);
                if (id != null) {
                    callback.itemId(id);
                }
                break;
            }
            case "link": {
                Map<String, String> attrs = XmlPullParserUtils.getAttrs(xmlPullParser);
                String link = attrs.get("href");
                if (link != null) {
                    callback.itemLink(link, attrs.get("type"), attrs.get("title"));
                }
                break;
            }
            case "published": {
                String published = XmlPullParserUtils.getNextText(xmlPullParser);
                if (published != null) {
                    callback.itemPublished(DateParser.parse(published), published);
                }
                break;
            }
            case "summary": {
                Map<String, String> attrs = XmlPullParserUtils.getAttrs(xmlPullParser);
                String summary = XmlPullParserUtils.getNextText(xmlPullParser);
                if (summary != null) {
                    callback.itemSummary(summary, attrs.get("type"), attrs.get("language"));
                }
                break;
            }
            case "category": {
                Map<String, String> attrs = XmlPullParserUtils.getAttrs(xmlPullParser);
                String category = XmlPullParserUtils.getNextText(xmlPullParser);
                if (category != null) {
                    callback.itemCategory(category, attrs.get("term"), attrs.get("scheme"));
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
            case "updated": {
                String updated = XmlPullParserUtils.getNextText(xmlPullParser);
                if (updated != null) {
                    callback.itemUpdated(DateParser.parse(updated), updated);
                }
                break;
            }
        }
    }

}
