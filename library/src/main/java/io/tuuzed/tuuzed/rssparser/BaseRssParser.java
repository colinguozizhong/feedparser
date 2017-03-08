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

import java.util.ArrayList;
import java.util.List;

import io.tuuzed.tuuzed.rssparser.callback.RssParserCallback;

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

}
