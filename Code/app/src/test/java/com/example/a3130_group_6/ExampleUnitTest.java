package com.example.a3130_group_6;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    static add_listing addListing;

    @BeforeClass
    public static void setup() {
        addListing = new add_listing();
    }

    @Test
    public void checkIfTaskTitleEmpty() {
        assertTrue(addListing.isEmptyTaskTitle(""));
        assertFalse(addListing.isEmptyTaskTitle("Good Task Title"));
    }

    @Test
    public void checkIfTaskDescriptionEmpty() {
        assertTrue(addListing.isEmptyTaskDescription(""));
        assertFalse(addListing.isEmptyTaskDescription("Good Task Description"));
    }

    @Test
    public void checkIfUrgencyIsEmpty() {
        assertTrue(addListing.isEmptyUrgency(""));
        assertFalse(addListing.isEmptyUrgency("2"));
    }

    @Test
    public void checkIfUrgencyCorrectRange() {
        assertTrue(addListing.checkUrgencyRange("2"));
        assertFalse(addListing.checkUrgencyRange("6"));
        // add a check or a test for invalid input
    }

    @Test
    public void checkIfDateIsEmpty() {
        assertTrue(addListing.isEmptyDate(""));
        assertFalse(addListing.isEmptyDate("20/02/2020"));
    }

    @Test
    public void checkIfPayIsEmpty() {
        assertTrue(addListing.isPayEmpty(""));
        assertFalse(addListing.isPayEmpty("20"));
    }
}