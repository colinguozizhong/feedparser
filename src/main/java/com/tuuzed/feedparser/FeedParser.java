package com.tuuzed.feedparser;

import com.tuuzed.feedparser.internal.AtomParser;
import com.tuuzed.feedparser.internal.GenericParser;
import com.tuuzed.feedparser.internal.RssParser;
import com.tuuzed.feedparser.util.DateParser;
import com.tuuzed.feedparser.util.FastXmlPullParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeedParser {
    private static Pattern CHARSET_PATTERN = Pattern.compile(
            "(encoding|charset)=.*(GB2312|UTF-8|GBK).*",
            Pattern.CASE_INSENSITIVE);

    public static void appendDateFormat(@NotNull DateFormat format) {
        DateParser.appendDateFormat(format);
    }

    public static void parse(@NotNull String url, @NotNull FeedCallback callback) {
        parse(url, callback, "utf-8");
    }

    public static void parse(@NotNull String url, @NotNull FeedCallback callback,
                             @NotNull String defCharset) {
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
                    parse(redirectUrl, callback, defCharset);
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
            if (charset == null) parse(input, callback, defCharset);
                // 匹配到编码
            else {
                reader = new InputStreamReader(input, charset);
                parse(reader, callback);
            }
        } catch (IOException e) {
            callback.fatalError(e);
        } finally {
            safeClose(reader);
            safeClose(input);
            if (connection != null) {
                connection.disconnect();
            }

        }
    }

    public static void parse(@NotNull InputStream input, @NotNull FeedCallback callback) {
        parse(input, callback, "utf-8");
    }

    public static void parse(@NotNull InputStream input, @NotNull FeedCallback callback,
                             @NotNull String defCharset) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        Reader reader = null;
        final byte[] buffer = new byte[2048];
        String charset = null;
        try {
            int len;
            while ((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            output.flush();
            byte[] bytes = output.toByteArray();
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
            safeClose(output);
            safeClose(reader);
        }
    }

    public static void parse(@NotNull Reader reader, @NotNull final FeedCallback callback) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(reader);
            FastXmlPullParser.parse(xmlPullParser, new FastXmlPullParser.Callback() {
                GenericParser parser = null;

                @Override
                public void startTag(@NotNull XmlPullParser xmlPullParser, @NotNull String tag) {
                    if (parser == null) {
                        if ("rss".equals(tag)) {
                            parser = new RssParser(callback);
                        } else if ("feed".equals(tag)) {
                            parser = new AtomParser(callback);
                        }
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


    private static void safeClose(@Nullable Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // pass
            }
        }
    }

}
