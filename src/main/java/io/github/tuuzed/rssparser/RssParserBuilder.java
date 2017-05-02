package io.github.tuuzed.rssparser;


import io.github.tuuzed.rssparser.exception.XmlPullInstantiationException;
import io.github.tuuzed.rssparser.util.TextUtils;
import okhttp3.OkHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class RssParserBuilder implements Builder<RssParser> {
    OkHttpClient okHttpClient;
    String defCharSet;
    XmlPullParser xmlPullParser;

    public RssParserBuilder setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        return this;
    }

    public RssParserBuilder setDefCharSet(String defCharSet) {
        this.defCharSet = defCharSet;
        return this;
    }

    public RssParserBuilder setXmlPullParser(XmlPullParser xmlPullParser) {
        this.xmlPullParser = xmlPullParser;
        return this;
    }

    @Override
    public RssParser build() {
        if (TextUtils.isEmpty(defCharSet)) {
            defCharSet = "utf-8";
        }
        if (xmlPullParser == null) {
            try {
                xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
            } catch (XmlPullParserException e) {
                throw new XmlPullInstantiationException();
            }
        }
        return new XmlPullRssParser(this);
    }
}
