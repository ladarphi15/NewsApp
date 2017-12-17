package at.fh_joanneum.newsly.newsly.parser;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import at.fh_joanneum.newsly.newsly.db.entity.LinkSourceRessort;

/**
 * Created by aneuh on 29.04.2017.
 */

public class DownloadRssTask extends AsyncTask<List<LinkSourceRessort>, Void, List<RssEntry>> {

    public interface AsyncResponse {
        void processFinish(List<RssEntry> output);
    }

    public AsyncResponse delegate = null;

    public DownloadRssTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected List<RssEntry> doInBackground(List<LinkSourceRessort>... params) {
        return loadXmlFromNetwork(params[0]);
    }

    @Override
    protected void onPostExecute(List<RssEntry> result) {
        delegate.processFinish(result);
    }

    public List<RssEntry> loadXmlFromNetwork(List<LinkSourceRessort> linkSourceRessorts) {
        List<RssEntry> allEntries = new ArrayList<>();

        for (LinkSourceRessort linkSourceRessort:
             linkSourceRessorts) {

            InputStream stream = null;

            // Instantiate the parser
            RssParser rssParser = new RssParser();

            try {
                stream = downloadUrl(linkSourceRessort.getLink());
                List<RssEntry> entries = rssParser.parse(stream, linkSourceRessort);

                allEntries.addAll(entries);
                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } catch (XmlPullParserException | IOException e) {
                Log.e("RSS_PARSE", e.getMessage(), e);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        Collections.sort(allEntries, new Comparator<RssEntry>() {

            @Override
            public int compare(RssEntry o1, RssEntry o2) {

                return o2.getPubDate().compareTo(o1.getPubDate());
            }

        });

        return allEntries;
    }

    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }
}