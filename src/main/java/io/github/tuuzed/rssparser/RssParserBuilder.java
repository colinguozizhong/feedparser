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
