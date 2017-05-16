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
package com.github.tuuzed.feedparser.callback;

import java.util.Date;
import java.util.List;

interface IFeedCallback extends Callback {
    void feed(String type, String version);

    void title(String type, String language, String value);

    void subtitle(String type, String language, String value);

    void links(String rel, String type, String href);

    void link(String value);

    void rights(String type, String language, String value);

    void language(String value);

    void generator(String uri, String version, String value);

    void skipDays(List<String> skipDays);

    void skipHours(List<String> skipHours);

    void published(Date date, String strDate);

    void updated(Date date, String strDate);

    void ttl(String ttl);

    void webMaster(String webMaster);

    void rating(String rating);

    void authors(String value);

    void docs(String docs);

    void cloud(String domain, String port, String path,
               String registerProcedure, String protocol);

    void tags(String tag, String domain);

    void error(Throwable throwable);

    void lethalError(Throwable throwable);
}
