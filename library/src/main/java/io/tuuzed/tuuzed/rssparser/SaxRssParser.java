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

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;

import io.tuuzed.tuuzed.rssparser.callback.RssParserCallback;


public class SaxRssParser extends BaseRssParser {
    private static SAXParser mSAXParser;

    private SaxRssParser() {
    }


    private static class Holder {
        private static SaxRssParser instance = new SaxRssParser();
    }

    public static SaxRssParser getInstance() {
        return Holder.instance;
    }


    public void init(SAXParser saxParser) {
        mSAXParser = saxParser;
    }

    public void parse(String url, String defCharSet, RssParserCallback callback) {
        if (mSAXParser == null) {
            throw new RuntimeException("SAXParser object cannot be empty ! Are you sure you have initialized ?");
        } else {
            try {
                mSAXParser.parse(url, new RssHandler(this, callback));
            } catch (SAXException | IOException e) {
                callback.error(e);
            }
        }
    }

    public void parse(Reader reader, RssParserCallback callback) {
        if (mSAXParser == null) {
            throw new RuntimeException("SAXParser object cannot be empty ! Are you sure you have initialized ?");
        } else {
            try {
                mSAXParser.parse(new InputSource(reader), new RssHandler(this, callback));
            } catch (SAXException | IOException e) {
                callback.error(e);
            }
        }
    }

    public void parse(InputStream is, String charSet, RssParserCallback callback) {
        if (mSAXParser == null) {
            throw new RuntimeException("SAXParser object cannot be empty ! Are you sure you have initialized ?");
        } else {
            try {
                mSAXParser.parse(is, new RssHandler(this, callback));
            } catch (SAXException | IOException e) {
                callback.error(e);
            }
        }
    }

    private static class RssHandler extends DefaultHandler {
        private RssParserCallback callback;
        private BaseRssParser rssParser;
        private String currentTag = null;
        private Map<String, String> attrs;

        RssHandler(BaseRssParser rssParser, RssParserCallback callback) {
            this.rssParser = rssParser;
            this.callback = callback;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            attrs = new HashMap<>();
            for (int i = 0; i < attributes.getLength(); i++) {
                attrs.put(attributes.getLocalName(i), attributes.getValue(i));
            }
            currentTag = qName;
            rssParser.startTag(callback, qName);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            currentTag = null;
            rssParser.endTag(callback, qName);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            if (currentTag == null) return;
            if (rssParser.isBeginRss) {
                if (currentTag.equals(RssNorm.RSS)) {
                    callback.rss(attrs.get(RssNorm.RSS_VERSION));
                }
            }
            if (rssParser.isBeginChannel && !rssParser.isBeginItem) {
                if (rssParser.isBeginImage) {
                    rssParser.image(callback, currentTag, getText(ch, start, length));
                } else if (rssParser.isBeginSkipDays) {
                    if (rssParser.temp_list != null && currentTag.equals(RssNorm.SKIP_DAYS_DAY)) {
                        rssParser.temp_list.add(getText(ch, start, length));
                    }
                } else if (rssParser.isBeginSkipHours) {
                    if (rssParser.temp_list != null && currentTag.equals(RssNorm.SKIP_HOURS_HOUR)) {
                        rssParser.temp_list.add(getText(ch, start, length));
                    }
                } else if (rssParser.isBeginTextInput) {
                    rssParser.textInput(callback, currentTag, getText(ch, start, length));
                } else {
                    rssParser.channel(callback, currentTag, getText(ch, start, length), attrs);
                }
            } else if (rssParser.isBeginItem) {
                rssParser.item(callback, currentTag, getText(ch, start, length), attrs);
            }
        }
    }

    private static String getText(char[] ch, int start, int length) {
        return new String(ch, start, length).trim();
    }

}
