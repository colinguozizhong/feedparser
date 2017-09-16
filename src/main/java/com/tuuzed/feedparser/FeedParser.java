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

import com.tuuzed.feedparser.impl.AtomParser;
import com.tuuzed.feedparser.impl.RssParser;
import com.tuuzed.feedparser.util.CloseableUtils;
import com.tuuzed.feedparser.util.DateParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeedParser {
    private static Pattern CHARSET_PATTERN = Pattern.compile(
            "(encoding|charset)=.*(gb2312|utf-8|gbk).*",
            Pattern.CASE_INSENSITIVE);

    public static void appendDateFormat(@NotNull DateFormat format) {
        DateParser.appendDateFormat(format);
    }

    public static void parse(@NotNull String url,
                             @NotNull FeedCallback callback) {
        parse(url, "utf-8", callback);
    }

    public static void parse(@NotNull String url,
                             @NotNull String defCharset,
                             @NotNull FeedCallback callback) {
        InputStream input = null;
        Reader reader = null;
        String charset = null;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            int responseCode = connection.getResponseCode();
            // 重定向
            if (responseCode == HttpURLConnection.HTTP_MOVED_PERM
                    || responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                String redirectUrl = connection.getHeaderField("location");
                if (redirectUrl != null) {
                    parse(redirectUrl, defCharset, callback);
                    return;
                }
            }
            // 非重定向
            final String contentType = connection.getContentType();
            if (contentType != null) {
                Matcher matcher = CHARSET_PATTERN.matcher(contentType);
                if (matcher.find()) {
                    charset = matcher.group(2);
                }
            }
            input = connection.getInputStream();
            // 没有匹配到编码
            if (charset == null) parse(input, defCharset, callback);
                // 匹配到编码
            else {
                reader = new InputStreamReader(input, charset);
                parse(reader, callback);
            }
        } catch (IOException e) {
            callback.fatalError(e);
        } finally {
            CloseableUtils.safeClose(reader);
            CloseableUtils.safeClose(input);
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static void parse(@NotNull InputStream input,
                             @NotNull FeedCallback callback) {
        parse(input, "utf-8", callback);
    }

    public static void parse(@NotNull InputStream input,
                             @NotNull String defCharset,
                             @NotNull FeedCallback callback) {
        final List<Byte> buffers = new ArrayList<>(2048);
        Reader reader = null;
        final byte[] buffer = new byte[2048];
        String charset = null;
        try {
            int len;
            while ((len = input.read(buffer)) != -1) {
                for (int i = 0; i < len; i++) {
                    buffers.add(buffer[i]);
                }
            }
            byte[] bytes = new byte[buffers.size()];
            for (int i = 0; i < buffers.size(); i++) {
                bytes[i] = buffers.get(i);
            }
            String content = new String(bytes);
            Matcher matcher = CHARSET_PATTERN.matcher(content);
            if (matcher.find()) {
                charset = matcher.group(2);
            }
            if (charset == null) {
                charset = defCharset;
            }
            reader = new InputStreamReader(new ByteArrayInputStream(bytes), charset);
            parse(reader, callback);
        } catch (IOException e) {
            callback.fatalError(e);
        } finally {
            buffers.clear();
            CloseableUtils.safeClose(reader);
        }
    }

    public static void parse(@NotNull Reader reader,
                             @NotNull final FeedCallback callback) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(reader);
            FastXmlPullParser.parse(xmlPullParser, new FastXmlPullParser.Callback() {
                Parser parser = null;

                @Override
                public void startTag(@NotNull XmlPullParser xmlPullParser, @NotNull String tag) {
                    if (parser == null) {
                        parser = getParser(tag, callback);
                    }
                    if (parser != null) {
                        parser.startTag(tag, xmlPullParser);
                    }
                }

                @Override
                public void endTag(@NotNull String tag) {
                    if (parser != null) {
                        parser.endTag(tag);
                    }
                }

                @Override
                public void error(@NotNull Throwable throwable) {
                    callback.error(throwable);
                }
            });
        } catch (XmlPullParserException e) {
            callback.fatalError(e);
        }
    }

    @Nullable
    private static Parser getParser(@NotNull String tag, @NotNull FeedCallback callback) {
        if ("rss".equals(tag)) {
            return new RssParser(callback);
        } else if ("feed".equals(tag)) {
            return new AtomParser(callback);
        }
        return null;
    }


}
