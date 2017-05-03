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
package com.github.tuuzed.feedparser;

import java.util.Date;
import java.util.List;

public abstract class FeedCallback {


    public void begin() {

    }


    public void rss(String version) {

    }


    public void title(String title) {

    }


    public void description(String description) {

    }


    public void link(String link) {

    }


    public void category(String category, String domain) {

    }


    public void cloud(String domain, String port, String path, String registerProcedure, String protocol) {

    }


    public void copyright(String copyright) {

    }


    public void docs(String docs) {

    }


    public void generator(String generator) {

    }


    public void language(String language) {

    }


    public void lastBuildDate(Date lastBuildDate, String strLastBuildDate) {

    }


    public void managingEditor(String managingEditor) {

    }


    public void pubDate(Date pubDate, String strPubDate) {

    }


    public void rating(String rating) {

    }


    public void skipDays(List<String> skipDays) {

    }


    public void skipHours(List<String> skipHours) {

    }


    public void ttl(String ttl) {

    }


    public void webMaster(String webMaster) {

    }


    public void error(Throwable e) {

    }


    public void end() {

    }


    public void textInputBegin() {

    }


    public void textInputTitle(String title) {

    }


    public void textInputName(String name) {

    }


    public void textInputLink(String link) {

    }


    public void textInputDescription(String description) {

    }


    public void textInputEnd() {

    }


    public void imageBegin() {

    }


    public void imageTitle(String title) {

    }


    public void imageUrl(String url) {

    }


    public void imageLink(String link) {

    }


    public void imageDescription(String description) {

    }


    public void imageHeight(String height) {

    }


    public void imageWidth(String width) {

    }


    public void imageEnd() {

    }


    public void itemBegin() {

    }


    public void itemTitle(String title) {

    }


    public void itemDescription(String description) {

    }


    public void itemLink(String link) {

    }


    public void itemAuthor(String author) {

    }


    public void itemCategory(String category, String domain) {

    }


    public void itemComments(String comments) {

    }


    public void itemEnclosure(String length, String type, String url) {

    }


    public void itemGuid(String guid, boolean isPermaLink) {

    }


    public void itemSource(String source, String url) {

    }


    public void itemPubDate(Date pubDate, String strPubDate) {

    }


    public void itemEnd() {

    }
}
