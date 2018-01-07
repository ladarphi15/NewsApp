package at.fh_joanneum.newsly.news4u;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;

import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;

import java.util.ArrayList;
import java.util.Random;

public class StartScreenActivity extends AppCompatActivity {
    public final static String APP_PATH_SD_CARD = "/DesiredSubfolderName/";
    public final static String APP_THUMBNAIL_PATH_SD_CARD = "thumbnails";

    ArrayList<Bitmap> bitmapArray;

    ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
    File testfile;
    FileOutputStream fileoutputstream;

    private static final String[] URLS = {
            "https://images.techhive.com/images/article/2016/11/03_shipping_malware-100694090-large.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        bitmapArray = new ArrayList<Bitmap>();
        ViewHelper.formatAppHeader(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        // new loadImg().execute();


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
            for(int i =0; i< 1000; i++) {
                saveImageToExternalStorage(getBitmapFromURL(URLS[0]));
            }
            return null;

        }

        @Override
        protected void onPostExecute(Bitmap result) {

        }
}}