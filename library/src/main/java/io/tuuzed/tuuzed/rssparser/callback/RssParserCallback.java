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
package io.tuuzed.tuuzed.rssparser.callback;


import java.util.Date;
import java.util.List;

public interface RssParserCallback {
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

    void lastBuildDate(Date lastBuildDate);

    void managingEditor(String managingEditor);

    void pubDate(Date pubDate);

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

    void itemPubDate(Date pubDate);

    void itemEnd();
}
