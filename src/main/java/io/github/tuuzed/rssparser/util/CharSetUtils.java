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
package io.github.tuuzed.rssparser.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharSetUtils {
    private final static Pattern sPattern = Pattern.compile(
            "(encoding|charset)=.*(GB2312|UTF-8|GBK).*",
            Pattern.CASE_INSENSITIVE);

    public static String getCharSet(String src) {
        if (TextUtils.isEmpty(src)) {
            return null;
        }
        Matcher matcher = sPattern.matcher(src);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return null;
    }


    /**
     * 获取输入流可能的编码
     *
     * @param is
     * @param charSet
     * @return
     * @throws IOException
     */
    public static InputStream getCharSet(InputStream is, String[] charSet) throws IOException {
        StringBuilder sb = new StringBuilder();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len;
        while ((len = is.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, len));
            outputStream.write(bytes, 0, len);
            outputStream.flush();
        }
        String content = sb.toString();
        Matcher matcher = sPattern.matcher(content);
        if (matcher.find()) {
            charSet[0] = matcher.group(2);
        }
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
