package com.tuuzed.feedparser;

import com.tuuzed.feedparser.util.CharSetUtil;
import com.tuuzed.feedparser.util.CloseableUtil;
import com.tuuzed.feedparser.xml.XmlParser;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FeedParser {
    // 日志
    private static final Logger logger = LoggerFactory.getLogger(FeedParser.class);

    private static OkHttpClient httpClient;

    public static void setHttpClient(OkHttpClient httpClient) {
        FeedParser.httpClient = httpClient;
    }

    public static void parse(String url, FeedCallback callback) {
        parse(url, "utf-8", callback);
    }

    public static void parse(String url, String defCharSet, FeedCallback callback) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            if (httpClient != null) {
                // 使用okHttp请求
                Request request = new Request.Builder().url(url).get().build();
                Call call = httpClient.newCall(request);
                Response response = call.execute();
                ResponseBody body = response.body();
                if (body == null) {
                    throw new IOException("连接失败");
                }
                Reader reader = body.charStream();
                parse(reader, callback);
            } else {
                connection = (HttpURLConnection) new URL(url).openConnection();
                inputStream = connection.getInputStream();
                parse(inputStream, defCharSet, callback);
            }
        } catch (IOException e) {
            callback.fatalError(e);
            logger.error(e.getMessage(), e);
        } finally {
            CloseableUtil.safeClose(inputStream);
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static void parse(InputStream is, String charSet, FeedCallback callback) {
        String[] charset = new String[]{charSet};
        try {
            is = CharSetUtil.getCharSet(is, charset);
            parse(new InputStreamReader(is, charset[0]), callback);
        } catch (IOException e) {
            callback.fatalError(e);
            logger.error(e.getMessage(), e);
        } finally {
            CloseableUtil.safeClose(is);
        }
    }

    public static void parse(Reader reader, final FeedCallback callback) {
        XmlPullParserFactory xmlPullParserFactory;
        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(reader);
            XmlParser.parse(xmlPullParser, new XmlParser.Callback() {
                GenericParser parser = null;

                @Override
                public void startTag(XmlPullParser xmlPullParser, String tagName) {
                    if (parser == null && "rss".equals(tagName)) {
                        parser = new RssParser();
                    } else if (parser == null && "feed".equals(tagName)) {
                        parser = new AtomParser();
                    }
                    if (parser != null) {
                        parser.startTag(tagName, xmlPullParser, callback);
                    }
                }

                @Override
                public void endTag(String tagName) {
                    if (parser != null) {
                        parser.endTag(tagName, callback);
                    }
                }

                @Override
                public void error(Throwable throwable) {
                    callback.error(throwable);
                }
            });
        } catch (XmlPullParserException e) {
            callback.fatalError(e);
        }
    }

}
