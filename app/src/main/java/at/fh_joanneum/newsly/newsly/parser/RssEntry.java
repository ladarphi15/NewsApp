package at.fh_joanneum.newsly.newsly.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by aneuh on 29.04.2017.
 */

public class RssEntry {
    private String title;
    private String author;
    private String link;
    private String description;
    private Date pubDate;
    private String ressort;
    private String source;

    public RssEntry(String title, String author, String link, String description, Date pubDate, String ressort, String source) {
        this.title = title;
        this.author = author;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.ressort = ressort;
        this.source = source;
    }


    @Override
    public String toString() {
        return "RssEntry{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", pubDate=" + pubDate +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRessort() {
        return ressort;
    }

    public void setRessort(String ressort) {
        this.ressort = ressort;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFormattedDateAndAuthor() {
        final DateFormat sdf = DateFormat.getDateTimeInstance();

        if (getPubDate() != null) {
            if(getAuthor() == null || getAuthor().equals("null") || getAuthor().equals(""))
            {
                return sdf.format(getPubDate());
            }
            return sdf.format(getPubDate()) + ", " + getAuthor();
        }

        return getAuthor();
    }

}
