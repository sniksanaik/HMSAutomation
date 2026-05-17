package org.example.hms.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ElementsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void switchToTab(String tabId) {
        driver.findElement(By.id("tab-" + tabId)).click();
        wait.until(ExpectedConditions.attributeContains(
            By.id("tab-content-" + tabId), "class", "active"));
    }
}
