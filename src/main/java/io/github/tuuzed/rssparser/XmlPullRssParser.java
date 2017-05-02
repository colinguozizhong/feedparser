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

import io.github.tuuzed.rssparser.callback.RssCallback;
import io.github.tuuzed.rssparser.util.CharSetUtils;
import io.github.tuuzed.rssparser.util.DateUtils;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class XmlPullRssParser implements RssParser {

    private boolean isBeginRss = false;
    private boolean isBeginChannel = false;
    private boolean isBeginImage = false;
    private boolean isBeginTextInput = false;
    private boolean isBeginSkipDays = false;
    private boolean isBeginSkipHours = false;
    private boolean isBeginItem = false;
    private List<String> temp_list = null;
    private XmlPullParser mXmlPullParser;
    private OkHttpClient mHttpClient;
    private String mDefCharSet;

    XmlPullRssParser(RssParserBuilder builder) {
        mHttpClient = builder.okHttpClient;
        mDefCharSet = builder.defCharSet;
        mXmlPullParser = builder.xmlPullParser;
    }

    @Override
    public void parse(String url, RssCallback callback) {
        parse(url, mDefCharSet, callback);
    }

    @Override
    public void parse(String url, String defCharSet, RssCallback callback) {
        if (mHttpClient != null) {
            Request request = new Request.Builder().url(url).get().build();
            Call call = mHttpClient.newCall(request);
            try {
                Response response = call.execute();
                parse(response.body().charStream(), callback);
            } catch (IOException e) {
                callback.error(e);
            }
        } else {
            String[] charSet = new String[1];
            InputStream inputStream = null;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setReadTimeout(5000);
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
            } finally {
                safeClose(inputStream);
            }
        }
    }

    @Override
    public void parse(Reader reader, RssCallback callback) {
        try {
            mXmlPullParser.setInput(reader);
            parse(mXmlPullParser, callback);
        } catch (XmlPullParserException e) {
            callback.error(e);
        } finally {
            safeClose(reader);
        }
    }

    @Override
    public void parse(InputStream is, String charSet, RssCallback callback) {
        try {
            mXmlPullParser.setInput(is, charSet);
            parse(mXmlPullParser, callback);
        } catch (XmlPullParserException e) {
            callback.error(e);
        } finally {
            safeClose(is);
        }
    }

    @Override
    public void addDateFormat(DateFormat format) {
        DateUtils.addDateFormat(format);
    }

    // 解析 Rss
    private void parse(XmlPullParser parser, RssCallback callback) {
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
        } catch (XmlPullParserException e) {
            callback.error(e);
        } catch (IOException e) {
            callback.error(e);
        }
    }


    // 开始标签时
    private void startTag(RssCallback callback) {
        String tagName = mXmlPullParser.getName();
        if (RssConst.RSS.equals(tagName)) {
            callback.begin();
            isBeginRss = true;
            Map<String, String> attrs = new HashMap<String, String>();
            for (int i = 0; i < mXmlPullParser.getAttributeCount(); i++) {
                attrs.put(mXmlPullParser.getAttributeName(i), mXmlPullParser.getAttributeValue(i).trim());
            }
            rss(callback, attrs);
        } else if (RssConst.CHANNEL.equals(tagName)) {
            isBeginChannel = true;
        } else if (RssConst.IMAGE.equals(tagName)) {
            callback.imageBegin();
            isBeginImage = true;
        } else if (RssConst.SKIP_DAYS.equals(tagName)) {
            isBeginSkipDays = true;
            temp_list = new ArrayList<String>();
        } else if (RssConst.SKIP_HOURS.equals(tagName)) {
            isBeginSkipHours = true;
            temp_list = new ArrayList<String>();
        } else if (RssConst.TEXT_INPUT.equals(tagName)) {
            callback.textInputBegin();
            isBeginTextInput = true;
        } else if (RssConst.ITEM.equals(tagName)) {
            callback.itemBegin();
            isBeginItem = true;
        }
        if (isBeginChannel) {
            if (isBeginItem) {
                // 开始解析item
                Map<String, String> attrs = new HashMap<String, String>();
                for (int i = 0; i < mXmlPullParser.getAttributeCount(); i++) {
                    attrs.put(mXmlPullParser.getAttributeName(i), mXmlPullParser.getAttributeValue(i).trim());
                }
                item(callback, tagName, attrs);
            } else if (isBeginImage) {
                // 开始解析image
                image(callback, tagName);
            } else if (isBeginSkipDays) {
                if (temp_list != null && tagName.equals(RssConst.SKIP_DAYS_DAY)) {
                    temp_list.add(nextText());
                }
            } else if (isBeginSkipHours) {
                if (temp_list != null && tagName.equals(RssConst.SKIP_HOURS_HOUR)) {
                    temp_list.add(nextText());
                }
            } else if (isBeginTextInput) {
                textInput(callback, tagName);
            } else {
                Map<String, String> attrs = new HashMap<String, String>();
                for (int i = 0; i < mXmlPullParser.getAttributeCount(); i++) {
                    attrs.put(mXmlPullParser.getAttributeName(i), mXmlPullParser.getAttributeValue(i).trim());
                }
                channel(callback, tagName, attrs);
            }
        }
    }

    // 结束标签时
    private void endTag(RssCallback callback) {
        String tagName = mXmlPullParser.getName();
        if (RssConst.RSS.equals(tagName)) {
            isBeginRss = false;
            callback.end();
        } else if (RssConst.CHANNEL.equals(tagName)) {
            isBeginChannel = false;
        } else if (RssConst.IMAGE.equals(tagName)) {
            callback.imageEnd();
            isBeginImage = false;
        } else if (RssConst.SKIP_DAYS.equals(tagName)) {
            isBeginSkipDays = false;
            callback.skipDays(temp_list);
        } else if (RssConst.SKIP_HOURS.equals(tagName)) {
            isBeginSkipHours = false;
            callback.skipHours(temp_list);
        } else if (RssConst.TEXT_INPUT.equals(tagName)) {
            isBeginTextInput = false;
            callback.textInputEnd();
        } else if (RssConst.ITEM.equals(tagName)) {
            isBeginItem = false;
            callback.itemEnd();
        }
    }


    private void rss(RssCallback callback, Map<String, String> attrs) {
        callback.rss(attrs.get(RssConst.RSS_VERSION));
    }

    private void channel(RssCallback callback, String tagName, Map<String, String> attrs) {
        if (RssConst.TITLE.equals(tagName)) {
            callback.title(nextText());
        } else if (RssConst.LINK.equals(tagName)) {
            callback.link(nextText());
        } else if (RssConst.DESCRIPTION.equals(tagName)) {
            callback.description(nextText());
        } else if (RssConst.CATEGORY.equals(tagName)) {
            callback.category(nextText(), attrs.get(RssConst.CATEGORY_DOMAIN));
        } else if (RssConst.CLOUD.equals(tagName)) {
            callback.cloud(
                    attrs.get(RssConst.CLOUD_DOMAIN),
                    attrs.get(RssConst.CLOUD_PORT),
                    attrs.get(RssConst.CLOUD_PATH),
                    attrs.get(RssConst.CLOUD_REGISTER_PROCEDURE),
                    attrs.get(RssConst.CLOUD_PROTOCOL));
        } else if (RssConst.COPYRIGHT.equals(tagName)) {
            callback.copyright(nextText());
        } else if (RssConst.DOCS.equals(tagName)) {
            callback.docs(nextText());
        } else if (RssConst.GENERATOR.equals(tagName)) {
            callback.generator(nextText());
        } else if (RssConst.LANGUAGE.equals(tagName)) {
            callback.language(nextText());
        } else if (RssConst.LAST_BUILD_DATE.equals(tagName)) {
            String text = nextText();
            callback.lastBuildDate(DateUtils.parse(text), text);
        } else if (RssConst.MANAGING_EDITOR.equals(tagName)) {
            callback.managingEditor(nextText());
        } else if (RssConst.PUB_DATE.equals(tagName)) {
            String text = nextText();
            callback.pubDate(DateUtils.parse(text), text);
        } else if (RssConst.RATING.equals(tagName)) {
            callback.rating(nextText());
        } else if (RssConst.TTL.equals(tagName)) {
            callback.ttl(nextText());
        } else if (RssConst.WEB_MASTER.equals(tagName)) {
            callback.webMaster(nextText());
        }
    }

    private void image(RssCallback callback, String tagName) {
        if (RssConst.IMAGE_TITLE.equals(tagName)) {
            callback.imageTitle(nextText());
        } else if (RssConst.IMAGE_HEIGHT.equals(tagName)) {
            callback.imageHeight(nextText());
        } else if (RssConst.IMAGE_WIDTH.equals(tagName)) {
            callback.imageWidth(nextText());
        } else if (RssConst.IMAGE_LINK.equals(tagName)) {
            callback.imageLink(nextText());
        } else if (RssConst.IMAGE_DESCRIPTION.equals(tagName)) {
            callback.imageDescription(nextText());
        } else if (RssConst.IMAGE_URL.equals(tagName)) {
            callback.imageUrl(nextText());
        }
    }

    private void textInput(RssCallback callback, String tagName) {
        if (RssConst.TEXT_INPUT_TITLE.equals(tagName)) {
            callback.textInputTitle(nextText());
        } else if (RssConst.TEXT_INPUT_LINK.equals(tagName)) {
            callback.textInputLink(nextText());
        } else if (RssConst.TEXT_INPUT_LINK.equals(tagName)) {
            callback.textInputLink(nextText());
        } else if (RssConst.TEXT_INPUT_DESCRIPTION.equals(tagName)) {
            callback.textInputDescription(nextText());
        } else if (RssConst.TEXT_INPUT_NAME.equals(tagName)) {
            callback.textInputName(nextText());
        }
    }

    private void item(RssCallback callback, String tagName, Map<String, String> attrs) {
        if (RssConst.IMAGE_TITLE.equals(tagName)) {
            callback.itemTitle(nextText());
        } else if (RssConst.ITEM_LINK.equals(tagName)) {
            callback.itemLink(nextText());
        } else if (RssConst.ITEM_AUTHOR.equals(tagName)) {
            callback.itemAuthor(nextText());
        } else if (RssConst.ITEM_CATEGORY.equals(tagName)) {
            callback.itemCategory(nextText(), attrs.get(RssConst.ITEM_CATEGORY_DOMAIN));
        } else if (RssConst.ITEM_PUB_DATE.equals(tagName)) {
            String text = nextText();
            callback.itemPubDate(DateUtils.parse(text), text);
        } else if (RssConst.ITEM_COMMENTS.equals(tagName)) {
            callback.itemComments(nextText());
        } else if (RssConst.ITEM_DESCRIPTION.equals(tagName)) {
            callback.itemDescription(nextText());
        } else if (RssConst.ITEM_ENCLOSURE.equals(tagName)) {
            callback.itemEnclosure(
                    attrs.get(RssConst.ITEM_ENCLOSURE_LENGTH),
                    attrs.get(RssConst.ITEM_ENCLOSURE_TYPE),
                    attrs.get(RssConst.ITEM_ENCLOSURE_URL));
        } else if (RssConst.ITEM_GUID.equals(tagName)) {
            callback.itemGuid(nextText(), Boolean.parseBoolean(attrs.get(RssConst.ITEM_GUID_IS_PERMA_LINK)));
        } else if (RssConst.ITEM_SOURCE.equals(tagName)) {
            callback.itemSource(nextText(), attrs.get(RssConst.ITEM_SOURCE_URL));
        }
    }


    // 取得文本
    private String nextText() {
        try {
            return mXmlPullParser.nextText().trim();
        } catch (XmlPullParserException e) {
            // pass
        } catch (IOException e) {
            // pass
        }
        return null;
    }

    // 安全关闭可关闭的对象
    private void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
