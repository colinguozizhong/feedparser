/* MIT License
 *
 * Copyright (c) 2016 TuuZed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *         OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.tuuzed.tuuzed.rssparser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import io.tuuzed.tuuzed.rssparser.callback.RssParserCallback;
import io.tuuzed.tuuzed.rssparser.util.CharSetUtils;
import io.tuuzed.tuuzed.rssparser.util.DateUtils;

public class XmlPullRssParser extends BaseRssParser {
    private XmlPullParser mXmlPullParser;

    public XmlPullRssParser(XmlPullParser xmlPullParser) {
        this.mXmlPullParser = xmlPullParser;
    }

    @Override
    public void parse(String url, RssParserCallback callback) {
        parse(url, "UTF-8", callback);
    }

    @Override
    public void parse(String url, String defCharSet, RssParserCallback callback) {
        String[] charSet = new String[1];
        InputStream inputStream = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            charSet[0] = CharSetUtils.getCharSet(connection.getContentType());
            if (charSet[0] == null) {
                inputStream = CharSetUtils.getCharSet(connection.getInputStream(), charSet);
            }
            if (charSet[0] == null) {
                charSet[0] = defCharSet;
            }
            if (inputStream == null) {
                inputStream = connection.getInputStream();
            }
            parse(inputStream, charSet[0], callback);
        } catch (IOException e) {
            callback.error(e);
        }
    }

    @Override
    public void parse(Reader reader, RssParserCallback callback) {
        try {
            mXmlPullParser.setInput(reader);
            parse(mXmlPullParser, callback);
        } catch (XmlPullParserException e) {
            callback.error(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void parse(InputStream is, String charSet, RssParserCallback callback) {
        try {
            mXmlPullParser.setInput(is, charSet);
            parse(mXmlPullParser, callback);
        } catch (XmlPullParserException e) {
            callback.error(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 解析Rss
    private void parse(XmlPullParser parser, RssParserCallback callback) {
        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        startTag(callback);
                        break;
                    case XmlPullParser.END_TAG:
                        endTag(callback);
                        break;
                }
                eventType = parser.next();
            }
            callback.end();
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
            callback.error(e);
        }
    }

    // 开始标签
    private void startTag(RssParserCallback callback) {
        String currentTag = mXmlPullParser.getName();
        startTag(callback, currentTag);
        if (RssNorm.RSS.equals(currentTag)) {
            Map<String, String> attrs = new HashMap<>();
            for (int i = 0; i < mXmlPullParser.getAttributeCount(); i++) {
                attrs.put(mXmlPullParser.getAttributeName(i), mXmlPullParser.getAttributeValue(i).trim());
            }
            rss(callback, attrs);
        }
        if (isBeginChannel) {
            if (isBeginItem) {
                // 开始解析item
                Map<String, String> attrs = new HashMap<>();
                for (int i = 0; i < mXmlPullParser.getAttributeCount(); i++) {
                    attrs.put(mXmlPullParser.getAttributeName(i), mXmlPullParser.getAttributeValue(i).trim());
                }
                item(callback, currentTag, attrs);
            } else if (isBeginImage) {
                // 开始解析image
                image(callback, currentTag);
            } else if (isBeginSkipDays) {
                if (temp_list != null && currentTag.equals(RssNorm.SKIP_DAYS_DAY)) {
                    temp_list.add(nextText());
                }
            } else if (isBeginSkipHours) {
                if (temp_list != null && currentTag.equals(RssNorm.SKIP_HOURS_HOUR)) {
                    temp_list.add(nextText());
                }
            } else if (isBeginTextInput) {
                textInput(callback, currentTag);
            } else {
                Map<String, String> attrs = new HashMap<>();
                for (int i = 0; i < mXmlPullParser.getAttributeCount(); i++) {
                    attrs.put(mXmlPullParser.getAttributeName(i), mXmlPullParser.getAttributeValue(i).trim());
                }
                channel(callback, currentTag, attrs);
            }
        }
    }


    // 结束标签
    private void endTag(RssParserCallback callback) {
        endTag(callback, mXmlPullParser.getName());
    }

    private void rss(RssParserCallback callback, Map<String, String> attrs) {
        callback.rss(attrs.get(RssNorm.RSS_VERSION));
    }

    private void item(RssParserCallback callback, String currentTag, Map<String, String> attrs) {
        switch (currentTag) {
            case RssNorm.ITEM_TITLE:
                callback.itemTitle(nextText());
                break;
            case RssNorm.ITEM_LINK:
                callback.itemLink(nextText());
                break;
            case RssNorm.ITEM_AUTHOR:
                callback.itemAuthor(nextText());
                break;
            case RssNorm.ITEM_CATEGORY:
                callback.itemCategory(nextText(), attrs.get(RssNorm.ITEM_CATEGORY_DOMAIN));
                break;
            case RssNorm.ITEM_PUB_DATE:
                callback.itemPubDate(DateUtils.parseDate(nextText()));
                break;
            case RssNorm.ITEM_COMMENTS:
                callback.itemCategory(nextText(), attrs.get(RssNorm.ITEM_CATEGORY_DOMAIN));
                break;
            case RssNorm.ITEM_DESCRIPTION:
                callback.itemDescription(nextText());
                break;
            case RssNorm.ITEM_ENCLOSURE:
                callback.itemEnclosure(
                        attrs.get(RssNorm.ITEM_ENCLOSURE_LENGTH),
                        attrs.get(RssNorm.ITEM_ENCLOSURE_TYPE),
                        attrs.get(RssNorm.ITEM_ENCLOSURE_URL));
                break;
            case RssNorm.ITEM_GUID:
                callback.itemGuid(nextText(), Boolean.parseBoolean(attrs.get(RssNorm.ITEM_GUID_IS_PERMA_LINK)));
                break;
            case RssNorm.ITEM_SOURCE:
                callback.itemSource(nextText(), attrs.get(RssNorm.ITEM_SOURCE_URL));
                break;
        }
    }

    private void image(RssParserCallback callback, String currentTag) {
        switch (currentTag) {
            case RssNorm.IMAGE_TITLE:
                callback.imageTitle(nextText());
                break;
            case RssNorm.IMAGE_HEIGHT:
                callback.imageHeight(nextText());
                break;
            case RssNorm.IMAGE_WIDTH:
                callback.imageWidth(nextText());
                break;
            case RssNorm.IMAGE_LINK:
                callback.imageLink(nextText());
                break;
            case RssNorm.IMAGE_DESCRIPTION:
                callback.imageDescription(nextText());
                break;
            case RssNorm.IMAGE_URL:
                callback.imageUrl(nextText());
                break;
        }
    }

    private void textInput(RssParserCallback callback, String currentTag) {
        switch (currentTag) {
            case RssNorm.TEXT_INPUT_TITLE:
                callback.textInputTitle(nextText());
                break;
            case RssNorm.TEXT_INPUT_LINK:
                callback.textInputLink(nextText());
                break;
            case RssNorm.TEXT_INPUT_DESCRIPTION:
                callback.textInputDescription(nextText());
                break;
            case RssNorm.TEXT_INPUT_NAME:
                callback.textInputName(nextText());
                break;
        }
    }

    private void channel(RssParserCallback callback, String currentTag, Map<String, String> attrs) {
        switch (currentTag) {
            case RssNorm.TITLE:
                callback.title(nextText());
                break;
            case RssNorm.LINK:
                callback.link(nextText());
                break;
            case RssNorm.DESCRIPTION:
                callback.description(nextText());
                break;
            case RssNorm.CATEGORY:
                callback.category(nextText(), attrs.get(RssNorm.CATEGORY_DOMAIN));
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
                callback.copyright(nextText());
                break;
            case RssNorm.DOCS:
                callback.docs(nextText());
                break;
            case RssNorm.GENERATOR:
                callback.generator(nextText());
                break;
            case RssNorm.LANGUAGE:
                callback.language(nextText());
                break;
            case RssNorm.LAST_BUILD_DATE:
                callback.lastBuildDate(DateUtils.parseDate(nextText()));
                break;
            case RssNorm.MANAGING_EDITOR:
                callback.managingEditor(nextText());
                break;
            case RssNorm.PUB_DATE:
                callback.pubDate(DateUtils.parseDate(nextText()));
                break;
            case RssNorm.RATING:
                callback.rating(nextText());
                break;
            case RssNorm.TTL:
                callback.ttl(nextText());
                break;
            case RssNorm.WEB_MASTER:
                callback.webMaster(nextText());
                break;
        }
    }

    // 取得内容
    private String nextText() {
        try {
            return mXmlPullParser.nextText();
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
