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

public class TestCallback implements RssParserCallback {

    private static void print(String s) {
        System.out.printf("=> %s() ", s);
    }

    private static void println(String s) {
        System.out.println(s);
    }

    @Override
    public void begin() {
        println("");
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println(">>>>>>>>>>>>>>>>>>>>>>>>>>  rss");
    }

    @Override
    public void rss(String version) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("version = " + version);
    }

    @Override
    public void title(String title) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("title = " + title);
    }

    @Override
    public void description(String description) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("description = " + description);
    }

    @Override
    public void link(String link) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("link = " + link);
    }

    @Override
    public void category(String category, String domain) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("category = " + category + " , domain=" + domain);
    }

    @Override
    public void cloud(String domain, String port, String path, String registerProcedure, String protocol) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("domain = " + domain + " , port=" + port + " , path=" + path + " , registerProcedure=" + registerProcedure + " , protocol=" + protocol);
    }

    @Override
    public void copyright(String copyright) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("copyright=" + copyright);
    }

    @Override
    public void docs(String docs) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("docs=" + docs);
    }

    @Override
    public void generator(String generator) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("generator=" + generator);
    }

    @Override
    public ImageCallback getImageCallback() {
        return new ImageCallbackImpl();
    }

    @Override
    public void language(String language) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("language = " + language);
    }

    @Override
    public void lastBuildDate(Date lastBuildDate) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("lastBuildDate = " + lastBuildDate);
    }

    @Override
    public void managingEditor(String managingEditor) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("managingEditor = " + managingEditor);
    }

    @Override
    public void pubDate(Date pubDate) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("pubDate = " + pubDate);
    }

    @Override
    public void rating(String rating) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("rating = " + rating);
    }

    @Override
    public void skipDays(List<String> skipDays) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("skipDays = " + skipDays.toString());
    }

    @Override
    public void skipHours(List<String> skipHours) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("skipHours = " + skipHours.toString());
    }

    @Override
    public TextInputCallBack getTextInputCallBack() {
        return new TextInputCallBackImpl();
    }

    @Override
    public void ttl(String ttl) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("ttl = " + ttl);
    }

    @Override
    public void webMaster(String webMaster) {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println("webMaster = " + webMaster);
    }

    @Override
    public ItemCallback getItemCallback() {
        return new ItemCallbackImpl();
    }

    @Override
    public void error(Throwable e) {
        System.err.print(" => " + Thread.currentThread().getStackTrace()[1].getMethodName());
        System.err.println(e.toString());
    }

    @Override
    public void end() {
        print(Thread.currentThread().getStackTrace()[1].getMethodName());
        println(">>>>>>>>>>>>>>>>>>>>>>>>>>   rss");
        println("");
    }

    private static class ItemCallbackImpl implements RssParserCallback.ItemCallback {

        @Override
        public void begin() {
            println("");
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println(">>>>>>>>>>>>>>>>>>>>>>>>>>   item");
        }

        @Override
        public void title(String title) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("title = " + title);
        }

        @Override
        public void description(String description) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("description = " + description);
        }

        @Override
        public void link(String link) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("link = " + link);
        }

        @Override
        public void author(String author) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("author = " + author);
        }

        @Override
        public void category(String category, String domain) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("category = " + category + " , domain = " + domain);
        }

        @Override
        public void comments(String comments) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("comments = " + comments);
        }

        @Override
        public void enclosure(String length, String type, String url) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("length = " + length + " , type = " + type + " , url = " + url);
        }

        @Override
        public void guid(String guid, boolean isPermaLink) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("guid = " + guid + " , isPermaLink = " + isPermaLink);
        }

        @Override
        public void source(String source, String url) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("source = " + source + " , url = " + url);
        }

        @Override
        public void pubDate(Date pubDate) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("pubDate = " + pubDate);
        }

        @Override
        public void end() {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println(">>>>>>>>>>>>>>>>>>>>>>>>>>   item");
            println("");
        }
    }

    private static class TextInputCallBackImpl implements RssParserCallback.TextInputCallBack {
        @Override
        public void begin() {
            println("");
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println(">>>>>>>>>>>>>>>>>>>>>>>>>>   textinput");
        }

        @Override
        public void title(String title) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("title = " + title);
        }

        @Override
        public void name(String name) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("name = " + name);
        }

        @Override
        public void link(String link) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("link = " + link);
        }

        @Override
        public void description(String description) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("description = " + description);
        }

        @Override
        public void end() {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println(">>>>>>>>>>>>>>>>>>>>>>>>>>>  textinput");
            println("");
        }
    }

    private static class ImageCallbackImpl implements RssParserCallback.ImageCallback {
        @Override
        public void begin() {
            println("");
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println(">>>>>>>>>>>>>>>>>>>>>>>>>>   image ");
        }

        @Override
        public void title(String title) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("title = " + title);
        }

        @Override
        public void url(String url) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("url = " + url);
        }

        @Override
        public void link(String link) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("link = " + link);
        }

        @Override
        public void description(String description) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("description = " + description);
        }

        @Override
        public void height(String height) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("height = " + height);
        }

        @Override
        public void width(String width) {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println("width = " + width);
        }

        @Override
        public void end() {
            print(Thread.currentThread().getStackTrace()[1].getMethodName());
            println(">>>>>>>>>>>>>>>>>>>>>>>>>>   image");
            println("");
        }
    }
}
