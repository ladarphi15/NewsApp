package at.fh_joanneum.newsly.news4u;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.v7.widget.ShareActionProvider;
import android.widget.Toast;

import at.fh_joanneum.newsly.news4u.parser.RssEntry;

/**
 * Created by aneuh on 06.05.2017.
 */
//
public class NewsWebViewActivity extends AppCompatActivity {
    public static RssEntry CURRENT_RSS_ENTRY = null;
    private ShareActionProvider mShareAction;
    WebView webView;



    String shareActionString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_web);
        webView = (WebView) findViewById(R.id.rssWebView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(CURRENT_RSS_ENTRY.getLink());
        Log.i("Cookie","Vor getCookie");
        getCookie("m.facebook.com", "CookieName");
        Log.i("Cookie","Nach getCookie");



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
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_start_screen, menu);



        MenuItem item = menu.findItem(R.id.share);

        ShareActionProvider miShare = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        shareIntent.setType("text/plain");



        // get reference to WebView

        WebView wvArticle = (WebView) findViewById(R.id.rssWebView);

        // pass in the URL currently being used by the WebView

        shareIntent.putExtra(Intent.EXTRA_TEXT, wvArticle.getUrl());
        Log.i("Share", "article Url = "+ wvArticle.getUrl());



        miShare.setShareIntent(shareIntent);

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

    @SuppressWarnings("deprecation")
    public void clearCookies(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else
        {
            CookieSyncManager cookieSyncMngr= CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager= CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }
    public String getCookie(String siteName,String CookieName){
        String CookieValue = null;

        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(siteName);
        if(cookies != null){
            String[] temp=cookies.split(";");
            for (String ar1 : temp ){
                if(ar1.contains(CookieName)){
                    String[] temp1=ar1.split("=");
                    CookieValue = temp1[1];
                    Toast.makeText(this, "Cookie found: its: ", Toast.LENGTH_LONG).show();

                }
            }
        }
        return CookieValue;
    }
}
