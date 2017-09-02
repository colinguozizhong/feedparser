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
package com.tuuzed.feedparser.internal;

import com.tuuzed.feedparser.FeedCallback;
import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;

public abstract class GenericParser {

    FeedCallback callback;

    GenericParser(@NotNull FeedCallback callback) {
        this.callback = callback;
    }

    public abstract void startTag(@NotNull String tag, @NotNull XmlPullParser xmlPullParser);

    public abstract void endTag(@NotNull String tag);
}
