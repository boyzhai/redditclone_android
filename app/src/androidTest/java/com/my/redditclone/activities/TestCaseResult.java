package com.my.redditclone.activities;


import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.my.redditclone.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.SecureRandom;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestCaseResult {
    String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER ;
    SecureRandom random = new SecureRandom();
    int LAST_POSITION = 100;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    public  String generateRandomString(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {

            // 0-62 (exclusive), random returns 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            // debug
            System.out.format("%d\t:\t%c%n", rndCharAt, rndChar);

            sb.append(rndChar);

        }

        return sb.toString();

    }

    @Test
    public void mainActivityTest2() throws InterruptedException {


        for(int i = 0 ; i < 10 ; i++) {

            ViewInteraction materialButton = onView(
                    allOf(withId(R.id.btn_create_post), withText("Create Post"),
                            childAtPosition(
                                    childAtPosition(
                                            withClassName(is("android.widget.ScrollView")),
                                            0),
                                    0)));
            materialButton.perform(scrollTo(), click());

            ViewInteraction textInputEditText = onView(
                    allOf(withId(R.id.edtTitle),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.tvTitleLayout),
                                            0),
                                    0),
                            isDisplayed()));
            textInputEditText.perform(replaceText(generateRandomString(10)), closeSoftKeyboard());

            ViewInteraction textInputEditText2 = onView(
                    allOf(withId(R.id.edtDescription),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.tvDescriptionLayout),
                                            0),
                                    0),
                            isDisplayed()));
            textInputEditText2.perform(replaceText(generateRandomString(10)), closeSoftKeyboard());

            ViewInteraction materialButton2 = onView(
                    allOf(withId(R.id.btnSubmit), withText("Submit"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.content_frame),
                                            0),
                                    2),
                            isDisplayed()));
            materialButton2.perform(click());
            
            ViewInteraction materialButton3 = onView(
                    allOf(withId(android.R.id.button1), withText("Ok"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.buttonPanel),
                                            0),
                                    3)));
            materialButton3.perform(scrollTo(), click());


        }

        onView(withRecyclerView(R.id.rv_topic_list)
                .atPositionOnView(0, R.id.cl_layout_card))
                .perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                1)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        onView(withRecyclerView(R.id.rv_topic_list)
                .atPositionOnView(1, R.id.cl_layout_card))
                .perform(click());

        appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                1)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        for(int i = 0 ; i < 20 ;i++){
            onView(withRecyclerView(R.id.rv_topic_list)
                    .atPositionOnView(0, R.id.btn_upvote))
                    .perform(click());
        }

        for(int i = 0 ; i < 22 ;i++){
            onView(withRecyclerView(R.id.rv_topic_list)
                    .atPositionOnView(1, R.id.btn_upvote))
                    .perform(click());
        }


        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.refresh), withContentDescription("Refresh"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        Thread.sleep(3000);

        for(int i = 0 ; i < 10 ;i++){
            onView(withRecyclerView(R.id.rv_topic_list)
                    .atPositionOnView(0, R.id.btn_downvote))
                    .perform(click());
        }

        //scroll recycleview to correct position for testing
        onView(withId(R.id.rv_topic_list))
                .perform(RecyclerViewActions.scrollToPosition((5)));

        for(int i = 0 ; i < 20 ;i++){
            onView(withRecyclerView(R.id.rv_topic_list)
                    .atPositionOnView(5, R.id.btn_upvote))
                    .perform(click());
        }


        for(int i = 0 ; i < 20 ;i++){
            onView(withRecyclerView(R.id.rv_topic_list)
                    .atPositionOnView(5, R.id.btn_downvote))
                    .perform(click());
        }
        actionMenuItemView = onView(
                allOf(withId(R.id.refresh), withContentDescription("Refresh"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());


    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }


    public static ViewAction clickChildViewWithId(int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();

            }
        };
    }


    public  RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    public class RecyclerViewMatcher {
        final int mRecyclerViewId;

        public RecyclerViewMatcher(int recyclerViewId) {
            this.mRecyclerViewId = recyclerViewId;
        }

        public Matcher<View> atPosition(final int position) {
            return atPositionOnView(position, -1);
        }

        public Matcher<View> atPositionOnView(final int position, final int targetViewId) {

            return new TypeSafeMatcher<View>() {
                Resources resources = null;
                View childView;

                public void describeTo(Description description) {
                    int id = targetViewId == -1 ? mRecyclerViewId : targetViewId;
                    String idDescription = Integer.toString(id);
                    if (this.resources != null) {
                        try {
                            idDescription = this.resources.getResourceName(id);
                        } catch (Resources.NotFoundException var4) {
                            idDescription = String.format("%s (resource name not found)", id);
                        }
                    }

                    description.appendText("with id: " + idDescription);
                }

                public boolean matchesSafely(View view) {

                    this.resources = view.getResources();

                    if (childView == null) {
                        RecyclerView recyclerView =
                                (RecyclerView) view.getRootView().findViewById(mRecyclerViewId);
                        if (recyclerView != null) {

                            childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                        }
                        else {
                            return false;
                        }
                    }

                    if (targetViewId == -1) {
                        return view == childView;
                    } else {
                        View targetView = childView.findViewById(targetViewId);
                        return view == targetView;
                    }

                }
            };
        }
    }

}
