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
            callback.imageBegin();
            isBeginImage = true;
        } else if ("skipDays".equals(tagName)) {
            isBeginSkipDays = true;
            tempList = new ArrayList<String>();
        } else if ("skipHours".equals(tagName)) {
            isBeginSkipHours = true;
            tempList = new ArrayList<String>();
        } else if ("textInput".equals(tagName)) {
            callback.textInputBegin();
            isBeginTextInput = true;
        } else if ("item".equals(tagName)) {
            callback.itemBegin();
            isBeginItem = true;
        }
        if (isBeginChannel) {
            if (isBeginItem) {
                item(tagName, xmlPullParser, callback);
            } else if (isBeginImage) {
                image(tagName, xmlPullParser, callback);
            } else if (isBeginSkipDays) {
                if (tempList != null && "skipDays".equals(tagName)) {
                    tempList.add(nextText(xmlPullParser));
                }
            } else if (isBeginSkipHours) {
                if (tempList != null && "skipHours".equals(tagName)) {
                    tempList.add(nextText(xmlPullParser));
                }
            } else if (isBeginTextInput) {
                textInput(tagName, xmlPullParser, callback);
            } else {
                channel(tagName, xmlPullParser, callback);
            }
        }
    }

    private void rss(XmlPullParser xmlPullParser, FeedCallback callback) {
        Map<String, String> attrs = getAttrs(xmlPullParser);
        String version = null;
        if (attrs != null) {
            version = attrs.get("version");
        }
        callback.rss(version);
    }

    private void channel(String tagName, XmlPullParser xmlPullParser, FeedCallback callback) {
        if ("title".equals(tagName)) {
            callback.title(nextText(xmlPullParser));
        } else if ("link".equals(tagName)) {
            callback.link(nextText(xmlPullParser));
        } else if ("description".equals(tagName)) {
            callback.description(nextText(xmlPullParser));
        } else if ("category".equals(tagName)) {
            String domain = null;
            Map<String, String> attrs = getAttrs(xmlPullParser);
            if (attrs != null) {
                domain = attrs.get("domain");
            }
            callback.category(nextText(xmlPullParser), domain);
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
            callback.copyright(nextText(xmlPullParser));
        } else if ("docs".equals(tagName)) {
            callback.docs(nextText(xmlPullParser));
        } else if ("generator".equals(tagName)) {
            callback.generator(nextText(xmlPullParser));
        } else if ("language".equals(tagName)) {
            callback.language(nextText(xmlPullParser));
        } else if ("lastBuildDate".equals(tagName)) {
            String text = nextText(xmlPullParser);
            callback.lastBuildDate(DateUtils.parse(text), text);
        } else if ("managingEditor".equals(tagName)) {
            callback.managingEditor(nextText(xmlPullParser));
        } else if ("pubDate".equals(tagName)) {
            String text = nextText(xmlPullParser);
            callback.pubDate(DateUtils.parse(text), text);
        } else if ("rating".equals(tagName)) {
            callback.rating(nextText(xmlPullParser));
        } else if ("ttl".equals(tagName)) {
            callback.ttl(nextText(xmlPullParser));
        } else if ("webMaster".equals(tagName)) {
            callback.webMaster(nextText(xmlPullParser));
        }
    }

    private void image(String tagName, XmlPullParser xmlPullParser, FeedCallback callback) {
        if ("title".equals(tagName)) {
            callback.imageTitle(nextText(xmlPullParser));
        } else if ("height".equals(tagName)) {
            callback.imageHeight(nextText(xmlPullParser));
        } else if ("width".equals(tagName)) {
            callback.imageWidth(nextText(xmlPullParser));
        } else if ("link".equals(tagName)) {
            callback.imageLink(nextText(xmlPullParser));
        } else if ("description".equals(tagName)) {
            callback.imageDescription(nextText(xmlPullParser));
        } else if ("url".equals(tagName)) {
            callback.imageUrl(nextText(xmlPullParser));
        }
    }

    private void textInput(String tagName, XmlPullParser xmlPullParser, FeedCallback callback) {
        if ("title".equals(tagName)) {
            callback.textInputTitle(nextText(xmlPullParser));
        } else if ("link".equals(tagName)) {
            callback.textInputLink(nextText(xmlPullParser));
        } else if ("description".equals(tagName)) {
            callback.textInputDescription(nextText(xmlPullParser));
        } else if ("name".equals(tagName)) {
            callback.textInputName(nextText(xmlPullParser));
        }
    }


    @Override
    public void endTag(String tagName, FeedCallback callback) {
        if ("rss".equals(tagName)) {
            callback.end();
        } else if ("channel".equals(tagName)) {
            isBeginChannel = false;
        } else if ("image".equals(tagName)) {
            callback.imageEnd();
            isBeginImage = false;
        } else if ("skipDays".equals(tagName)) {
            isBeginSkipDays = false;
            callback.skipDays(tempList);
        } else if ("skipHours".equals(tagName)) {
            isBeginSkipHours = false;
            callback.skipHours(tempList);
        } else if ("textInput".equals(tagName)) {
            isBeginTextInput = false;
            callback.textInputEnd();
        } else if ("item".equals(tagName)) {
            isBeginItem = false;
            callback.itemEnd();
        }
    }

    private void item(String tagName, XmlPullParser xmlPullParser, FeedCallback callback) {
        if ("title".equals(tagName)) {
            callback.itemTitle(nextText(xmlPullParser));
        } else if ("link".equals(tagName)) {
            callback.itemLink(nextText(xmlPullParser));
        } else if ("author".equals(tagName)) {
            callback.itemAuthor(nextText(xmlPullParser));
        } else if ("category".equals(tagName)) {
            String domain = null;
            Map<String, String> attrs = getAttrs(xmlPullParser);
            if (attrs != null) {
                domain = attrs.get("domain");
            }
            callback.itemCategory(nextText(xmlPullParser), domain);
        } else if ("pubDate".equals(tagName)) {
            String text = nextText(xmlPullParser);
            callback.itemPubDate(DateUtils.parse(text), text);
        } else if ("comments".equals(tagName)) {
            callback.itemComments(nextText(xmlPullParser));
        } else if ("description".equals(tagName)) {
            callback.itemDescription(nextText(xmlPullParser));
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
            callback.itemEnclosure(length, type, url);
        } else if ("guid".equals(tagName)) {
            boolean isPermaLink = false;
            Map<String, String> attrs = getAttrs(xmlPullParser);
            if (attrs != null) {
                isPermaLink = Boolean.parseBoolean(attrs.get("isPermaLink"));
            }
            callback.itemGuid(nextText(xmlPullParser), isPermaLink);
        } else if ("source".equals(tagName)) {
            String url = null;
            Map<String, String> attrs = getAttrs(xmlPullParser);
            if (attrs != null) {
                url = attrs.get("url");
            }
            callback.itemSource(nextText(xmlPullParser), url);
        }
    }
}
