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

public interface ImageCallback extends Callback {

    void title(String type, String language, String value);

    void links(String rel, String type, String href);

    void link(String value);

    void description(String value);

    void width(String value);

    void height(String value);

    void href(String value);
}
