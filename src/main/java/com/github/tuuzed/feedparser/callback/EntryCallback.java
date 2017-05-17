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

/**
 * /rss/channel/item
 */
public interface EntryCallback extends Callback {

    /**
     * /rss/channel/item/title
     *
     * @param value
     */
    void title(String value);

    /**
     * @param value
     */
    void link(String value);

    /**
     * /rss/channel/item/description
     *
     * @param type
     * @param language
     * @param value
     */
    void summary(String type, String language, String value);

    /**
     * /rss/channel/item/description
     *
     * @param type
     * @param language
     * @param value
     */
    void content(String type, String language, String value);

    /**
     * /rss/channel/item/author
     *
     * @param name
     */
    void authors(String name);

    /**
     * /rss/channel/item/category
     *
     * @param value
     * @param domain
     */
    void tags(String value, String domain);

    /**
     * /rss/channel/item/comments
     *
     * @param value
     */
    void comments(String value);

    /**
     * /rss/channel/item/enclosure
     *
     * @param length
     * @param type
     * @param url
     */
    void enclosure(String length, String type, String url);

    /**
     * /rss/channel/item/guid
     *
     * @param value
     * @param isPermaLink
     */
    void id(String value, Boolean isPermaLink);

    /**
     * /rss/channel/item/pubDate
     *
     * @param date
     * @param strDate
     */
    void published(Date date, String strDate);

    /**
     * @param date
     * @param strDate
     */
    void updated(Date date, String strDate);


    /**
     * /rss/channel/item/source
     *
     * @param value
     * @param link
     */
    void source(String value, String link);


}
