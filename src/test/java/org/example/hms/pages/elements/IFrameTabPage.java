package org.example.hms.pages.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IFrameTabPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "iframe-form") private WebElement iframeElement;

    public IFrameTabPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void switchToIframe() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeElement));
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void fillLabRequestForm(String patientName, String testType, String priority) {
        driver.findElement(By.id("iframe-patient")).sendKeys(patientName);
        new Select(driver.findElement(By.id("iframe-test"))).selectByValue(testType);
        driver.findElement(By.id("iframe-priority-" + priority)).click();
    }

    public void submitLabRequest() {
        driver.findElement(By.id("iframe-submit-btn")).click();
    }

    public boolean isResultVisible() {
        WebElement result = driver.findElement(By.id("iframe-result"));
        wait.until(ExpectedConditions.visibilityOf(result));
        return result.isDisplayed();
    }

    public void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }
}
