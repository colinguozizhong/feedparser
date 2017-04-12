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
import io.github.tuuzed.rssparser.util.CharSetUtils;
import io.github.tuuzed.rssparser.util.DateUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TuuZed
 */
abstract class BaseRssParser implements RssParser {
    protected boolean isBeginRss = false;
    protected boolean isBeginChannel = false;
    protected boolean isBeginImage = false;
    protected boolean isBeginTextInput = false;
    protected boolean isBeginSkipDays = false;
    protected boolean isBeginSkipHours = false;
    protected boolean isBeginItem = false;
    protected List<String> temp_list = null;


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
        } finally {
            safeClose(inputStream);
        }
    }


    /**
     * 开始标签
     *
     * @param callback :回调
     * @param tagName  :标签名
     */
    protected void startTag(RssParserCallback callback, String tagName) {
        switch (tagName) {
            case RssNorm.RSS:
                callback.begin();
                isBeginRss = true;
                break;
            case RssNorm.CHANNEL:
                isBeginChannel = true;
                break;
            case RssNorm.IMAGE:
                callback.imageBegin();
                isBeginImage = true;
                break;
            case RssNorm.SKIP_DAYS:
                isBeginSkipDays = true;
                temp_list = new ArrayList<>();
                break;
            case RssNorm.SKIP_HOURS:
                isBeginSkipHours = true;
                temp_list = new ArrayList<>();
                break;
            case RssNorm.TEXT_INPUT:
                callback.textInputBegin();
                isBeginTextInput = true;
                break;
            case RssNorm.ITEM:
                callback.itemBegin();
                isBeginItem = true;
                break;
        }
    }

    /**
     * 结束标签
     *
     * @param callback :回调
     * @param tagName  :标签名
     */
    protected void endTag(RssParserCallback callback, String tagName) {
        switch (tagName) {
            case RssNorm.RSS:
                isBeginRss = false;
                callback.end();
                break;
            case RssNorm.CHANNEL:
                isBeginChannel = false;
                break;
            case RssNorm.IMAGE:
                callback.imageEnd();
                isBeginImage = false;
                break;
            case RssNorm.SKIP_DAYS:
                isBeginSkipDays = false;
                callback.skipDays(temp_list);
                break;
            case RssNorm.SKIP_HOURS:
                isBeginSkipHours = false;
                callback.skipDays(temp_list);
                break;
            case RssNorm.TEXT_INPUT:
                callback.textInputEnd();
                isBeginTextInput = false;
                break;
            case RssNorm.ITEM:
                callback.itemEnd();
                isBeginItem = false;
                break;
        }
    }

    /**
     * 添加时间格式
     *
     * @param format :时间格式
     */
    @Override
    public void addDateFormat(DateFormat format) {
        DateUtils.addDateFormat(format);
    }
    /**
     * 安全关闭可关闭的对象
     *
     * @param closeable :可关闭的对象
     */
    protected void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
