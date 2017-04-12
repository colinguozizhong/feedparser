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

import java.io.InputStream;
import java.io.Reader;
import java.text.DateFormat;

import io.github.tuuzed.rssparser.callback.RssParserCallback;

/**
 * @author TuuZed
 */
public interface RssParser {

    void parse(String url, RssParserCallback callback);

    void parse(String url, String defCharSet, RssParserCallback callback);

    void parse(Reader reader, RssParserCallback callback);

    void parse(InputStream is, String charSet, RssParserCallback callback);

    void addDateFormat(DateFormat format);
}
