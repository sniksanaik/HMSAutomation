package org.example.hms.tests;

import org.example.hms.base.BaseTest;
import org.example.hms.pages.LoginPage;
import org.example.hms.pages.PatientPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PatientTest extends BaseTest {

    private PatientPage patientPage;

    @BeforeMethod
    public void navigateToPatients() {
        new LoginPage(driver, wait).login(ADMIN_USER, ADMIN_PASS);
        wait.until(ExpectedConditions.urlContains("index.html"));
        driver.get(BASE_URL + "/patients.html");
        patientPage = new PatientPage(driver, wait);
    }

    @Test(description = "Add a new patient successfully")
    public void testAddPatient() {
        patientPage.clickAddPatient();
        patientPage.fillPatientForm("Alice Johnson", "30", "Female", "A+", "Cardiology");
        patientPage.savePatient();
        Assert.assertTrue(patientPage.isPatientInTable("Alice Johnson"), "New patient should appear in table");
    }

    @Test(description = "Search filters patient table")
    public void testSearchPatient() {
        patientPage.searchPatient("John");
        Assert.assertTrue(patientPage.isPatientInTable("John Doe"), "John Doe should be visible after search");
    }

    @Test(description = "Add patient with insurance and emergency flags")
    public void testAddPatientWithFlags() {
        patientPage.clickAddPatient();
        patientPage.fillPatientForm("Bob Emergency", "45", "Male", "B+", "Emergency");
        patientPage.setInsurance(true);
        patientPage.setEmergency(true);
        patientPage.savePatient();
        Assert.assertTrue(patientPage.isPatientInTable("Bob Emergency"));
    }

    @Test(description = "Delete a patient")
    public void testDeletePatient() {
        // First add a patient to delete
        patientPage.clickAddPatient();
        patientPage.fillPatientForm("Delete Me", "25", "Male", "O-", "General");
        patientPage.savePatient();

        patientPage.clickDeleteForPatient("Delete Me");
        patientPage.confirmDelete();
        Assert.assertFalse(patientPage.isPatientInTable("Delete Me"), "Deleted patient should not appear in table");
    }
}
