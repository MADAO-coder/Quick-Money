package com.example.a3130_group_6;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeRegistrationUnitTest {
    registrationForEmployees regEmployee = new registrationForEmployees();

    @Test
    public void passwordValidation_test(){
        regEmployee.isPasswordMatched("123456789", "123456789");
        assertTrue( regEmployee.isPasswordMatched("123456789", "123456789"));
        assertFalse(regEmployee.isPasswordMatched("123456789", "12345678"));
    }
    @Test
    public void checkIfLocationEmpty(){
        assertTrue(regEmployee.isEmptyLocation(""));
        assertFalse(regEmployee.isEmptyLocation("Halifax"));
    }
}
