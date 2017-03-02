package io.tuuzed.tuuzed.rssparser.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import io.tuuzed.tuuzed.rssparser.RssParser;
import io.tuuzed.tuuzed.rssparser.SaxRssParser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RssParser mRssParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            mRssParser = SaxRssParser.getInstance();
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

    }
}
