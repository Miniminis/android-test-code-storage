package com.example.androidtestproject


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun unitFragmentTest() {
        val appCompatButton = onView(
            allOf(
                withId(R.id.btn_to_unittest), withText("Go To UnitTest"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_nav_host),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_nav_host),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("mhson@crepass.com"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withText("mhson@crepass.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_nav_host),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(click())

        val appCompatEditText3 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_nav_host),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("2q"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_nav_host),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(replaceText("2www"), closeSoftKeyboard())

        val appCompatEditText5 = onView(
            allOf(
                withText("2www"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_nav_host),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(pressImeActionButton())

        val editText = onView(
            allOf(
                withText("mhson@crepass.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_nav_host),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        editText.check(matches(withText("mhson@crepass.com")))

        val editText2 = onView(
            allOf(
                withText("••"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_nav_host),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        editText2.check(matches(withText("••")))

        val editText3 = onView(
            allOf(
                withText("••••"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_nav_host),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        editText3.check(matches(withText("••••")))

        val editText4 = onView(
            allOf(
                withText("••••"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_nav_host),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        editText4.check(matches(withText("••••")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
