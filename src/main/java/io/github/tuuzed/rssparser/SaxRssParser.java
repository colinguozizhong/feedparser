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
package io.github.tuuzed.rssparser;

import io.github.tuuzed.rssparser.callback.RssParserCallback;
import io.github.tuuzed.rssparser.util.DateUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;


class SaxRssParser extends BaseRssParser {
    private SAXParser mSaxParser;

    SaxRssParser(SAXParser saxParser) {
        this.mSaxParser = saxParser;
    }

    @Override
    public void parse(Reader reader, RssParserCallback callback) {
        try {
            mSaxParser.parse(new InputSource(reader), new RssHandler(this, callback));
        } catch (SAXException | IOException e) {
            callback.error(e);
        }
    }

    @Override
    public void parse(InputStream is, String charSet, RssParserCallback callback) {
        try {
            mSaxParser.parse(is, new RssHandler(this, callback));
        } catch (SAXException | IOException e) {
            callback.error(e);
        }
    }

    private static class RssHandler extends DefaultHandler {
        private RssParserCallback callback;
        private SaxRssParser mRssParser;
        private String currentTag = null;
        private Map<String, String> attrs;

        RssHandler(SaxRssParser rssParser, RssParserCallback callback) {
            this.mRssParser = rssParser;
            this.callback = callback;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            attrs = new HashMap<>();
            for (int i = 0; i < attributes.getLength(); i++) {
                attrs.put(attributes.getLocalName(i), attributes.getValue(i));
            }
            currentTag = qName;
            mRssParser.startTag(callback, qName);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            currentTag = null;
            mRssParser.endTag(callback, qName);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            if (currentTag == null) return;
            if (mRssParser.isBeginRss) {
                if (currentTag.equals(RssNorm.RSS)) {
                    callback.rss(attrs.get(RssNorm.RSS_VERSION));
                }
            }
            if (mRssParser.isBeginChannel && !mRssParser.isBeginItem) {
                if (mRssParser.isBeginImage) {
                    image(callback, currentTag, getText(ch, start, length));
                } else if (mRssParser.isBeginSkipDays) {
                    if (mRssParser.temp_list != null && currentTag.equals(RssNorm.SKIP_DAYS_DAY)) {
                        mRssParser.temp_list.add(getText(ch, start, length));
                    }
                } else if (mRssParser.isBeginSkipHours) {
                    if (mRssParser.temp_list != null && currentTag.equals(RssNorm.SKIP_HOURS_HOUR)) {
                        mRssParser.temp_list.add(getText(ch, start, length));
                    }
                } else if (mRssParser.isBeginTextInput) {
                    textInput(callback, currentTag, getText(ch, start, length));
                } else {
                    channel(callback, currentTag, getText(ch, start, length), attrs);
                }
            } else if (mRssParser.isBeginItem) {
                item(callback, currentTag, getText(ch, start, length), attrs);
            }
        }

        private void channel(RssParserCallback callback, String currentTag, String text, Map<String, String> attrs) {
            switch (currentTag) {
                case RssNorm.TITLE:
                    callback.title(text);
                    break;
                case RssNorm.LINK:
                    callback.link(text);
                    break;
                case RssNorm.DESCRIPTION:
                    callback.description(text);
                    break;
                case RssNorm.CATEGORY:
                    callback.category(text, attrs.get(RssNorm.CATEGORY_DOMAIN));
                    break;
                case RssNorm.CLOUD:
                    callback.cloud(
                            attrs.get(RssNorm.CLOUD_DOMAIN),
                            attrs.get(RssNorm.CLOUD_PORT),
                            attrs.get(RssNorm.CLOUD_PATH),
                            attrs.get(RssNorm.CLOUD_REGISTER_PROCEDURE),
                            attrs.get(RssNorm.CLOUD_PROTOCOL));
                    break;
                case RssNorm.COPYRIGHT:
                    callback.copyright(text);
                    break;
                case RssNorm.DOCS:
                    callback.docs(text);
                    break;
                case RssNorm.GENERATOR:
                    callback.generator(text);
                    break;
                case RssNorm.LANGUAGE:
                    callback.language(text);
                    break;
                case RssNorm.LAST_BUILD_DATE:
                    callback.lastBuildDate(DateUtils.parse(text));
                    break;
                case RssNorm.MANAGING_EDITOR:
                    callback.managingEditor(text);
                    break;
                case RssNorm.PUB_DATE:
                    callback.pubDate(DateUtils.parse(text));
                    break;
                case RssNorm.RATING:
                    callback.rating(text);
                    break;
                case RssNorm.TTL:
                    callback.ttl(text);
                    break;
                case RssNorm.WEB_MASTER:
                    callback.webMaster(text);
                    break;
            }
        }

