package io.tuuzed.tuuzed.rssparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.tuuzed.tuuzed.rssparser.callback.RssParserCallback;
import io.tuuzed.tuuzed.rssparser.util.DateUtils;

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
                RssParserCallback.ImageCallback imageCallback = callback.getImageCallback();
                if (imageCallback != null) {
                    imageCallback.begin();
                }
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
                RssParserCallback.TextInputCallBack textInputCallBack = callback.getTextInputCallBack();
                if (textInputCallBack != null) {
                    textInputCallBack.begin();
                }
                isBeginTextInput = true;
                break;
            case RssNorm.ITEM:
                RssParserCallback.ItemCallback itemCallback = callback.getItemCallback();
                if (itemCallback != null) {
                    itemCallback.begin();
                }
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
                isBeginImage = false;
                RssParserCallback.ImageCallback imageCallback = callback.getImageCallback();
                if (imageCallback != null) {
                    imageCallback.end();
                }
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
                isBeginTextInput = false;
                RssParserCallback.TextInputCallBack textInputCallBack = callback.getTextInputCallBack();
                if (textInputCallBack != null) {
                    textInputCallBack.end();
                }
                break;
            case RssNorm.ITEM:
                isBeginItem = false;
                RssParserCallback.ItemCallback itemCallback = callback.getItemCallback();
                if (itemCallback != null) {
                    itemCallback.end();
                }
                break;
        }
    }

    protected void channel(RssParserCallback callback, String currentTag, String text, Map<String, String> attrs) {

        switch (currentTag) {
            case RssNorm.TITLE:
                callback.title(text);
                break;
            case RssNorm.LINK:
                callback.link(text);
                break;
            case RssNorm.DESCRIPTION:
                callback.description(text);
                break;
            case RssNorm.CATEGORY:
                callback.category(text, attrs.get(RssNorm.CATEGORY_DOMAIN));
                break;
            case RssNorm.CLOUD:
                callback.cloud(
                        attrs.get(RssNorm.CLOUD_DOMAIN),
                        attrs.get(RssNorm.CLOUD_PORT),
                        attrs.get(RssNorm.CLOUD_PATH),
                        attrs.get(RssNorm.CLOUD_REGISTER_PROCEDURE),
                        attrs.get(RssNorm.CLOUD_PROTOCOL));
                break;
            case RssNorm.COPYRIGHT:
                callback.copyright(text);
                break;
            case RssNorm.DOCS:
                callback.docs(text);
                break;
            case RssNorm.GENERATOR:
                callback.generator(text);
                break;
            case RssNorm.LANGUAGE:
                callback.language(text);
                break;
            case RssNorm.LAST_BUILD_DATE:
                callback.lastBuildDate(DateUtils.parseDate(text));
                break;
            case RssNorm.MANAGING_EDITOR:
                callback.managingEditor(text);
                break;
            case RssNorm.PUB_DATE:
                callback.pubDate(DateUtils.parseDate(text));
                break;
            case RssNorm.RATING:
                callback.rating(text);
                break;
            case RssNorm.TTL:
                callback.ttl(text);
                break;
            case RssNorm.WEB_MASTER:
                callback.webMaster(text);
                break;
        }
    }

    protected void image(RssParserCallback callback, String currentTag, String text) {
        RssParserCallback.ImageCallback imageCallback = callback.getImageCallback();
        if (imageCallback != null) {
            switch (currentTag) {
                case RssNorm.IMAGE_TITLE:
                    imageCallback.title(text);
                    break;
                case RssNorm.IMAGE_HEIGHT:
                    imageCallback.height(text);
                    break;
                case RssNorm.IMAGE_WIDTH:
                    imageCallback.width(text);
                    break;
                case RssNorm.IMAGE_LINK:
                    imageCallback.link(text);
                    break;
                case RssNorm.IMAGE_DESCRIPTION:
                    imageCallback.description(text);
                    break;
                case RssNorm.IMAGE_URL:
                    imageCallback.url(text);
                    break;

            }
        }
    }

    protected void textInput(RssParserCallback callback, String currentTag, String text) {
        RssParserCallback.TextInputCallBack textInputCallBack = callback.getTextInputCallBack();
        if (textInputCallBack != null) {
            switch (currentTag) {
                case RssNorm.TEXT_INPUT_TITLE:
                    textInputCallBack.title(text);
                    break;
                case RssNorm.TEXT_INPUT_LINK:
                    textInputCallBack.link(text);
                    break;
                case RssNorm.TEXT_INPUT_DESCRIPTION:
                    textInputCallBack.description(text);
                    break;
                case RssNorm.TEXT_INPUT_NAME:
                    textInputCallBack.name(text);
                    break;
            }
        }
    }

    protected void item(RssParserCallback callback, String currentTag, String text, Map<String, String> attrs) {
        RssParserCallback.ItemCallback itemCallback = callback.getItemCallback();
        if (itemCallback != null) {
            switch (currentTag) {
                case RssNorm.ITEM_TITLE:
                    itemCallback.title(text);
                    break;
                case RssNorm.ITEM_LINK:
                    itemCallback.link(text);
                    break;
                case RssNorm.ITEM_AUTHOR:
                    itemCallback.author(text);
                    break;
                case RssNorm.ITEM_CATEGORY:
                    itemCallback.category(text, attrs.get(RssNorm.ITEM_CATEGORY_DOMAIN));
                    break;
                case RssNorm.ITEM_PUB_DATE:
                    itemCallback.pubDate(DateUtils.parseDate(text));
                    break;
                case RssNorm.ITEM_COMMENTS:
                    itemCallback.comments(text);
                    break;
                case RssNorm.ITEM_DESCRIPTION:
                    itemCallback.description(text);
                    break;
                case RssNorm.ITEM_ENCLOSURE:
                    itemCallback.enclosure(
                            attrs.get(RssNorm.ITEM_ENCLOSURE_LENGTH),
                            attrs.get(RssNorm.ITEM_ENCLOSURE_TYPE),
                            attrs.get(RssNorm.ITEM_ENCLOSURE_URL));
                    break;
                case RssNorm.ITEM_GUID:
                    itemCallback.guid(text,
                            Boolean.parseBoolean(attrs.get(RssNorm.ITEM_GUID_IS_PERMA_LINK)));
                    break;
                case RssNorm.ITEM_SOURCE:
                    callback.getItemCallback().source(text,
                            attrs.get(RssNorm.ITEM_SOURCE_URL));
                    break;
            }
        }
    }
    
}
