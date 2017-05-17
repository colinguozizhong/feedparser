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

/**
 * /rss/channel/image
 */
public interface ImageCallback extends Callback {

    /**
     * /rss/channel/image/title
     *
     * @param value
     */
    void title(String value);

    /**
     * /rss/channel/image/link
     *
     * @param value
     */
    void link(String value);

    /**
     * /rss/channel/image/url
     *
     * @param value
     */
    void href(String value);


    /**
     * /rss/channel/image/description
     *
     * @param value
     */
    void description(String value);

    /**
     * /rss/channel/image/width
     *
     * @param value
     */
    void width(String value);

    /**
     * /rss/channel/image/height
     *
     * @param value
     */
    void height(String value);

}
