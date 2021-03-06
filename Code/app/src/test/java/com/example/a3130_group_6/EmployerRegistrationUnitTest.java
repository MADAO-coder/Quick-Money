package com.example.a3130_group_6;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmployerRegistrationUnitTest {
    registrationForEmployers regEmployer = new registrationForEmployers();

    @Test
    public void passwordValidation_test(){
        regEmployer.isPasswordMatched("123456789", "123456789");
        assertTrue( regEmployer.isPasswordMatched("123456789", "123456789"));
        assertFalse(regEmployer.isPasswordMatched("123456789", "12345678"));
    }
}
