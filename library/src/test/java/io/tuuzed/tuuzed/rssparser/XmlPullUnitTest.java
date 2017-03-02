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

import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class XmlPullUnitTest {

    @Test
    public void localTest() throws FileNotFoundException, XmlPullParserException {
        RssParser rssParser = XmlPullRssParser.getInstance();
        rssParser.parse(new FileReader("example_rss.xml"), new TestCallback());
    }


    @Test
    public void remoteTest() {
        final String url = "http://news.qq.com/newsgn/rss_newsgn.xml";
        try {
            remote(url);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void remoteListTest() throws IOException, XmlPullParserException {
        List<String> urls = UnitTestUtils.getRssUrls();
        for (final String url : urls) {
            remote(url);
        }
    }

    private void remote(final String url) throws IOException, XmlPullParserException {
        RssParser rssParser = XmlPullRssParser.getInstance();
        rssParser.parse(url, "UTF-8", new TestCallback());
    }


}
