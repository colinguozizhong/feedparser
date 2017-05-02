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
package io.github.tuuzed.rssparser.callback;

import java.util.Date;
import java.util.List;

public interface RssCallback {
    void begin();

    void rss(String version);

    void title(String title);

    void description(String description);

    void link(String link);

    void category(String category, String domain);

    void cloud(String domain, String port, String path, String registerProcedure, String protocol);

    void copyright(String copyright);

    void docs(String docs);

    void generator(String generator);

    void language(String language);

    void lastBuildDate(Date lastBuildDate, String strLastBuild);

    void managingEditor(String managingEditor);

    void pubDate(Date pubDate, String strPubDate);

    void rating(String rating);

    void skipDays(List<String> skipDays);

    void skipHours(List<String> skipHours);

    void ttl(String ttl);

    void webMaster(String webMaster);


    void error(Throwable e);

    void end();

    // textInput
    void textInputBegin();

    void textInputTitle(String title);

    void textInputName(String name);

    void textInputLink(String link);

    void textInputDescription(String description);

    void textInputEnd();

    // image
    void imageBegin();

    void imageTitle(String title);

    void imageUrl(String url);

    void imageLink(String link);

    void imageDescription(String description);

    void imageHeight(String height);

    void imageWidth(String width);

    void imageEnd();

    // item
    void itemBegin();

    void itemTitle(String title);

    void itemDescription(String description);

    void itemLink(String link);

    void itemAuthor(String author);

    void itemCategory(String category, String domain);

    void itemComments(String comments);

    void itemEnclosure(String length, String type, String url);

    void itemGuid(String guid, boolean isPermaLink);

    void itemSource(String source, String url);

    void itemPubDate(Date pubDate, String strPubDate);

    void itemEnd();
}
