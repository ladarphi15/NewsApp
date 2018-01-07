package at.fh_joanneum.newsly.news4u;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Angelina on 25.12.2017.
 *
 */

public class WidgetProvider extends AppWidgetProvider {

    final String WIDGETACTION_NEXT = "next_action";
    final String WIDGETACTION_STANDARD ="https://derstandard.at/";
    final String WIDGETACTION_APA ="https://www.ots.at/";
    final String WIDGETACTION_KURIER ="https://kurier.at/";
    final String WIDGETACTION_PRESSE ="https://diepresse.com/";
    final String WIDGETACTION_SALZBURG ="http://syrian-mirror.net:80";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
       RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent openIntent = new Intent(context, StartScreenActivity.class);

        PendingIntent openPpendingIntent = PendingIntent.getActivity(context,0,openIntent,0);

        remoteViews.setOnClickPendingIntent(R.id.button_open, openPpendingIntent);

        remoteViews.setOnClickPendingIntent(R.id.imageButton_Standard, onClickPendingInten(context, WIDGETACTION_STANDARD));
        remoteViews.setOnClickPendingIntent(R.id.imageButton_salzburgnachrichten, onClickPendingInten(context, WIDGETACTION_SALZBURG));
        remoteViews.setOnClickPendingIntent(R.id.imageButton_kurier, onClickPendingInten(context, WIDGETACTION_KURIER));
        remoteViews.setOnClickPendingIntent(R.id.imageButton_diePresse, onClickPendingInten(context, WIDGETACTION_PRESSE));
        remoteViews.setOnClickPendingIntent(R.id.imageButton_ots_apa, onClickPendingInten(context, WIDGETACTION_APA));





        //At the end UPDATE widget
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);



        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
       //Reinsetzen von der view von nachrichten remoteViews.set
        if(intent.getAction().toString().equals(WIDGETACTION_NEXT))
        {
            int color = Color.argb(255, new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
            remoteViews.setInt(R.id.widget_layout, "setBackgroundColor", color);
            updateWidgetNow(context,remoteViews);
        }

        //open Medien in Browser
        if(intent.getAction().toString().equals(WIDGETACTION_STANDARD))
        {
            Intent intent_web = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(WIDGETACTION_STANDARD));
            context.startActivity(intent_web);
        }
        if(intent.getAction().toString().equals(WIDGETACTION_APA))
        {
            Intent intent_web = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(WIDGETACTION_APA));
            context.startActivity(intent_web);
        }
        if(intent.getAction().toString().equals(WIDGETACTION_KURIER))
        {
            Intent intent_web = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(WIDGETACTION_KURIER));
            context.startActivity(intent_web);
        }
        if(intent.getAction().toString().equals(WIDGETACTION_SALZBURG))
        {
            Intent intent_web = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(WIDGETACTION_SALZBURG));
            Log.i("Cookie"," Nacht STARTACTIVITY Vor getCookie");
            getCookie("m.facebook.at", "CookieName");
            Log.i("Cookie","Nach getCookie");
            context.startActivity(intent_web);
            Log.i("Cookie"," Nacht STARTACTIVITY Vor getCookie");
            getCookie("wwwm.facebook.com", "CookieName");
            Log.i("Cookie","Nach getCookie");
        }
        if(intent.getAction().toString().equals(WIDGETACTION_PRESSE))
        {
            Intent intent_web = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(WIDGETACTION_PRESSE));
            context.startActivity(intent_web);
        }


        super.onReceive(context, intent);
    }

    public void updateWidgetNow (Context context, RemoteViews remoteViews){
        ComponentName widgetComponents = new ComponentName(context, WidgetProvider.class);
        AppWidgetManager.getInstance(context).updateAppWidget(widgetComponents, remoteViews);

    }

    public  PendingIntent onClickPendingInten(Context context, String stringAction)
    {
        Intent onClickIntent= new Intent(context, WidgetProvider.class);
        onClickIntent.setAction(stringAction);

        return PendingIntent.getBroadcast(context,0,onClickIntent,0);
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
        Log.i("Cookie","Manager erstellt");
        String cookies = cookieManager.getCookie(siteName);
        Log.i("Cookie"," getCookie with sitename");
        if(cookies != null){
            Log.i("Cookie","cookies not null");
            String[] temp=cookies.split(";");
            for (String ar1 : temp ){
                if(ar1.contains(CookieName)){
                    String[] temp1=ar1.split("=");
                    CookieValue = temp1[1];
                    Log.i("Cookie","found cookie "+CookieValue );


                }
            }
        }
        return CookieValue;
    }


}
