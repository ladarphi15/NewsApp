package at.fh_joanneum.newsly.news4u;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.fh_joanneum.newsly.news4u.parser.RssEntry;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.onView;

@RunWith(AndroidJUnit4.class)
public class NewsTabTest {

    @Rule
    public ActivityTestRule<StartScreenActivity> mActivityRule =
        new ActivityTestRule<>(StartScreenActivity.class);

    @Test
    public void testStartScreenOpenNewsTab() throws Exception {
        onData(instanceOf(RssEntry.class))
            .inAdapterView(allOf(withId(android.R.id.list), isDisplayed()))
            .atPosition(9)
            .check(matches(isDisplayed()));
    }

    @Test
    public void testClickNewsEntry() throws Exception {
        onData(instanceOf(RssEntry.class))
                .atPosition(1)
                .perform(click());
        onView(withId(R.id.rssWebView)).check(matches(isDisplayed()));
    }
}
