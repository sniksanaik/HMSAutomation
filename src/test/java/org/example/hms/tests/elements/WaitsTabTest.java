package org.example.hms.tests.elements;

import org.example.hms.base.BaseTest;
import org.example.hms.pages.ElementsPage;
import org.example.hms.pages.LoginPage;
import org.example.hms.pages.elements.WaitsTabPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WaitsTabTest extends BaseTest {

    private WaitsTabPage waitsTab;

    @BeforeMethod
    public void setup() {
        new LoginPage(driver, wait).login(ADMIN_USER, ADMIN_PASS);
        wait.until(ExpectedConditions.urlContains("index.html"));
        driver.get(BASE_URL + "/elements.html");
        new ElementsPage(driver, wait).switchToTab("waits");
        waitsTab = new WaitsTabPage(driver, wait);
    }

    @Test(description = "Delayed element becomes visible after 3 second wait")
    public void testDelayedElementAppearance() {
        waitsTab.clickShowDelayed();
        Assert.assertTrue(waitsTab.waitForDelayedElement(), "Delayed element should become visible");
    }

    @Test(description = "Toggle section shows on first click and hides on second")
    public void testToggleVisibility() {
        waitsTab.clickToggleSection();
        Assert.assertTrue(waitsTab.isToggleSectionVisible(), "Section should be visible after first click");
        waitsTab.clickToggleSection();
        Assert.assertFalse(waitsTab.isToggleSectionVisible(), "Section should be hidden after second click");
    }

    @Test(description = "Fetch data button shows result after spinner disappears")
    public void testFetchData() {
        waitsTab.clickFetchData();
        Assert.assertTrue(waitsTab.waitForFetchResult(), "Fetch result should appear after loading");
    }

    @Test(description = "Session timer starts and updates display from default value")
    public void testTimerStart() throws InterruptedException {
        waitsTab.clickStartTimer();
        Thread.sleep(1500);
        Assert.assertNotEquals(waitsTab.getTimerDisplay(), "--:--", "Timer display should update after start");
    }
}
