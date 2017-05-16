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

import com.github.tuuzed.feedparser.callback.FeedCallback;
import com.github.tuuzed.feedparser.internal.AbstractParser;
import com.github.tuuzed.feedparser.internal.IBuilder;
import com.github.tuuzed.feedparser.internal.Logger;
import com.github.tuuzed.feedparser.util.CharSetUtils;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FeedParser {
    private static final Logger sLogger = Logger.getLogger(FeedParser.class);
    private XmlPullParserFactory mXmlPullParserFactory;
    private OkHttpClient mHttpClient;
    private boolean mDebug;
    private int mTimeout;


    private FeedParser(Builder builder) {
        mDebug = builder.debug;
        sLogger.setDebug(mDebug);
        mHttpClient = builder.httpClient;
        mTimeout = builder.timeout;
        try {
            mXmlPullParserFactory = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public void parse(String url, FeedCallback callback) {
        parse(url, "utf-8", callback);
    }

    public void parse(String url, String defCharSet, FeedCallback callback) {
        if (mHttpClient != null) {
            Request request = new Request.Builder().url(url).get().build();
            Call call = mHttpClient.newCall(request);
            Response response = null;
            try {
                response = call.execute();
                parse(response.body().charStream(), callback);
            } catch (IOException e) {
                callback.error(e);
            } finally {
                safeClose(response);
                call.cancel();
            }
        } else {
            String[] charSet = new String[1];
            InputStream inputStream = null;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setReadTimeout(mTimeout);
                connection.setConnectTimeout(mTimeout);
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

    public void parse(InputStream is, String charSet, FeedCallback callback) {
        try {
            parse(new InputStreamReader(is, charSet), callback);
        } catch (UnsupportedEncodingException e) {
            callback.error(e);
        }
    }

    public void parse(Reader reader, final FeedCallback callback) {
        try {
            XmlPullParser xmlPullParser = mXmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(reader);
            XmlParser.parse(xmlPullParser, new XmlParser.Callback() {
                AbstractParser parser = null;

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

                public void endTag(String tagName) {
                    if (parser != null) {
                        parser.endTag(tagName, callback);
                    }
                }

                public void error(Throwable throwable) {
                    callback.lethalError(throwable);
                }
            });
        } catch (XmlPullParserException e) {
            callback.lethalError(e);
        }
    }

    private void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static class Builder implements IBuilder<FeedParser> {

        private boolean debug;
        private OkHttpClient httpClient;
        private int timeout;

        public Builder setDebug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public Builder setHttpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder setTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public FeedParser build() {
            if (timeout == 0) {
                timeout = 5000;
            }
            return new FeedParser(this);
        }
    }


}
