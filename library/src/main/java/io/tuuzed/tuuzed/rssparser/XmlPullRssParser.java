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
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import io.tuuzed.tuuzed.rssparser.callback.RssParserCallback;
import io.tuuzed.tuuzed.rssparser.util.CharSetUtils;

public class XmlPullRssParser extends BaseRssParser {
    private XmlPullParser mParser;
    private static XmlPullRssParser instance;

    private XmlPullRssParser() throws XmlPullParserException {
        mParser = XmlPullParserFactory.newInstance().newPullParser();
    }

    public static XmlPullRssParser getInstance() throws XmlPullParserException {
        if (instance == null) {
            synchronized (XmlPullRssParser.class) {
                if (instance == null) {
                    instance = new XmlPullRssParser();
                }
            }
        }
        return instance;
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
        if (mParser != null) {
            try {
                mParser.setInput(reader);
                parse(mParser, callback);
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
        } else {
            throw new RuntimeException("XmlPullParser object cannot be empty !");
        }
    }

    @Override
    public void parse(InputStream is, String charSet, RssParserCallback callback) {
        if (mParser != null) {
            try {
                mParser.setInput(is, charSet);
                parse(mParser, callback);
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
        } else {
            throw new RuntimeException("XmlPullParser object cannot be empty !");
        }
    }

    // 解析Rss
    private void parse(XmlPullParser parser, RssParserCallback callback) {
        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        startTag(callback);
                        break;
                    case XmlPullParser.END_TAG:
                        endTag(callback);
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
        String name = mParser.getName();
        startTag(callback, name);
        if (RssNorm.RSS.equals(name)) {
            for (int i = 0; i < mParser.getAttributeCount(); i++) {
                if (RssNorm.RSS_VERSION.equals(mParser.getAttributeName(i))) {
                    callback.rss(mParser.getAttributeValue(i));
                    break;
                }
            }

        }
        if (isBeginChannel && !isBeginItem) {
            if (isBeginImage) {
                image(callback, name, nextText());
            } else if (isBeginSkipDays) {
                if (temp_list != null && name.equals(RssNorm.SKIP_DAYS_DAY)) {
                    temp_list.add(nextText());
                }
            } else if (isBeginSkipHours) {
                if (temp_list != null && name.equals(RssNorm.SKIP_HOURS_HOUR)) {
                    temp_list.add(nextText());
                }
            } else if (isBeginTextInput) {
                textInput(callback, name, nextText());
            } else {
                Map<String, String> attrs = new HashMap<>();
                for (int i = 0; i < mParser.getAttributeCount(); i++) {
                    attrs.put(mParser.getAttributeName(i), mParser.getAttributeValue(i).trim());
                }
                channel(callback, name, nextText(), attrs);
            }
        } else if (isBeginItem) {
            Map<String, String> attrs = new HashMap<>();
            for (int i = 0; i < mParser.getAttributeCount(); i++) {
                attrs.put(mParser.getAttributeName(i), mParser.getAttributeValue(i).trim());
            }
            item(callback, name, nextText(), attrs);
        }
    }

    // 结束标签
    private void endTag(RssParserCallback callback) {
        endTag(callback, mParser.getName());
    }

    // 取得内容
    private String nextText() {
        try {
            return mParser.nextText();
        } catch (XmlPullParserException | IOException e) {
            return null;
        }
    }
}
