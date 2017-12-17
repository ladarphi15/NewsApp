package at.fh_joanneum.newsly.newsly;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import at.fh_joanneum.newsly.newsly.parser.RssEntry;

/**
 * Created by aneuh on 06.05.2017.
 */

public class NewsWebViewActivity extends AppCompatActivity {
    public static RssEntry CURRENT_RSS_ENTRY = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_web);
        WebView webView = (WebView) findViewById(R.id.rssWebView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(CURRENT_RSS_ENTRY.getLink());

        ViewHelper.formatAppHeader(this);
    }
}
