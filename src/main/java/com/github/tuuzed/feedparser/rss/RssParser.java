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
package com.github.tuuzed.feedparser.rss;

import com.github.tuuzed.feedparser.callback.EntryCallback;
import com.github.tuuzed.feedparser.callback.FeedCallback;
import com.github.tuuzed.feedparser.callback.ImageCallback;
import com.github.tuuzed.feedparser.callback.TextInputCallback;
import com.github.tuuzed.feedparser.util.DateUtils;
import com.github.tuuzed.feedparser.xml.AbstractParser;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RssParser extends AbstractParser {
    private boolean isBeginChannel = false;
    private boolean isBeginImage = false;
    private boolean isBeginTextInput = false;
    private boolean isBeginSkipDays = false;
    private boolean isBeginSkipHours = false;
    private boolean isBeginItem = false;
    private List<String> tempList = null;

    private static final String DEF_TYPE = "text/plain";
    private static final String DEF_LANGUAGE = null;

    @Override
    public void startTag(String tagName, XmlPullParser xmlPullParser, FeedCallback callback) {
        switch (tagName) {
            case "rss":
                callback.begin();
                rss(xmlPullParser, callback);
                break;
            case "channel":
                isBeginChannel = true;
                break;
            case "image":
                if (callback.imageCallback != null) {
                    callback.imageCallback.begin();
                }
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
                if (callback.textInputCallback != null) {
                    callback.textInputCallback.begin();
                }
                isBeginTextInput = true;
                break;
            case "item":
                if (callback.entryCallback != null) {
                    callback.entryCallback.begin();
                }
                isBeginItem = true;
                break;
        }
        if (!isBeginChannel) {
            return;
        }
        if (isBeginItem) {
            item(tagName, xmlPullParser, callback.entryCallback);
        } else if (isBeginImage) {
            image(tagName, xmlPullParser, callback.imageCallback);
        } else if (isBeginSkipDays && tempList != null && "day".equals(tagName)) {
            tempList.add(nextText(xmlPullParser));
        } else if (isBeginSkipHours && tempList != null && "hour".equals(tagName)) {
            tempList.add(nextText(xmlPullParser));
        } else if (isBeginTextInput) {
            textInput(tagName, xmlPullParser, callback.textInputCallback);
        } else {
            channel(tagName, xmlPullParser, callback);
        }
    }

    private void rss(XmlPullParser xmlPullParser, FeedCallback callback) {
        Map<String, String> attrs = getAttrs(xmlPullParser);
        if (attrs != null) {
            callback.feed("rss", attrs.get("version"));
        } else {
            callback.feed("rss", null);
        }
    }

    private void channel(String tagName, XmlPullParser xmlPullParser, FeedCallback callback) {
        switch (tagName) {
            case "title":
                callback.title(DEF_TYPE, DEF_LANGUAGE, nextText(xmlPullParser));
                break;
            case "link":
                callback.link(nextText(xmlPullParser));
                break;
            case "description":
                callback.subtitle(DEF_TYPE, DEF_LANGUAGE, nextText(xmlPullParser));
                break;
            case "category": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    callback.tags(nextText(xmlPullParser), attrs.get("domain"));
                } else {
                    callback.tags(nextText(xmlPullParser), null);
                }
                break;
            }
            case "cloud": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    callback.cloud(attrs.get("domain"), attrs.get("port"),
                            attrs.get("path"), attrs.get("registerProcedure"),
                            attrs.get("protocol"));
                } else {
                    callback.cloud(null, null, null, null, null);
                }
                break;
            }
            case "copyright":
                callback.rights(DEF_TYPE, DEF_LANGUAGE, nextText(xmlPullParser));
                break;
            case "docs":
                callback.docs(nextText(xmlPullParser));
                break;
            case "generator":
                callback.generator(nextText(xmlPullParser));
                break;
            case "language":
                callback.language(nextText(xmlPullParser));
                break;
            case "lastBuildDate": {
                String text = nextText(xmlPullParser);
                callback.updated(DateUtils.parse(text), text);
                break;
            }
            case "managingEditor":
                callback.authors(nextText(xmlPullParser));
                break;
            case "pubDate": {
                String text = nextText(xmlPullParser);
                callback.published(DateUtils.parse(text), text);
                break;
            }
            case "rating":
                callback.rating(nextText(xmlPullParser));
                break;
            case "ttl":
                callback.ttl(nextText(xmlPullParser));
                break;
            case "webMaster":
                callback.webMaster(nextText(xmlPullParser));
                break;
        }
    }

    private void image(String tagName, XmlPullParser xmlPullParser, ImageCallback callback) {
        if (callback == null) {
            return;
        }
        switch (tagName) {
            case "title":
                callback.title(nextText(xmlPullParser));
                break;
            case "height":
                callback.height(nextText(xmlPullParser));
                break;
            case "width":
                callback.width(nextText(xmlPullParser));
                break;
            case "link":
                callback.link(nextText(xmlPullParser));
                break;
            case "description":
                callback.description(nextText(xmlPullParser));
                break;
            case "url":
                callback.href(nextText(xmlPullParser));
                break;
        }
    }

    private void textInput(String tagName, XmlPullParser xmlPullParser, TextInputCallback callback) {
        if (callback == null) {
            return;
        }
        switch (tagName) {
            case "title":
                callback.title(nextText(xmlPullParser));
                break;
            case "link":
                callback.link(nextText(xmlPullParser));
                break;
            case "description":
                callback.description(nextText(xmlPullParser));
                break;
            case "name":
                callback.name(nextText(xmlPullParser));
                break;
        }
    }

    private void item(String tagName, XmlPullParser xmlPullParser, EntryCallback callback) {
        if (callback == null) {
            return;
        }
        switch (tagName) {
            case "title":
                callback.title(nextText(xmlPullParser));
                break;
            case "link":
                callback.link(nextText(xmlPullParser));
                break;
            case "author":
                callback.authors(nextText(xmlPullParser));
                break;
            case "category": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    callback.tags(nextText(xmlPullParser), attrs.get("domain"));
                } else {
                    callback.tags(nextText(xmlPullParser), null);
                }
                break;
            }
            case "pubDate": {
                String text = nextText(xmlPullParser);
                callback.published(DateUtils.parse(text), text);
                break;
            }
            case "comments":
                callback.comments(nextText(xmlPullParser));
                break;
            case "description": {
                String text = nextText(xmlPullParser);
                callback.summary(DEF_TYPE, DEF_LANGUAGE, text);
                callback.content(DEF_TYPE, DEF_LANGUAGE, text);
                break;
            }
            case "enclosure": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    callback.enclosure(attrs.get("length"), attrs.get("type"), attrs.get("url"));
                } else {
                    callback.enclosure(null, null, null);
                }
                break;
            }
            case "guid": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    callback.id(nextText(xmlPullParser), Boolean.parseBoolean(attrs.get("isPermaLink")));
                } else {
                    callback.id(nextText(xmlPullParser), null);
                }
                break;
            }
            case "source": {
                Map<String, String> attrs = getAttrs(xmlPullParser);
                if (attrs != null) {
                    callback.source(nextText(xmlPullParser), attrs.get("url"));
                } else {
                    callback.source(nextText(xmlPullParser), null);
                }
                break;
            }
        }
    }

    /**
     * 标签结束
     *
     * @param tagName  结束标签名
     * @param callback 回调
     */
    @Override
    public void endTag(String tagName, FeedCallback callback) {
        switch (tagName) {
            case "rss":
                callback.end();
                break;
            case "channel":
                isBeginChannel = false;
                break;
            case "image":
                if (callback != null) {
                    callback.end();
                }
                isBeginImage = false;
                break;
            case "skipDays":
                isBeginSkipDays = false;
                callback.skipDays(tempList);
                break;
            case "skipHours":
                isBeginSkipHours = false;
                callback.skipHours(tempList);
                break;
            case "textInput":
                isBeginTextInput = false;
                if (callback.textInputCallback != null) {
                    callback.textInputCallback.end();
                }
                break;
            case "item":
                if (callback.entryCallback != null) {
                    callback.entryCallback.end();
                }
                isBeginItem = false;
                break;
        }
    }
}
