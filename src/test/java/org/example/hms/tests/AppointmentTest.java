package org.example.hms.tests;

import org.example.hms.base.BaseTest;
import org.example.hms.pages.AppointmentPage;
import org.example.hms.pages.LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AppointmentTest extends BaseTest {

    private AppointmentPage appointmentPage;

    @BeforeMethod
    public void navigateToAppointments() {
        new LoginPage(driver, wait).login(ADMIN_USER, ADMIN_PASS);
        wait.until(ExpectedConditions.urlContains("index.html"));
        driver.get(BASE_URL + "/appointments.html");
        appointmentPage = new AppointmentPage(driver, wait);
    }

    @Test(description = "Default appointment (John Doe) is loaded in table")
    public void testDefaultAppointmentLoaded() {
        Assert.assertTrue(appointmentPage.isAppointmentInTable("John Doe"), "Default appointment should be visible");
    }

    @Test(description = "Add a new appointment")
    public void testAddAppointment() {
        appointmentPage.clickAddAppointment();
        appointmentPage.fillAppointmentForm("Jane Smith", "Dr. Brown", "Neurology",
                "2025-08-15", "14:00", "Follow-up");
        appointmentPage.saveAppointment();
        Assert.assertTrue(appointmentPage.isAppointmentInTable("Jane Smith"), "New appointment should appear in table");
    }

    @Test(description = "Filter appointments by department")
    public void testFilterByDepartment() {
        appointmentPage.filterByDepartment("Cardiology");
        Assert.assertTrue(appointmentPage.isAppointmentInTable("John Doe"), "Cardiology appointment should be visible");
    }

    @Test(description = "Filter appointments by status")
    public void testFilterByStatus() {
        appointmentPage.filterByStatus("Scheduled");
        Assert.assertTrue(appointmentPage.isAppointmentInTable("John Doe"), "Scheduled appointment should be visible");
    }
}
