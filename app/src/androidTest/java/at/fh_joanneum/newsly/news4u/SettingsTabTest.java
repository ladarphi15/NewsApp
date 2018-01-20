package at.fh_joanneum.newsly.news4u;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.fh_joanneum.newsly.news4u.db.entity.RessortSetting;
import at.fh_joanneum.newsly.news4u.db.entity.Source;
import at.fh_joanneum.newsly.news4u.db.entity.SourceSetting;
import at.fh_joanneum.newsly.news4u.parser.RssEntry;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SettingsTabTest {

    @Rule
    public ActivityTestRule<StartScreenActivity> mActivityRule =
        new ActivityTestRule<>(StartScreenActivity.class);

    @Test
    public void testRessortsSettings() throws Exception {
        onView(withId(android.R.id.list)).perform(swipeLeft());
        onView(withId(R.id.ressorts)).perform(click());

        onData(instanceOf(RessortSetting.class))
                .atPosition(1)
                .perform(click());
    }

    @Test
    public void testSourcesSettings() throws Exception {
        onView(withId(android.R.id.list)).perform(swipeLeft());
        onView(withId(R.id.sources)).perform(click());

        onData(instanceOf(SourceSetting.class))
                .atPosition(1)
                .perform(click());
    }
}
