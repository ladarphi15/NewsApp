package at.fh_joanneum.newsly.news4u.parser;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import at.fh_joanneum.newsly.news4u.db.entity.LinkSourceRessort;

import static org.junit.Assert.*;

/**
 * Created by edi on 08.01.18.
 */
public class RssParserTest {
    RssParser parser;

    @Test
    public void testParse() throws IOException, XmlPullParserException {
        // GIVEN
        DownloadRssTask downloadRssTask = new DownloadRssTask(null);
        parser = new RssParser();
        final String link = "http://derStandard.at/?page=rss&ressort=Sport";
        final InputStream stream = downloadRssTask.downloadUrl(link);
        LinkSourceRessort linkSourceRessort = new LinkSourceRessort("http://derStandard.at/?page=rss&ressort=Sport", "derStandard.at", "Sport");

        // WHEN
        final List<RssEntry> parsed = parser.parse(stream, linkSourceRessort);

        // THEN
        Assert.assertNotNull(parsed);
        for (RssEntry entry : parsed) {
            Assert.assertEquals("Sport", entry.getSource());
        }
    }
}