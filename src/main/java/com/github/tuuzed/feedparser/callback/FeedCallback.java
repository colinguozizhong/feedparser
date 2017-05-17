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
package com.github.tuuzed.feedparser.callback;

import java.util.Date;
import java.util.List;

public abstract class FeedCallback implements Callback {
    public ImageCallback imageCallback;
    public EntryCallback entryCallback;
    public TextInputCallback textInputCallback;

    /**
     * /rss
     *
     * @param type
     * @param version
     */
    public abstract void feed(String type, String version);

    /**
     * /rss/channel/title
     *
     * @param type
     * @param language
     * @param value
     */
    public abstract void title(String type, String language, String value);

    /**
     * /rss/channel/description
     *
     * @param type
     * @param language
     * @param value
     */
    public abstract void subtitle(String type, String language, String value);

    /**
     * /rss/channel/link
     *
     * @param value
     */
    public abstract void link(String value);

    /**
     * /rss/channel/language
     *
     * @param value
     */
    public abstract void language(String value);

    /**
     * /rss/channel/copyright
     *
     * @param type
     * @param language
     * @param value
     */
    public abstract void rights(String type, String language, String value);

    /**
     * /rss/channel/managingEditor
     *
     * @param value
     */
    public abstract void authors(String value);

    /**
     * /rss/channel/webMaster
     *
     * @param value
     */
    public abstract void webMaster(String value);

    /**
     * /rss/channel/pubDate
     *
     * @param date
     * @param strDate
     */
    public abstract void published(Date date, String strDate);

    /**
     * /rss/channel/pubDate/lastBuildDate
     *
     * @param date
     * @param strDate
     */
    public abstract void updated(Date date, String strDate);

    /**
     * /rss/channel/category
     *
     * @param value
     * @param domain
     */
    public abstract void tags(String value, String domain);

    /**
     * /rss/channel/generator
     *
     * @param value
     */
    public abstract void generator(String value);

    /**
     * /rss/channel/docs
     *
     * @param value
     */
    public abstract void docs(String value);

    /**
     * /rss/channel/cloud
     *
     * @param domain
     * @param port
     * @param path
     * @param registerProcedure
     * @param protocol
     */
    public abstract void cloud(String domain, String port, String path,
                               String registerProcedure, String protocol);

    /**
     * /rss/channel/ttl
     *
     * @param ttl
     */
    public abstract void ttl(String ttl);

    public abstract void links(String rel, String type, String href);


    /**
     * /rss/channel/skipDays
     *
     * @param skipDays
     */
    public abstract void skipDays(List<String> skipDays);

    /**
     * /rss/channel/skipHours
     *
     * @param skipHours
     */
    public abstract void skipHours(List<String> skipHours);


    /**
     * /rss/channel/rating
     *
     * @param rating
     */
    public abstract void rating(String rating);


    /**
     * 错误
     *
     * @param throwable
     */
    public abstract void error(Throwable throwable);

    /**
     * 致命的错误
     *
     * @param throwable
     */
    public abstract void lethalError(Throwable throwable);
}
