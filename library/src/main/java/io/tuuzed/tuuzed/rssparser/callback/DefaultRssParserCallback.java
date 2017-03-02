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

public abstract class DefaultRssParserCallback implements RssParserCallback {
    @Override
    public void begin() {

    }

    @Override
    public void rss(String version) {

    }

    @Override
    public void title(String title) {

    }

    @Override
    public void description(String description) {

    }

    @Override
    public void link(String link) {

    }

    @Override
    public void category(String category, String domain) {

    }

    @Override
    public void cloud(String domain, String port, String path, String registerProcedure, String protocol) {

    }

    @Override
    public void copyright(String copyright) {

    }

    @Override
    public void docs(String docs) {

    }

    @Override
    public void generator(String generator) {

    }

    @Override
    public ImageCallback getImageCallback() {
        return null;
    }

    @Override
    public void language(String language) {

    }

    @Override
    public void lastBuildDate(Date lastBuildDate) {

    }

    @Override
    public void managingEditor(String managingEditor) {

    }

    @Override
    public void pubDate(Date pubDate) {

    }

    @Override
    public void rating(String rating) {

    }

    @Override
    public void skipDays(List<String> skipDays) {

    }

    @Override
    public void skipHours(List<String> skipHours) {

    }

    @Override
    public TextInputCallBack getTextInputCallBack() {
        return null;
    }

    @Override
    public void ttl(String ttl) {

    }

    @Override
    public void webMaster(String webMaster) {

    }

    @Override
    public ItemCallback getItemCallback() {
        return null;
    }

    @Override
    public void error(Throwable e) {

    }

    @Override
    public void end() {

    }
}
