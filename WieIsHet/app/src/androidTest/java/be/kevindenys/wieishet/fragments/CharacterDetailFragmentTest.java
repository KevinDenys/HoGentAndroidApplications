package be.kevindenys.wieishet.fragments;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import be.kevindenys.wieishet.R;
import be.kevindenys.wieishet.activity.App;
import be.kevindenys.wieishet.activity.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Created by Kevin on 31/12/2017.
 */
public class CharacterDetailFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mMainActivity = null;

    @Before
    public void setUp() throws Exception {
        mMainActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void mainActivityTestLaunch(){
        //kijken of MainActivity start
        FrameLayout llContain = (FrameLayout) mMainActivity.findViewById(R.id.flContainer);
        assertNotNull(llContain);
    }

    @Test
    public void fragmentLaunch(){
        //Kijken of de fragment ingeladen word, aan de hand van de recyclerview (enige component dat de fragment heeft)
        FrameLayout llContain = (FrameLayout) mMainActivity.findViewById(R.id.flContainer);
        CharacterMenuFragment fragment = new CharacterMenuFragment();
        fragment.setApp(mMainActivity.getApp());
        mMainActivity.getSupportFragmentManager().beginTransaction().add(llContain.getId(),fragment).commit();
        onView(withId(R.id.recyclerview)).check(ViewAssertions.matches(isDisplayed()));
    }
    @Test
    public void dataTest(){
        //Kijken of er data is
        FrameLayout llContain = (FrameLayout) mMainActivity.findViewById(R.id.flContainer);
        CharacterMenuFragment fragment = new CharacterMenuFragment();
        fragment.setApp(mMainActivity.getApp());
        mMainActivity.getSupportFragmentManager().beginTransaction().add(llContain.getId(),fragment).commit();
        onData(is(instanceOf(Character.class)));
    }
    @After
    public void tearDown() throws Exception {
        mMainActivity = null;

    }

}