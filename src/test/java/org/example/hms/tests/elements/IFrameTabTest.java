package org.example.hms.tests.elements;

import org.example.hms.base.BaseTest;
import org.example.hms.pages.ElementsPage;
import org.example.hms.pages.LoginPage;
import org.example.hms.pages.elements.IFrameTabPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class IFrameTabTest extends BaseTest {

    private IFrameTabPage iframeTab;

    @BeforeMethod
    public void setup() {
        new LoginPage(driver, wait).login(ADMIN_USER, ADMIN_PASS);
        wait.until(ExpectedConditions.urlContains("index.html"));
        driver.get(BASE_URL + "/elements.html");
        new ElementsPage(driver, wait).switchToTab("iframe");
        iframeTab = new IFrameTabPage(driver, wait);
    }

    @Test(description = "Fill and submit lab request form inside iframe")
    public void testIframeFormSubmit() {
        iframeTab.switchToIframe();
        iframeTab.fillLabRequestForm("Alice Johnson", "mri", "urgent");
        iframeTab.submitLabRequest();
        Assert.assertTrue(iframeTab.isResultVisible(), "Result message should appear after iframe form submit");
        iframeTab.switchToDefaultContent();
    }

    @Test(description = "Submitting empty iframe form triggers a JS alert")
    public void testIframeEmptySubmitShowsAlert() {
        iframeTab.switchToIframe();
        iframeTab.submitLabRequest();
        iframeTab.acceptAlert();
        iframeTab.switchToDefaultContent();
    }

    @Test(description = "Urgent priority radio can be selected inside iframe")
    public void testIframeUrgentPriority() {
        iframeTab.switchToIframe();
        iframeTab.fillLabRequestForm("Bob Smith", "xray", "urgent");
        iframeTab.submitLabRequest();
        Assert.assertTrue(iframeTab.isResultVisible());
        iframeTab.switchToDefaultContent();
    }
}
