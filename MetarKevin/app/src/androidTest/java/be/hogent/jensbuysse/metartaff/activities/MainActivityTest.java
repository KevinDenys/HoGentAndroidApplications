package be.hogent.jensbuysse.metartaff.activities;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import be.hogent.jensbuysse.metartaff.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by Kevin on 16/01/2018.
 */
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mMainActivity = null;

    @Before
    public void setUp() throws Exception {
        mMainActivity = mainActivityActivityTestRule.getActivity();

    }

    @Test
    public void testRecyclerviewLoad(){
        RecyclerView recyl = (RecyclerView) mMainActivity.findViewById(R.id.airportlist);
        onView(withId(recyl.getId())).check(ViewAssertions.matches(isDisplayed()));
    }
    @After
    public void tearDown() throws Exception {
    }

}