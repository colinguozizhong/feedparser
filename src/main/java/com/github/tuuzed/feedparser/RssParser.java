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
package com.github.tuuzed.feedparser;

import com.github.tuuzed.feedparser.callback.EntryCallback;
import com.github.tuuzed.feedparser.callback.FeedCallback;
import com.github.tuuzed.feedparser.callback.ImageCallback;
import com.github.tuuzed.feedparser.callback.TextInputCallback;
import com.github.tuuzed.feedparser.internal.AbstractParser;
import com.github.tuuzed.feedparser.util.DateUtils;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RssParser extends AbstractParser {
    private boolean isBeginChannel = false;
    private boolean isBeginImage = false;
    private boolean isBeginTextInput = false;
    private boolean isBeginSkipDays = false;
    private boolean isBeginSkipHours = false;
    private boolean isBeginItem = false;
    private List<String> tempList = null;

    private static final String DEF_TYPE = "text/plain";
    private static final String DEF_LANGUAGE = null;

    RssParser() {
    }


    @Override
    public void startTag(String tagName, XmlPullParser xmlPullParser, FeedCallback callback) {
        if ("rss".equals(tagName)) {
            callback.begin();
            rss(xmlPullParser, callback);
        } else if ("channel".equals(tagName)) {
            isBeginChannel = true;
        } else if ("image".equals(tagName)) {
            if (callback.imageCallback != null) {
                callback.imageCallback.begin();
            }
            isBeginImage = true;
        } else if ("skipDays".equals(tagName)) {
            isBeginSkipDays = true;
            tempList = new ArrayList<String>();
        } else if ("skipHours".equals(tagName)) {
            isBeginSkipHours = true;
            tempList = new ArrayList<String>();
        } else if ("textInput".equals(tagName)) {
            if (callback.textInputCallback != null) {
                callback.textInputCallback.begin();
            }
            isBeginTextInput = true;
        } else if ("item".equals(tagName)) {
            if (callback.entryCallback != null) {
                callback.entryCallback.begin();
            }
            isBeginItem = true;
        }
        if (isBeginChannel) {
            if (isBeginItem) {
                item(tagName, xmlPullParser, callback.entryCallback);
            } else if (isBeginImage) {
                image(tagName, xmlPullParser, callback.imageCallback);
            } else if (isBeginSkipDays) {
                if (tempList != null && "skipDays".equals(tagName)) {
                    tempList.add(nextText(xmlPullParser));
                }
            } else if (isBeginSkipHours) {
                if (tempList != null && "skipHours".equals(tagName)) {
                    tempList.add(nextText(xmlPullParser));
                }
            } else if (isBeginTextInput) {
                textInput(tagName, xmlPullParser, callback.textInputCallback);
            } else {
                channel(tagName, xmlPullParser, callback);
            }
        }
    }

    @Override
    public void endTag(String tagName, FeedCallback callback) {
        if ("rss".equals(tagName)) {
            callback.end();
        } else if ("channel".equals(tagName)) {
            isBeginChannel = false;
        } else if ("image".equals(tagName)) {
            if (callback.imageCallback != null) {
                callback.imageCallback.end();
            }
            isBeginImage = false;
        } else if ("skipDays".equals(tagName)) {
            isBeginSkipDays = false;
            callback.skipDays(tempList);
        } else if ("skipHours".equals(tagName)) {
            isBeginSkipHours = false;
            callback.skipHours(tempList);
        } else if ("textInput".equals(tagName)) {
            isBeginTextInput = false;
            if (callback.textInputCallback != null) {
                callback.textInputCallback.end();
            }
        } else if ("item".equals(tagName)) {
            if (callback.entryCallback != null) {
                callback.entryCallback.end();
            }
            isBeginItem = false;
        }
    }

    private void rss(XmlPullParser xmlPullParser, FeedCallback callback) {
        Map<String, String> attrs = getAttrs(xmlPullParser);
        String version = null;
        if (attrs != null) {
            version = attrs.get("version");
        }
        callback.feed("rss", version);
    }

    private void channel(String tagName, XmlPullParser xmlPullParser, FeedCallback callback) {
        if ("title".equals(tagName)) {
            callback.title(DEF_TYPE, DEF_LANGUAGE, nextText(xmlPullParser));
        } else if ("link".equals(tagName)) {
            callback.link(nextText(xmlPullParser));
        } else if ("description".equals(tagName)) {
            callback.subtitle(DEF_TYPE, DEF_LANGUAGE, nextText(xmlPullParser));
        } else if ("category".equals(tagName)) {
            String domain = null;
            Map<String, String> attrs = getAttrs(xmlPullParser);
            if (attrs != null) {
                domain = attrs.get("domain");
            }
            callback.tags(nextText(xmlPullParser), domain);
        } else if ("cloud".equals(tagName)) {
            Map<String, String> attrs = getAttrs(xmlPullParser);
            String domain = null;
            String port = null;
            String path = null;
            String registerProcedure = null;
            String protocol = null;
            if (attrs != null) {
                domain = attrs.get("domain");
                port = attrs.get("port");
                path = attrs.get("path");
                registerProcedure = attrs.get("registerProcedure");
                protocol = attrs.get("protocol");
            }
            callback.cloud(domain, port, path, registerProcedure, protocol);
        } else if ("copyright".equals(tagName)) {
            callback.rights(DEF_TYPE, DEF_LANGUAGE, nextText(xmlPullParser));
        } else if ("docs".equals(tagName)) {
            callback.docs(nextText(xmlPullParser));
        } else if ("generator".equals(tagName)) {
            callback.generator(null, null, nextText(xmlPullParser));
        } else if ("language".equals(tagName)) {
            callback.language(nextText(xmlPullParser));
        } else if ("lastBuildDate".equals(tagName)) {
            String text = nextText(xmlPullParser);
            callback.updated(DateUtils.parse(text), text);
        } else if ("managingEditor".equals(tagName)) {
            callback.authors(nextText(xmlPullParser));
        } else if ("pubDate".equals(tagName)) {
            String text = nextText(xmlPullParser);
            callback.published(DateUtils.parse(text), text);
        } else if ("rating".equals(tagName)) {
            callback.rating(nextText(xmlPullParser));
        } else if ("ttl".equals(tagName)) {
            callback.ttl(nextText(xmlPullParser));
        } else if ("webMaster".equals(tagName)) {
            callback.webMaster(nextText(xmlPullParser));
        }
    }

    private void image(String tagName, XmlPullParser xmlPullParser, ImageCallback callback) {
        if (callback == null) {
            return;
        }
        if ("title".equals(tagName)) {
            callback.title(DEF_TYPE, DEF_LANGUAGE, nextText(xmlPullParser));
        } else if ("height".equals(tagName)) {
            callback.height(nextText(xmlPullParser));
        } else if ("width".equals(tagName)) {
            callback.width(nextText(xmlPullParser));
        } else if ("link".equals(tagName)) {
            callback.links(null, DEF_TYPE, nextText(xmlPullParser));
        } else if ("description".equals(tagName)) {
            callback.description(nextText(xmlPullParser));
        } else if ("url".equals(tagName)) {
            callback.link(nextText(xmlPullParser));
        }
    }

    private void textInput(String tagName, XmlPullParser xmlPullParser, TextInputCallback callback) {
        if (callback == null) {
            return;
        }
        if ("title".equals(tagName)) {
            callback.title(nextText(xmlPullParser));
        } else if ("link".equals(tagName)) {
            callback.link(nextText(xmlPullParser));
        } else if ("description".equals(tagName)) {
            callback.description(nextText(xmlPullParser));
        } else if ("name".equals(tagName)) {
            callback.name(nextText(xmlPullParser));
        }
    }

    private void item(String tagName, XmlPullParser xmlPullParser, EntryCallback callback) {
        if (callback == null) {
            return;
        }
        if ("title".equals(tagName)) {
            callback.title(DEF_TYPE, DEF_LANGUAGE, nextText(xmlPullParser));
        } else if ("link".equals(tagName)) {
            callback.link(nextText(xmlPullParser));
        } else if ("author".equals(tagName)) {
            callback.authors(nextText(xmlPullParser));
        } else if ("category".equals(tagName)) {
            String domain = null;
            Map<String, String> attrs = getAttrs(xmlPullParser);
            if (attrs != null) {
                domain = attrs.get("domain");
            }
            callback.tags(nextText(xmlPullParser), domain);
        } else if ("pubDate".equals(tagName)) {
            String text = nextText(xmlPullParser);
            callback.published(DateUtils.parse(text), text);
        } else if ("comments".equals(tagName)) {
            callback.comments(nextText(xmlPullParser));
        } else if ("description".equals(tagName)) {
            callback.summary(DEF_TYPE, DEF_LANGUAGE, nextText(xmlPullParser));
        } else if ("enclosure".equals(tagName)) {
            String length = null;
            String type = null;
            String url = null;
            Map<String, String> attrs = getAttrs(xmlPullParser);
            if (attrs != null) {
                length = attrs.get("length");
                type = attrs.get("type");
                url = attrs.get("url");
            }
            callback.enclosure(length, type, url);
        } else if ("guid".equals(tagName)) {
            boolean isPermaLink = false;
            Map<String, String> attrs = getAttrs(xmlPullParser);
            if (attrs != null) {
                isPermaLink = Boolean.parseBoolean(attrs.get("isPermaLink"));
            }
            callback.id(nextText(xmlPullParser), isPermaLink);
        } else if ("source".equals(tagName)) {
            String url = null;
            Map<String, String> attrs = getAttrs(xmlPullParser);
            if (attrs != null) {
                url = attrs.get("url");
            }
            callback.source(nextText(xmlPullParser), url);
        }
    }
}
