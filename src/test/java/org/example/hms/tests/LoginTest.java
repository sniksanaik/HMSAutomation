package org.example.hms.tests;

import org.example.hms.base.BaseTest;
import org.example.hms.pages.DashboardPage;
import org.example.hms.pages.LoginPage;
import org.example.hms.utils.ScreenshotUtil;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(description = "Valid credentials redirect to dashboard")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.login(ADMIN_USER, ADMIN_PASS);

        wait.until(ExpectedConditions.urlContains("index.html"));
        DashboardPage dashboard = new DashboardPage(driver, wait);
        Assert.assertTrue(dashboard.isLoaded(), "Dashboard should be loaded after valid login");

        takeScreenshot("homepage_after_login", ScreenshotUtil.PASS_DIR);
    }

    @Test(description = "Invalid credentials show error message")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.login(ADMIN_USER, "wrongpassword");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be visible for invalid credentials");
    }

    @Test(description = "Empty credentials show error message")
    public void testEmptyCredentials() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.login("", "");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be visible for empty credentials");
    }

    @Test(description = "Remember me checkbox can be selected")
    public void testRememberMe() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.checkRememberMe();
        loginPage.login(ADMIN_USER, ADMIN_PASS);
        wait.until(ExpectedConditions.urlContains("index.html"));
        Assert.assertTrue(new DashboardPage(driver, wait).isLoaded());

    }
}
