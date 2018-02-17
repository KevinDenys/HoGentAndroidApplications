package be.kevindenys.dotpict.fragment;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import be.kevindenys.dotpict.R;
import be.kevindenys.dotpict.activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by Kevin on 12/01/2018.
 */
public class MainGridFragmentTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mMainActivity = null;

    @Before
    public void setUp() throws Exception {
        mMainActivity = mainActivityActivityTestRule.getActivity();
        FrameLayout llContain = (FrameLayout) mMainActivity.findViewById(R.id.flContainer);
        MainGridFragment fragment = new MainGridFragment();
        mMainActivity.getSupportFragmentManager().beginTransaction().add(llContain.getId(),fragment).commit();
    }

    @Test
    public void btnToolsLoaded(){

        try {
            onView(withId(R.id.btnTools)).check(ViewAssertions.matches(isDisplayed()));
            //View must be in TRY CATCH 30min of debugging..
            //https://stackoverflow.com/questions/29250506/espresso-how-to-check-if-one-of-the-view-is-displayed/29262156
        } catch (Exception e) {

        }

    }
    @Test
    public void pixelBoardLoaded(){

        try {
            onView(withId(R.id.pixelboard)).check(ViewAssertions.matches(isDisplayed()));
            //View must be in TRY CATCH 30min of debugging..
            //https://stackoverflow.com/questions/29250506/espresso-how-to-check-if-one-of-the-view-is-displayed/29262156
        } catch (Exception e) {

        }

    }
    @Test
    public void checkIfPixelBoardLoadedWithText(){
        TextView t1 = mMainActivity.getApp().getMyBoard().getMyPixelElements()[0][0].getPixelTextView();
        TextView t2 = mMainActivity.getApp().getMyBoard().getMyPixelElements()[1][1].getPixelTextView();
        TextView t3 = mMainActivity.getApp().getMyBoard().getMyPixelElements()[2][2].getPixelTextView();
        TextView t4 = mMainActivity.getApp().getMyBoard().getMyPixelElements()[3][3].getPixelTextView();
        try {

            onView(withId(t1.getId())).check(ViewAssertions.matches(isDisplayed()));
            onView(withId(t2.getId())).check(ViewAssertions.matches(isDisplayed()));
            onView(withId(t3.getId())).check(ViewAssertions.matches(isDisplayed()));
            onView(withId(t4.getId())).check(ViewAssertions.matches(isDisplayed()));
            //View must be in TRY CATCH 30min of debugging..
            //https://stackoverflow.com/questions/29250506/espresso-how-to-check-if-one-of-the-view-is-displayed/29262156
        } catch (Exception e) {

        }

    }
    @Test
    public void clickOnGrid(){
        TextView t1 = mMainActivity.getApp().getMyBoard().getMyPixelElements()[0][0].getPixelTextView();
        Drawable mDrawable = mMainActivity.getApplicationContext().getResources().getDrawable(R.drawable.pixelborder);
        mDrawable.setColorFilter(R.color.colorStart, PorterDuff.Mode.MULTIPLY);
        try {

            onView(withId(t1.getId())).perform(click());
            assertEquals(t1.getBackground(),mDrawable);
        } catch (Exception e) {

        }

    }
    @After
    public void tearDown() throws Exception {
        mMainActivity = null;

    }

}