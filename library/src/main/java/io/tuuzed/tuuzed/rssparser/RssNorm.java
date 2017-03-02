package io.tuuzed.tuuzed.rssparser;

/**
 * @author TuuZed
 */
class RssNorm {
    /**
     * <rss version="2.0">...</rss>
     */
    public static final String RSS = "rss";
    public static final String RSS_VERSION = "version";
    /**
     * <rss version="2.0">
     * <channel>...</channel>
     * </rss>
     */
    public static final String CHANNEL = "channel";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <title>Example rss title</title>
     * ...
     * </channel>
     */
    public static final String TITLE = "title";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <link>Example rss link</link>
     * ...
     * </channel>
     */
    public static final String LINK = "link";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <description>Example rss description</description>
     * ...
     * </channel>
     */
    public static final String DESCRIPTION = "description";

    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <category domain="domain.com">Example rss category</category>
     * ...
     * </channel>
     */
    public static final String CATEGORY = "category";
    public static final String CATEGORY_DOMAIN = "domain";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <cloud domain="domain" port="port" path="path" registerProcedure="registerProcedure" protocol="protocol"/>
     * ...
     * </channel>
     */
    public static final String CLOUD = "cloud";
    public static final String CLOUD_DOMAIN = "domain";
    public static final String CLOUD_PORT = "port";
    public static final String CLOUD_PATH = "path";
    public static final String CLOUD_REGISTER_PROCEDURE = "registerProcedure";
    public static final String CLOUD_PROTOCOL = "protocol";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <copyright>domain.com All Rights Reserved</copyright>
     * ...
     * </channel>
     */
    public static final String COPYRIGHT = "copyright";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <docs>docs</docs>
     * ...
     * </channel>
     */
    public static final String DOCS = "docs";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <generator>generator</generator>
     * ...
     * </channel>
     */
    public static final String GENERATOR = "generator";
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
    public static final String IMAGE = "image";
    public static final String IMAGE_TITLE = "title";
    public static final String IMAGE_HEIGHT = "height";
    public static final String IMAGE_WIDTH = "width";
    public static final String IMAGE_LINK = "link";
    public static final String IMAGE_DESCRIPTION = "description";
    public static final String IMAGE_URL = "url";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <language>zh-cn</language>
     * ...
     * </channel>
     */
    public static final String LANGUAGE = "language";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <lastBuildDate>2016-12-03 17:36:04</lastBuildDate>
     * ...
     * </channel>
     */
    public static final String LAST_BUILD_DATE = "lastBuildDate";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <managingEditor>managingEditor</managingEditor>
     * ...
     * </channel>
     */
    public static final String MANAGING_EDITOR = "managingEditor";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <pubDate>2016-12-03 17:36:04</pubDate>
     * ...
     * </channel>
     */
    public static final String PUB_DATE = "pubDate";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <rating>rating</rating>
     * ...
     * </channel>
     */
    public static final String RATING = "rating";

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
    public static final String SKIP_DAYS = "skipDays";
    public static final String SKIP_DAYS_DAY = "day";

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
    public static final String SKIP_HOURS = "skipHours";
    public static final String SKIP_HOURS_HOUR = "hour";
    /**
     * <textinput>
     * <title>textinput title</title>
     * <name>name</name>
     * <link>http://www.domain.com</link>
     * <description>textinput description</description>
     * </textinput>
     */
    public static final String TEXT_INPUT = "textinput";
    public static final String TEXT_INPUT_TITLE = "title";
    public static final String TEXT_INPUT_NAME = "name";
    public static final String TEXT_INPUT_LINK = "link";
    public static final String TEXT_INPUT_DESCRIPTION = "description";

    /**
     * /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <ttl>ttl</ttl>
     * ...
     * </channel>
     */
    public static final String TTL = "ttl";
    /**
     * <rss version="2.0">
     * <channel>
     * ...
     * <webMaster>webMaster</webMaster>
     * ...
     * </channel>
     */
    public static final String WEB_MASTER = "webMaster";

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
    public static final String ITEM = "item";
    public static final String ITEM_TITLE = "title";
    public static final String ITEM_LINK = "link";
    public static final String ITEM_AUTHOR = "author";
    public static final String ITEM_CATEGORY = "category";
    public static final String ITEM_CATEGORY_DOMAIN = "domain";
    public static final String ITEM_PUB_DATE = "pubDate";
    public static final String ITEM_COMMENTS = "comments";
    public static final String ITEM_DESCRIPTION = "description";
    public static final String ITEM_ENCLOSURE = "enclosure";
    public static final String ITEM_ENCLOSURE_LENGTH = "length";
    public static final String ITEM_ENCLOSURE_TYPE = "type";
    public static final String ITEM_ENCLOSURE_URL = "url";
    public static final String ITEM_GUID = "guid";
    public static final String ITEM_GUID_IS_PERMA_LINK = "isPermaLink";
    public static final String ITEM_SOURCE = "source";
    public static final String ITEM_SOURCE_URL = "url";
}
