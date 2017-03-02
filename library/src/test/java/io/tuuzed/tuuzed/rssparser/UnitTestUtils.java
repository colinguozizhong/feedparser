package io.tuuzed.tuuzed.rssparser;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class UnitTestUtils {
    public static List<String> getRssUrls() {
        File file = new File("rss_list.txt");
        List<String> rssUrls = new LinkedList<>();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (!rssUrls.contains(line)) {
                    rssUrls.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(br);
            closeStream(fr);
        }
        return rssUrls;
    }

    public static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
