package com.tuuzed.feedparser;

import com.tuuzed.feedparser.internal.AtomParser;
import com.tuuzed.feedparser.internal.GenericParser;
import com.tuuzed.feedparser.internal.RssParser;
import com.tuuzed.feedparser.util.DateParser;
import com.tuuzed.feedparser.util.FastXmlPullParser;
import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.Reader;
import java.text.DateFormat;

public class FeedParser {

    public static void appendDateFormat(@NotNull DateFormat format) {
        DateParser.appendDateFormat(format);
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

}
