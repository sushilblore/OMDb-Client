package com.android.sushil.omdbclient;

import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import com.android.sushil.omdbclient.data.model.Search;
import com.android.sushil.omdbclient.ui.main.MoviesList.MoviesListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SearchViewTest {

    private static final String TEST_TITLE = "Seven Samurai";

    @Rule
    public ActivityTestRule<MoviesListActivity> mActivityRule =
            new ActivityTestRule<>(MoviesListActivity.class);

    @Test
    public void testSearchToolbar() throws Exception {

        Instrumentation inst = InstrumentationRegistry.getInstrumentation();
        onView(withId(R.id.action_search)).perform(click());
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText("Seven Samurai"));
        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_SEARCH);
        //onData(withId(R.id.movie_title)).check(matches(isDisplayed()));
    }
}
