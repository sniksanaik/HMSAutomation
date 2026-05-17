package org.example.hms.pages.elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitsTabPage {

    private final WebDriverWait wait;

    @FindBy(id = "btn-show-delayed")   private WebElement showDelayedBtn;
    @FindBy(id = "delayed-element")    private WebElement delayedElement;
    @FindBy(id = "btn-start-progress") private WebElement startProgressBtn;
    @FindBy(id = "progress-bar")       private WebElement progressBar;
    @FindBy(id = "btn-toggle-section") private WebElement toggleSectionBtn;
    @FindBy(id = "toggle-section")     private WebElement toggleSection;
    @FindBy(id = "btn-fetch-data")     private WebElement fetchDataBtn;
    @FindBy(id = "fetch-result")       private WebElement fetchResult;
    @FindBy(id = "btn-start-timer")    private WebElement startTimerBtn;
    @FindBy(id = "timer-display")      private WebElement timerDisplay;

    public WaitsTabPage(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void clickShowDelayed() { showDelayedBtn.click(); }

    public boolean waitForDelayedElement() {
        wait.until(ExpectedConditions.visibilityOf(delayedElement));
        return delayedElement.isDisplayed();
    }

    public void clickStartProgress()     { startProgressBtn.click(); }
    public String getProgressBarWidth()  { return progressBar.getCssValue("width"); }

    public void clickToggleSection()     { toggleSectionBtn.click(); }
    public boolean isToggleSectionVisible() { return toggleSection.isDisplayed(); }

    public void clickFetchData() { fetchDataBtn.click(); }

    public boolean waitForFetchResult() {
        wait.until(ExpectedConditions.visibilityOf(fetchResult));
        return fetchResult.isDisplayed();
    }

    public void clickStartTimer()        { startTimerBtn.click(); }
    public String getTimerDisplay()      { return timerDisplay.getText(); }
}
