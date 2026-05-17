package org.example.hms.tests.elements;

import org.example.hms.base.BaseTest;
import org.example.hms.pages.ElementsPage;
import org.example.hms.pages.LoginPage;
import org.example.hms.pages.elements.AlertsTabPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AlertsTabTest extends BaseTest {

    private AlertsTabPage alertsTab;

    @BeforeMethod
    public void setup() {
        new LoginPage(driver, wait).login(ADMIN_USER, ADMIN_PASS);
        wait.until(ExpectedConditions.urlContains("index.html"));
        driver.get(BASE_URL + "/elements.html");
        new ElementsPage(driver, wait).switchToTab("alerts");
        alertsTab = new AlertsTabPage(driver, wait);
    }

    @Test(description = "JS Alert text is correct and can be accepted")
    public void testJsAlert() {
        alertsTab.triggerJsAlert();
        Assert.assertTrue(alertsTab.getAlertText().contains("JS Alert"), "Alert text should contain 'JS Alert'");
        alertsTab.acceptAlert();
    }

    @Test(description = "JS Confirm can be accepted")
    public void testJsConfirmAccept() {
        alertsTab.triggerJsConfirm();
        alertsTab.acceptAlert();
    }

    @Test(description = "JS Confirm can be dismissed")
    public void testJsConfirmDismiss() {
        alertsTab.triggerJsConfirm();
        alertsTab.dismissAlert();
    }

    @Test(description = "JS Prompt accepts typed text and submits")
    public void testJsPrompt() {
        alertsTab.triggerJsPrompt();
        alertsTab.sendTextToPrompt("Dr. Adams");
    }

    @Test(description = "Info modal opens and closes via OK button")
    public void testInfoModal() {
        alertsTab.openInfoModal();
        alertsTab.closeInfoModal();
    }

    @Test(description = "Success modal opens and closes via X button")
    public void testSuccessModal() {
        alertsTab.openSuccessModal();
        alertsTab.closeSuccessModal();
    }

    @Test(description = "Danger modal confirm triggers delete toast")
    public void testDangerModalConfirm() {
        alertsTab.openDangerModal();
        alertsTab.confirmDangerModal();
        Assert.assertTrue(alertsTab.isToastVisible(), "Toast should appear after confirming delete");
    }

    @Test(description = "Danger modal cancel closes without action")
    public void testDangerModalCancel() {
        alertsTab.openDangerModal();
        alertsTab.cancelDangerModal();
    }

    @Test(description = "Nested two-step modal completes full flow")
    public void testNestedModal() {
        alertsTab.completeNestedModal("Cardiology");
    }

    @Test(description = "Success toast notification is displayed")
    public void testSuccessToast() {
        alertsTab.clickSuccessToast();
        Assert.assertTrue(alertsTab.isToastVisible(), "Success toast should be visible");
    }

    @Test(description = "Error toast notification is displayed")
    public void testErrorToast() {
        alertsTab.clickErrorToast();
        Assert.assertTrue(alertsTab.isToastVisible(), "Error toast should be visible");
    }
}
