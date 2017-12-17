package at.fh_joanneum.newsly.newsly;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by aneuh on 29.04.2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int tabs;

    public PagerAdapter(FragmentManager fm, int tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabNews tab1 = new TabNews();
                return tab1;
            case 1:
                TabSettings tab2 = new TabSettings();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabs;
    }

}
