package org.example.hms.tests;

import org.example.hms.base.BaseTest;
import org.example.hms.pages.DoctorPage;
import org.example.hms.pages.LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DoctorTest extends BaseTest {

    private DoctorPage doctorPage;

    @BeforeMethod
    public void setup() {
        new LoginPage(driver, wait).login(ADMIN_USER, ADMIN_PASS);
        wait.until(ExpectedConditions.urlContains("index.html"));
        driver.get(BASE_URL + "/doctors.html");
        doctorPage = new DoctorPage(driver, wait);
    }

    // ── Grid Load ────────────────────────────────────────────────────────────

    @Test(description = "All 6 seeded doctor cards are rendered on page load")
    public void testDefaultDoctorsLoaded() {
        Assert.assertEquals(doctorPage.getDoctorCardCount(), 6, "Should display 6 seeded doctors");
    }

    @Test(description = "Seeded doctors are visible in the grid by name")
    public void testSeededDoctorNamesVisible() {
        Assert.assertTrue(doctorPage.isDoctorCardVisible("Dr. Adams"),  "Dr. Adams should be visible");
        Assert.assertTrue(doctorPage.isDoctorCardVisible("Dr. Brown"),  "Dr. Brown should be visible");
        Assert.assertTrue(doctorPage.isDoctorCardVisible("Dr. Clark"),  "Dr. Clark should be visible");
        Assert.assertTrue(doctorPage.isDoctorCardVisible("Dr. Davis"),  "Dr. Davis should be visible");
        Assert.assertTrue(doctorPage.isDoctorCardVisible("Dr. Evans"),  "Dr. Evans should be visible");
        Assert.assertTrue(doctorPage.isDoctorCardVisible("Dr. Foster"), "Dr. Foster should be visible");
    }

    // ── Search ───────────────────────────────────────────────────────────────

    @Test(description = "Search by name filters the doctor grid")
    public void testSearchByName() {
        doctorPage.searchDoctor("Adams");
        Assert.assertEquals(doctorPage.getDoctorCardCount(), 1, "Only Dr. Adams should be visible");
        Assert.assertTrue(doctorPage.isDoctorCardVisible("Dr. Adams"));
    }

    @Test(description = "Search with no match shows empty grid")
    public void testSearchNoMatch() {
        doctorPage.searchDoctor("ZZZNOMATCH");
        Assert.assertEquals(doctorPage.getDoctorCardCount(), 0, "No cards should match unknown search");
    }

    @Test(description = "Clearing search restores all doctor cards")
    public void testClearSearch() {
        doctorPage.searchDoctor("Adams");
        doctorPage.clearFilters();
        Assert.assertEquals(doctorPage.getDoctorCardCount(), 6, "All 6 doctors should be visible after clear");
    }

    // ── Filters ──────────────────────────────────────────────────────────────

    @Test(description = "Filter by Cardiology specialty shows only Dr. Adams")
    public void testFilterBySpecialty() {
        doctorPage.filterBySpecialty("Cardiology");
        Assert.assertEquals(doctorPage.getDoctorCardCount(), 1);
        Assert.assertTrue(doctorPage.isDoctorCardVisible("Dr. Adams"));
    }

    @Test(description = "Filter by Available shows only available doctors")
    public void testFilterByAvailable() {
        doctorPage.filterByAvailability("true");
        int count = doctorPage.getDoctorCardCount();
        Assert.assertEquals(count, 4, "4 doctors should be available");
        Assert.assertFalse(doctorPage.isDoctorCardVisible("Dr. Clark"),  "Dr. Clark is unavailable");
        Assert.assertFalse(doctorPage.isDoctorCardVisible("Dr. Foster"), "Dr. Foster is unavailable");
    }

    @Test(description = "Filter by Unavailable shows only unavailable doctors")
    public void testFilterByUnavailable() {
        doctorPage.filterByAvailability("false");
        Assert.assertEquals(doctorPage.getDoctorCardCount(), 2, "2 doctors should be unavailable");
        Assert.assertTrue(doctorPage.isDoctorCardVisible("Dr. Clark"));
        Assert.assertTrue(doctorPage.isDoctorCardVisible("Dr. Foster"));
    }

    @Test(description = "Combining specialty and availability filters narrows results")
    public void testCombinedFilter() {
        doctorPage.filterBySpecialty("Neurology");
        doctorPage.filterByAvailability("true");
        Assert.assertEquals(doctorPage.getDoctorCardCount(), 1);
        Assert.assertTrue(doctorPage.isDoctorCardVisible("Dr. Brown"));
    }

    // ── Add Doctor ───────────────────────────────────────────────────────────

    @Test(description = "Add a new available doctor and verify card appears in grid")
    public void testAddDoctor() {
        doctorPage.openAddDoctorModal();
        doctorPage.fillDoctorForm("Dr. Green", "Pediatrics",
                "green@hms.com", "555-2001", "5", true);
        doctorPage.saveDoctor();
        Assert.assertTrue(doctorPage.isDoctorCardVisible("Dr. Green"), "New doctor card should appear");
        Assert.assertEquals(doctorPage.getDoctorCardCount(), 7, "Grid should now have 7 doctors");
    }

    @Test(description = "Add an unavailable doctor and verify badge")
    public void testAddUnavailableDoctor() {
        doctorPage.openAddDoctorModal();
        doctorPage.fillDoctorForm("Dr. Hayes", "Oncology",
                "hayes@hms.com", "555-2002", "3", false);
        doctorPage.saveDoctor();
        Assert.assertTrue(doctorPage.isDoctorCardVisible("Dr. Hayes"));
        doctorPage.filterByAvailability("false");
        Assert.assertTrue(doctorPage.isDoctorCardVisible("Dr. Hayes"), "Dr. Hayes should appear under unavailable filter");
    }

    @Test(description = "Cancel add doctor modal does not add a new card")
    public void testCancelAddDoctor() {
        doctorPage.openAddDoctorModal();
        doctorPage.closeModal();
        Assert.assertEquals(doctorPage.getDoctorCardCount(), 6, "Card count should remain 6 after cancel");
    }

    // ── View Doctor ──────────────────────────────────────────────────────────

    @Test(description = "View button triggers JS alert with doctor details")
    public void testViewDoctorAlert() {
        doctorPage.clickViewDoctor(1);
        String alertText = doctorPage.getAlertText();
        Assert.assertTrue(alertText.contains("Dr. Adams"), "Alert should contain doctor name");
        Assert.assertTrue(alertText.contains("Cardiology"), "Alert should contain specialty");
        doctorPage.acceptAlert();
    }

    // ── Remove Doctor ────────────────────────────────────────────────────────

    @Test(description = "Remove doctor confirm removes the card from grid")
    public void testRemoveDoctorConfirm() {
        int before = doctorPage.getDoctorCardCount();
        doctorPage.clickRemoveDoctor(6); // Dr. Foster
        doctorPage.acceptAlert();        // confirm the JS confirm dialog
        Assert.assertEquals(doctorPage.getDoctorCardCount(), before - 1, "Card count should decrease by 1");
        Assert.assertFalse(doctorPage.isDoctorCardVisible("Dr. Foster"), "Dr. Foster should be removed");
    }

    @Test(description = "Remove doctor dismiss keeps the card in grid")
    public void testRemoveDoctorDismiss() {
        int before = doctorPage.getDoctorCardCount();
        doctorPage.clickRemoveDoctor(5); // Dr. Evans
        doctorPage.dismissAlert();       // cancel the JS confirm dialog
        Assert.assertEquals(doctorPage.getDoctorCardCount(), before, "Card count should remain unchanged");
        Assert.assertTrue(doctorPage.isDoctorCardVisible("Dr. Evans"), "Dr. Evans should still be visible");
    }
}
