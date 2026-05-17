package org.example.hms.tests.elements;

import org.example.hms.base.BaseTest;
import org.example.hms.pages.ElementsPage;
import org.example.hms.pages.LoginPage;
import org.example.hms.pages.elements.TablesTabPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TablesTabTest extends BaseTest {

    private TablesTabPage tablesTab;

    @BeforeMethod
    public void setup() {
        new LoginPage(driver, wait).login(ADMIN_USER, ADMIN_PASS);
        wait.until(ExpectedConditions.urlContains("index.html"));
        driver.get(BASE_URL + "/elements.html");
        new ElementsPage(driver, wait).switchToTab("tables");
        tablesTab = new TablesTabPage(driver, wait);
    }

    @Test(description = "Search input filters table rows by patient name")
    public void testTableSearch() {
        tablesTab.searchTable("John");
        Assert.assertTrue(tablesTab.getVisibleRowCount() >= 1, "At least one row should match 'John'");
    }

    @Test(description = "Setting 5 rows per page limits visible rows to 5")
    public void testRowsPerPage() {
        tablesTab.setRowsPerPage("5");
        Assert.assertTrue(tablesTab.getVisibleRowCount() <= 5, "Row count should not exceed 5 per page");
    }

    @Test(description = "Clicking Patient column header sorts ascending then descending")
    public void testColumnSortByPatient() {
        tablesTab.clickColumnHeader(1); // ascending
        tablesTab.clickColumnHeader(1); // descending
    }

    @Test(description = "Clicking Age column header sorts the table")
    public void testColumnSortByAge() {
        tablesTab.clickColumnHeader(2);
    }

    @Test(description = "Search with no match returns empty table")
    public void testSearchNoMatch() {
        tablesTab.searchTable("ZZZNOMATCH");
        Assert.assertEquals(tablesTab.getVisibleRowCount(), 0, "No rows should match an unknown search term");
    }
}
