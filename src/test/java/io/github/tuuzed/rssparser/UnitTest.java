/* Copyright 2017 TuuZed
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.tuuzed.rssparser;

import io.github.tuuzed.rssparser.callback.DefaultRssParserCallback;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnitTest {
    @Test
    public void testUrlConnect() throws IOException {
        String url = "http://rss.news.sohu.com/rss/pfocus.xml";
        String url2 = "https://www.zhihu.com/";
        HttpURLConnection connection = (HttpURLConnection) new URL(url2).openConnection();
        String regex = "(encoding|charset)=.*(" +
                "gb2312|GB2312|" +
                "utf-8|UTF-8|" +
                "gbk|GBK" +
                ").*";
        Pattern pattern = Pattern.compile(regex);
        String field = connection.getContentType();
        Matcher matcher = pattern.matcher(field);
        if (matcher.find()) {
            System.out.println(matcher.group(2));
        }
        int code = connection.getResponseCode();
        System.out.println(code);
    }

    @Test
    public void xmlPullRssParserTest() {
        String url = "http://rss.news.sohu.com/rss/pfocus.xml";
        XmlPullRssParser.getDefault().addDateFormat(new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss Z", Locale.CHINA));
        XmlPullRssParser.getDefault().parse(url, new DefaultRssParserCallback() {
            @Override
            public void itemTitle(String title) {
                super.itemTitle(title);
                System.out.println(title);
            }

            @Override
            public void itemLink(String link) {
                super.itemLink(link);
                System.out.println(link);
            }

            @Override
            public void itemPubDate(Date pubDate) {
                super.itemPubDate(pubDate);
                System.out.println(pubDate);
            }

            @Override
            public void end() {
                super.end();
//                System.out.println("end");
            }

            @Override
            public void error(Throwable e) {
                super.error(e);
                e.printStackTrace();
            }
        });
    }
}
