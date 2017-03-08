package io.tuuzed.tuuzed.rssparser;

import org.junit.Test;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void saxRssParserTest() throws IOException, ParserConfigurationException, SAXException {
        //final String url = "http://news.qq.com/newsgn/rss_newsgn.xml";
        final String url = "http://news.163.com/special/00011K6L/rss_newstop.xml";
        SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
        RssParser rssParser = new SaxRssParser(saxParser);
        rssParser.parse(url, "utf-8", new TestCallback());
    }

    @Test
    public void xmlPullRssParserTest() throws XmlPullParserException {
        final String url = "http://news.163.com/special/00011K6L/rss_newstop.xml";
        XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
        RssParser rssParser = new XmlPullRssParser(xmlPullParser);
        rssParser.parse(url, "utf-8", new TestCallback());
    }

}