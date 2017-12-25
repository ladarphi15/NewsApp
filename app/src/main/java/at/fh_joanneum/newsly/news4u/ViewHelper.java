package at.fh_joanneum.newsly.news4u;

import android.app.Activity;
import android.graphics.Typeface;
import android.widget.TextView;

public final class ViewHelper {

    private ViewHelper() {
    }

    public static void formatAppHeader(Activity activity) {
        TextView text = (TextView) activity.findViewById(R.id.textHeader);
        Typeface type = Typeface.createFromAsset(activity.getAssets(), "fonts/Outwrite.ttf");
        text.setTypeface(type);
        text.setTextSize(52);
    }
}