        private void image(RssParserCallback callback, String currentTag, String text) {
            switch (currentTag) {
                case RssNorm.IMAGE_TITLE:
                    callback.imageTitle(text);
                    break;
                case RssNorm.IMAGE_HEIGHT:
                    callback.imageHeight(text);
                    break;
                case RssNorm.IMAGE_WIDTH:
                    callback.imageWidth(text);
                    break;
                case RssNorm.IMAGE_LINK:
                    callback.imageLink(text);
                    break;
                case RssNorm.IMAGE_DESCRIPTION:
                    callback.imageDescription(text);
                    break;
                case RssNorm.IMAGE_URL:
                    callback.imageUrl(text);
                    break;
            }
        }

        private void textInput(RssParserCallback callback, String currentTag, String text) {
            switch (currentTag) {
                case RssNorm.TEXT_INPUT_TITLE:
                    callback.textInputTitle(text);
                    break;
                case RssNorm.TEXT_INPUT_LINK:
                    callback.textInputLink(text);
                    break;
                case RssNorm.TEXT_INPUT_DESCRIPTION:
                    callback.textInputDescription(text);
                    break;
                case RssNorm.TEXT_INPUT_NAME:
                    callback.textInputName(text);
                    break;
            }
        }

        private void item(RssParserCallback callback, String currentTag, String text, Map<String, String> attrs) {
            switch (currentTag) {
                case RssNorm.ITEM_TITLE:
                    callback.itemTitle(text);
                    break;
                case RssNorm.ITEM_LINK:
                    callback.itemLink(text);
                    break;
                case RssNorm.ITEM_AUTHOR:
                    callback.itemAuthor(text);
                    break;
                case RssNorm.ITEM_CATEGORY:
                    callback.itemCategory(text, attrs.get(RssNorm.ITEM_CATEGORY_DOMAIN));
                    break;
                case RssNorm.ITEM_PUB_DATE:
                    callback.itemPubDate(DateUtils.parse(text));
                    break;
                case RssNorm.ITEM_COMMENTS:
                    callback.itemCategory(text, attrs.get(RssNorm.ITEM_CATEGORY_DOMAIN));
                    break;
                case RssNorm.ITEM_DESCRIPTION:
                    callback.itemDescription(text);
                    break;
                case RssNorm.ITEM_ENCLOSURE:
                    callback.itemEnclosure(
                            attrs.get(RssNorm.ITEM_ENCLOSURE_LENGTH),
                            attrs.get(RssNorm.ITEM_ENCLOSURE_TYPE),
                            attrs.get(RssNorm.ITEM_ENCLOSURE_URL));
                    break;
                case RssNorm.ITEM_GUID:
                    callback.itemGuid(text, Boolean.parseBoolean(attrs.get(RssNorm.ITEM_GUID_IS_PERMA_LINK)));
                    break;
                case RssNorm.ITEM_SOURCE:
                    callback.itemSource(text, attrs.get(RssNorm.ITEM_SOURCE_URL));
                    break;
            }
        }

        private String getText(char[] ch, int start, int length) {
            return new String(ch, start, length).trim();
        }
    }


}
