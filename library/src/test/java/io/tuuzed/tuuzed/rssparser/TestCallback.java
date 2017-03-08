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

import java.util.Date;
import java.util.List;

import io.tuuzed.tuuzed.rssparser.callback.RssParserCallback;

class TestCallback implements RssParserCallback {
    @Override
    public void begin() {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("======================rss======================");
    }

    @Override
    public void rss(String version) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("version = " + version);

    }

    @Override
    public void title(String title) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("title = " + title);

    }

    @Override
    public void description(String description) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("description = " + description);
    }

    @Override
    public void link(String link) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("link = " + link);
    }

    @Override
    public void category(String category, String domain) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("category = " + category + " , domain=" + domain);
    }

    @Override
    public void cloud(String domain, String port, String path, String registerProcedure, String protocol) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("domain = " + domain + " , port=" + port + " , path=" + path + " , registerProcedure=" + registerProcedure + " , protocol=" + protocol);
    }

    @Override
    public void copyright(String copyright) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("copyright=" + copyright);
    }

    @Override
    public void docs(String docs) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("docs=" + docs);
    }

    @Override
    public void generator(String generator) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("generator=" + generator);
    }

    @Override
    public void language(String language) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("language = " + language);
    }

    @Override
    public void lastBuildDate(Date lastBuildDate) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("lastBuildDate = " + lastBuildDate);
    }

    @Override
    public void managingEditor(String managingEditor) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("managingEditor = " + managingEditor);
    }

    @Override
    public void pubDate(Date pubDate) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("pubDate = " + pubDate);
    }

    @Override
    public void rating(String rating) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("rating = " + rating);
    }

    @Override
    public void skipDays(List<String> skipDays) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("skipDays = " + skipDays.toString());
    }

    @Override
    public void skipHours(List<String> skipHours) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("skipHours = " + skipHours.toString());
    }


    @Override
    public void ttl(String ttl) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("ttl = " + ttl);
    }

    @Override
    public void webMaster(String webMaster) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("webMaster = " + webMaster);
    }


    @Override
    public void error(Throwable e) {
        System.err.print(" => " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.err.println(e.toString());
    }

    @Override
    public void end() {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("====================== rss ======================\n");
    }

    @Override
    public void textInputBegin() {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("====================== textInputBegin ======================");
    }

    @Override
    public void textInputTitle(String title) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("title = " + title);
    }

    @Override
    public void textInputName(String name) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("name = " + name);
    }

    @Override
    public void textInputLink(String link) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("link = " + link);
    }

    @Override
    public void textInputDescription(String description) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("description = " + description);
    }

    @Override
    public void textInputEnd() {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("====================== textInputEnd ======================\n");
    }

    @Override
    public void imageBegin() {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("====================== imageBegin ======================");
    }

    @Override
    public void imageTitle(String title) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("title = " + title);
    }

    @Override
    public void imageUrl(String url) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("url = " + url);
    }

    @Override
    public void imageLink(String link) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("link = " + link);
    }

    @Override
    public void imageDescription(String description) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("description = " + description);
    }

    @Override
    public void imageHeight(String height) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("height = " + height);
    }

    @Override
    public void imageWidth(String width) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("width = " + width);
    }

    @Override
    public void imageEnd() {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("====================== imageEnd ======================\n");
    }

    @Override
    public void itemBegin() {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("====================== itemBegin ======================");
    }

    @Override
    public void itemTitle(String title) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("title = " + title);
    }

    @Override
    public void itemDescription(String description) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("description = " + description);
    }

    @Override
    public void itemLink(String link) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("link = " + link);
    }

    @Override
    public void itemAuthor(String author) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("author = " + author);
    }

    @Override
    public void itemCategory(String category, String domain) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("category = " + category + "\tdomain = " + domain);
    }

    @Override
    public void itemComments(String comments) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("comments = " + comments);
    }

    @Override
    public void itemEnclosure(String length, String type, String url) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("length = " + length + "\ttype = " + type + "\turl = " + url);
    }

    @Override
    public void itemGuid(String guid, boolean isPermaLink) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("guid = " + guid + "\tisPermaLink = " + isPermaLink);
    }

    @Override
    public void itemSource(String source, String url) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("source = " + source + "\turl = " + url);
    }

    @Override
    public void itemPubDate(Date pubDate) {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("pubDate = " + pubDate);
    }

    @Override
    public void itemEnd() {
        System.out.print(Thread.currentThread().getStackTrace()[1].getMethodName() + "()\t");
        System.out.println("====================== itemEnd ======================\n");
    }
}
