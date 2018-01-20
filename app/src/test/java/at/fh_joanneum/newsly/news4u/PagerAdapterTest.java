package at.fh_joanneum.newsly.news4u;

import android.support.v4.app.Fragment;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PagerAdapterTest {
    private PagerAdapter pagerAdapter;

    @Before
    public void setup() {
        pagerAdapter = new PagerAdapter(new MockFragmentManager(), 4);
    }

    @Test
    public void testGetItem() {
        final Fragment item1 = pagerAdapter.getItem(0);
        final Fragment item2 = pagerAdapter.getItem(1);
        final Fragment item3 = pagerAdapter.getItem(2);

        Assert.assertEquals(4, pagerAdapter.getCount());
        Assert.assertTrue(item1 instanceof TabNews);
        Assert.assertTrue(item2 instanceof TabSettings);
        Assert.assertNull(item3);
    }

}