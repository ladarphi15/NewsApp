package at.fh_joanneum.newsly.newsly.db.entity;

/**
 * Created by edi on 07/05/2017.
 */

public class LinkSourceRessort {
    private String link;
    private String source;
    private String ressort;

    public LinkSourceRessort(String link, String source, String ressort) {
        this.link = link;
        this.source = source;
        this.ressort = ressort;
    }

    public String getLink() {
        return link;
    }

    public String getSource() {
        return source;
    }

    public String getRessort() {
        return ressort;
    }
}
