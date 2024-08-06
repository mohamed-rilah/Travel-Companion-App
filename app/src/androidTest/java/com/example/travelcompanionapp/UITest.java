package com.example.travelcompanionapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void homepageTest() {
        onView(withId(R.id.welcomeMessageTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.currentLocationText)).check(matches(isDisplayed()));
        onView(withId(R.id.newLocationText)).check(matches(isDisplayed()));
        onView(withId(R.id.userInstruction)).check(matches(isDisplayed()));
        onView(withId(R.id.button_setCurrentLocation)).check(matches(isDisplayed()));
        onView(withId(R.id.button_selectNewLocation)).check(matches(isDisplayed()));
    }

    @Test
    public void selectNewLocationTest() {
        onView(withId(R.id.button_selectNewLocation)).perform(click());
        onView(withId(R.id.selectingNewLocation)).check(matches(isDisplayed()));
        onView(withId(R.id.button_backFromSetLocation)).check(matches(isDisplayed()));
    }

    @Test
    public void weatherPageTest() {
        onView(withId(R.id.currentLocationText)).perform(click());
        onView(withId(R.id.weatherFragment)).perform(click());
        onView(withId(R.id.currentLocationText)).check(matches(isDisplayed()));
    }

    @Test
    public void placesPageTest() {
        onView(withId(R.id.currentLocationText)).perform(click());
        onView(withId(R.id.placesFragment)).perform(click());
        onView(withId(R.id.searchIcon)).check(matches(isDisplayed()));
        onView(withId(R.id.placesType)).check(matches(isDisplayed()));
        onView(withId(R.id.button_fetchPlacesFromAPI)).check(matches(isDisplayed()));
    }

    @Test
    public void itineraryPageTest() {
        onView(withId(R.id.itineraryFragment)).perform(click());
        onView(withId(R.id.itineraryViewMessage)).check(matches(isDisplayed()));
        onView(withId(R.id.searchIcon)).check(matches(isDisplayed()));
        onView(withId(R.id.searchTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.button_createItinerary)).check(matches(isDisplayed()));
    }

    @Test
    public void itineraryCreatePageTest() {
        onView(withId(R.id.itineraryFragment)).perform(click());
        onView(withId(R.id.button_createItinerary)).perform(click());
        onView(withId(R.id.itineraryCreateMessage)).check(matches(isDisplayed()));
        onView(withId(R.id.itineraryForm)).check(matches(isDisplayed()));
        onView(withId(R.id.button_backFromCreateItinerary)).check(matches(isDisplayed()));
    }
}