package at.fh_joanneum.newsly.newsly.parser;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import at.fh_joanneum.newsly.newsly.db.entity.LinkSourceRessort;

/**
 * Created by aneuh on 29.04.2017.
 */

public class RssParser {
    private static final String ns = null;

    private LinkSourceRessort _linkSourceRessort;
    public List parse(InputStream in, LinkSourceRessort linkSourceRessort) throws XmlPullParserException, IOException {
        _linkSourceRessort = linkSourceRessort;

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<RssEntry> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<RssEntry> entries = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "rss");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("channel")) {
                parser.require(XmlPullParser.START_TAG, ns, "channel");

                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }

                    String innerName = parser.getName();

                    if (innerName.equals("item")) {
                        entries.add(readEntry(parser));
                    } else {
                        skip(parser);
                    }
                }
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private RssEntry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");

        String title = null;
        String description = null;
        String link = null;
        Date pubDate = null;
        String author = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = readTag(parser, "title");
            } else if (name.equals("description")) {
                description = readTag(parser, "description");
            } else if (name.equals("link")) {
                link = readTag(parser, "link");
            } else if (name.equals("pubDate")) {
                pubDate = readDateTime(parser, "pubDate");
            } else if (name.equals("author")) {
                author = readTag(parser, "author");
            } else {
                skip(parser);
            }
        }
        return new RssEntry(title, author, link, description, pubDate, _linkSourceRessort.getRessort(), _linkSourceRessort.getSource());
    }

    private Date readDateTime(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        String dateTimeString = readTag(parser, tag);
        Date date = null;

        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);
        try {
            date = format.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    // Processes title tags in the feed.
    private String readTag(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return title;
    }
    
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
