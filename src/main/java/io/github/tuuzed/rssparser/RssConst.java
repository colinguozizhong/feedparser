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

/**
 * @author TuuZed
 */
final class RssConst {
    /**
     * <rss version="2.0">...</rss>
     */
    static final String RSS = "rss";
    static final String RSS_VERSION = "version";
    /**
     * <rss version="2.0">
     * <channel>...</channel>
     * </rss>
     */
    static final String CHANNEL = "channel";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <title>Example rss title</title>
     * ...
     * </channel>
     */
    static final String TITLE = "title";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <link>Example rss link</link>
     * ...
     * </channel>
     */
    static final String LINK = "link";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <description>Example rss description</description>
     * ...
     * </channel>
     */
    static final String DESCRIPTION = "description";

    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <category domain="domain.com">Example rss category</category>
     * ...
     * </channel>
     */
    static final String CATEGORY = "category";
    static final String CATEGORY_DOMAIN = "domain";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <cloud domain="domain" port="port" path="path" registerProcedure="registerProcedure" protocol="protocol"/>
     * ...
     * </channel>
     */
    static final String CLOUD = "cloud";
    static final String CLOUD_DOMAIN = "domain";
    static final String CLOUD_PORT = "port";
    static final String CLOUD_PATH = "path";
    static final String CLOUD_REGISTER_PROCEDURE = "registerProcedure";
    static final String CLOUD_PROTOCOL = "protocol";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <copyright>domain.com All Rights Reserved</copyright>
     * ...
     * </channel>
     */
    static final String COPYRIGHT = "copyright";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <docs>docs</docs>
     * ...
     * </channel>
     */
    static final String DOCS = "docs";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <generator>generator</generator>
     * ...
     * </channel>
     */
    static final String GENERATOR = "generator";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <image>
     * <title>image title</title>
     * <height>height</height>
     * <width>width</width>
     * <link>http://www.domain.com</link>
     * <description>image description</description>
     * <url>http://www.domain.com/rss/logo_news.gif</url>
     * </image>
     * ...
     * </channel>
     */
    static final String IMAGE = "image";
    static final String IMAGE_TITLE = "title";
    static final String IMAGE_HEIGHT = "height";
    static final String IMAGE_WIDTH = "width";
    static final String IMAGE_LINK = "link";
    static final String IMAGE_DESCRIPTION = "description";
    static final String IMAGE_URL = "url";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <language>zh-cn</language>
     * ...
     * </channel>
     */
    static final String LANGUAGE = "language";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <lastBuildDate>2016-12-03 17:36:04</lastBuildDate>
     * ...
     * </channel>
     */
    static final String LAST_BUILD_DATE = "lastBuildDate";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <managingEditor>managingEditor</managingEditor>
     * ...
     * </channel>
     */
    static final String MANAGING_EDITOR = "managingEditor";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <pubDate>2016-12-03 17:36:04</pubDate>
     * ...
     * </channel>
     */
    static final String PUB_DATE = "pubDate";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <rating>rating</rating>
     * ...
     * </channel>
     */
    static final String RATING = "rating";

    /**
     * /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <skipHours>
     * <hour>1</hour>
     * <hour>2</hour>
     * <hour>3</hour>
     * <hour>4</hour>
     * <hour>5</hour>
     * <hour>6</hour>
     * </skipHours>
     * ...
     * </channel>
     */
    static final String SKIP_DAYS = "skipDays";
    static final String SKIP_DAYS_DAY = "day";

    /**
     * /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <skipHours>
     * <hour>1</hour>
     * <hour>2</hour>
     * <hour>3</hour>
     * <hour>4</hour>
     * <hour>5</hour>
     * <hour>6</hour>
     * </skipHours>
     * ...
     * </channel>
     */
    static final String SKIP_HOURS = "skipHours";
    static final String SKIP_HOURS_HOUR = "hour";
    /**
     * <textinput>
     * <title>textinput title</title>
     * <name>name</name>
     * <link>http://www.domain.com</link>
     * <description>textinput description</description>
     * </textinput>
     */
    static final String TEXT_INPUT = "textinput";
    static final String TEXT_INPUT_TITLE = "title";
    static final String TEXT_INPUT_NAME = "name";
    static final String TEXT_INPUT_LINK = "link";
    static final String TEXT_INPUT_DESCRIPTION = "description";

    /**
     * /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <ttl>ttl</ttl>
     * ...
     * </channel>
     */
    static final String TTL = "ttl";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <webMaster>webMaster</webMaster>
     * ...
     * </channel>
     */
    static final String WEB_MASTER = "webMaster";

    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <item>
     * <title>item title</title>
     * <link>http://www.domain.com</link>
     * <author>/www.domain.com</author>
     * <category domain="domain.com">
     * domain.com | item
     * </category>
     * <pubDate>2016-12-03 17:36:04</pubDate>
     * <comments>comments</comments>
     * <description>
     * description
     * </description>
     * <enclosure length="length" type="type" url="url"/>
     * <guid isPermaLink="true">www.domain.com</guid>
     * <source url="url">Source</source>
     * </item>
     * ...
     * </channel>
     */
    static final String ITEM = "item";
    static final String ITEM_TITLE = "title";
    static final String ITEM_LINK = "link";
    static final String ITEM_AUTHOR = "author";
    static final String ITEM_CATEGORY = "category";
    static final String ITEM_CATEGORY_DOMAIN = "domain";
    static final String ITEM_PUB_DATE = "pubDate";
    static final String ITEM_COMMENTS = "comments";
    static final String ITEM_DESCRIPTION = "description";
    static final String ITEM_ENCLOSURE = "enclosure";
    static final String ITEM_ENCLOSURE_LENGTH = "length";
    static final String ITEM_ENCLOSURE_TYPE = "type";
    static final String ITEM_ENCLOSURE_URL = "url";
    static final String ITEM_GUID = "guid";
    static final String ITEM_GUID_IS_PERMA_LINK = "isPermaLink";
    static final String ITEM_SOURCE = "source";
    static final String ITEM_SOURCE_URL = "url";
}