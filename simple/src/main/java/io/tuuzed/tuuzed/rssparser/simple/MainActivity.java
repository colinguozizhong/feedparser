package io.tuuzed.tuuzed.rssparser.simple;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import io.github.tuuzed.adapter.Items;
import io.github.tuuzed.adapter.RecyclerViewAdapter;
import io.tuuzed.tuuzed.rssparser.RssParser;
import io.tuuzed.tuuzed.rssparser.XmlPullRssParser;
import io.tuuzed.tuuzed.rssparser.callback.DefaultRssParserCallback;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private Items mItems;
    private RecyclerViewAdapter mAdapter;
    private RssParser mRssParser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mItems = new Items();
        mAdapter = new RecyclerViewAdapter(mItems);
        mAdapter.register(RssItem.class, new RssItemItemComponent());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        try {
            XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
            mRssParser = new XmlPullRssParser(xmlPullParser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 0x1234) {
                    Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                } else if (msg.what == 0x1111) {
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "成功" + mItems.size(), Toast.LENGTH_SHORT).show();
                }
                mSwipeRefreshLayout.setRefreshing(false);
                return false;
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                final String url = "http://news.qq.com/newsgn/rss_newsgn.xml";
                mRssParser.parse(url, new DefaultRssParserCallback() {
                    RssItem rssItem;

                    @Override
                    public void itemBegin() {
                        super.itemBegin();
                        rssItem = new RssItem();
                    }

                    @Override
                    public void itemTitle(String title) {
                        super.itemTitle(title);
                        rssItem.setTitle(title);

                    }

                    @Override
                    public void itemLink(String link) {
                        super.itemLink(link);
                        rssItem.setLink(link);

                    }

                    @Override
                    public void itemDescription(String description) {
                        super.itemDescription(description);
                        rssItem.setDescription(description);
                    }

                    @Override
                    public void itemEnd() {
                        super.itemEnd();
                        mItems.add(rssItem);
                    }

                    @Override
                    public void end() {
                        super.end();
                        handler.sendEmptyMessage(0x1111);
                    }

                    @Override
                    public void error(Throwable e) {
                        super.error(e);
                        e.printStackTrace();
                        Log.e(TAG, e.toString());
                        handler.sendEmptyMessage(0x1234);
                    }
                });
            }
        }).start();


    }
}
