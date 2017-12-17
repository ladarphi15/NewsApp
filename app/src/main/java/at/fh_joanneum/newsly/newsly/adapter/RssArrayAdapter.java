package at.fh_joanneum.newsly.newsly.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import at.fh_joanneum.newsly.newsly.R;
import at.fh_joanneum.newsly.newsly.parser.RssEntry;

/**
 * Created by aneuh on 06.05.2017.
 */

public class RssArrayAdapter extends ArrayAdapter<RssEntry> {

    public RssArrayAdapter(@NonNull Context context, @NonNull List<RssEntry> objects) {
        super(context, R.layout.rss_row_item, objects);
    }

    private static class ViewHolder {
        TextView txtTitle;
        TextView txtDateAndAuthor;
        TextView txtDescription;
        TextView txtRessortAndSource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        LayoutInflater inflater = LayoutInflater.from(getContext());

        RssEntry entry = getItem(position);

        final View result;
        ViewHolder viewHolder; //
        viewHolder = new ViewHolder();

        convertView = inflater.inflate(R.layout.rss_row_item, parent, false);

        viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.itemTitle);
        viewHolder.txtDateAndAuthor = (TextView) convertView.findViewById(R.id.itemDateAndAuthor);
        viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.itemDescription);
        viewHolder.txtRessortAndSource = (TextView) convertView.findViewById(R.id.itemRessort);

        convertView.setTag(viewHolder);

        viewHolder.txtRessortAndSource.setText((entry.getRessort() + " | " + entry.getSource()).toUpperCase());

        viewHolder.txtTitle.setText(entry.getTitle());
        viewHolder.txtDateAndAuthor.setText(entry.getFormattedDateAndAuthor());

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String htmlText = formatDescription(Html.fromHtml(entry.getDescription(), Html.FROM_HTML_MODE_COMPACT, Images, null).toString());
            viewHolder.txtDescription.setText(stripHtml(htmlText));
        } else {
            String htmlText = formatDescription(Html.fromHtml(entry.getDescription(), Images, null).toString());
            viewHolder.txtDescription.setText(stripHtml(htmlText));
        }

        // Return the completed view to render on screen
        return convertView;
    }

    private String formatDescription(String s) {
        return s.substring(0, s.length() > 250 ? 250 : s.length()) + " ...";
    }

    private Html.ImageGetter Images = new Html.ImageGetter() {

        public Drawable getDrawable(String source) {

            Drawable d = new Drawable() {
                @Override
                public void draw(@NonNull Canvas canvas) {

                }

                @Override
                public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

                }

                @Override
                public void setColorFilter(@Nullable ColorFilter colorFilter) {

                }

                @Override
                public int getOpacity() {
                    return PixelFormat.UNKNOWN;
                }
            };
            return d;
        }
    };

    public String stripHtml(String s) {
        return s.replace('\n', (char) 32)
                .replace((char) 160, (char) 32).replace((char) 65532, (char) 32).trim();
    }
}
