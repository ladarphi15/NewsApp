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
    final String WIDGETACTION_SALZBURG ="https://www.sn.at/";


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
            context.startActivity(intent_web);
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


}
