package at.fh_joanneum.newsly.news4u;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.v7.widget.ShareActionProvider;

import at.fh_joanneum.newsly.news4u.parser.RssEntry;

/**
 * Created by aneuh on 06.05.2017.
 */
//
public class NewsWebViewActivity extends AppCompatActivity {
    public static RssEntry CURRENT_RSS_ENTRY = null;
    private ShareActionProvider mShareActionProvider;
    WebView webView;



    String shareActionString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_web);
        webView = (WebView) findViewById(R.id.rssWebView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(CURRENT_RSS_ENTRY.getLink());


        ViewHelper.formatAppHeader(this);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_start_screen, menu);

        MenuItem shareMenuItem = menu.findItem(R.id.menu_item_share);

        if (shareMenuItem != null) {
            ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareMenuItem);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "http://www.google.com");
            shareActionProvider.setShareIntent(shareIntent);
            //shareMenuItem.setIcon(R.drawable.ic_action_share);
        }

        return super.onCreateOptionsMenu(menu);
        }
        */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /** Inflating the current activity's menu with res/menu/items.xml */
        getMenuInflater().inflate(R.menu.menu_start_screen, menu);

        return super.onCreateOptionsMenu(menu);

    }

    /** Returns a share intent */
    private Intent getDefaultShareIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
        intent.putExtra(Intent.EXTRA_TEXT,"Extra Text");
        return intent;
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                shareURL();
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareURL() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
        startActivity(Intent.createChooser(shareIntent, "Share This!"));
    }
}
