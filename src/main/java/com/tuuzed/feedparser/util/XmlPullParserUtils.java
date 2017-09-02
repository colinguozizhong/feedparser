package com.tuuzed.feedparser.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XmlPullParserUtils {

    @NotNull
    public static Map<String, String> getAttrs(@NotNull XmlPullParser xmlPullParser) {
        Map<String, String> attrs = new HashMap<>();
        int count = xmlPullParser.getAttributeCount();
        for (int i = 0; i < count; i++) {
            attrs.put(xmlPullParser.getAttributeName(i), xmlPullParser.getAttributeValue(i).trim());
        }
        return attrs;
    }

    @Nullable
    public static String getNextText(@NotNull XmlPullParser xmlPullParser) {
        try {
            return xmlPullParser.nextText().trim();
        } catch (XmlPullParserException | IOException e) {
            return null;
        }
    }
}
