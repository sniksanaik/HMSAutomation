package org.example.hms.pages.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FormsTabPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    @FindBy(id = "f-text")               private WebElement textInput;
    @FindBy(id = "f-number")             private WebElement numberInput;
    @FindBy(id = "f-email")              private WebElement emailInput;
    @FindBy(id = "f-password")           private WebElement passwordInput;
    @FindBy(id = "f-date")               private WebElement dateInput;
    @FindBy(id = "f-time")               private WebElement timeInput;
    @FindBy(id = "f-tel")                private WebElement telInput;
    @FindBy(id = "f-url")                private WebElement urlInput;
    @FindBy(id = "f-search")             private WebElement searchInput;
    @FindBy(id = "f-range")              private WebElement rangeSlider;
    @FindBy(id = "range-val")            private WebElement rangeValueLabel;
    @FindBy(id = "f-select")             private WebElement dropdown;
    @FindBy(id = "f-multiselect")        private WebElement multiSelect;
    @FindBy(id = "f-textarea")           private WebElement textarea;
    @FindBy(id = "f-datalist")           private WebElement datalistInput;
    @FindBy(id = "submit-practice-form") private WebElement submitBtn;
    @FindBy(id = "reset-practice-form")  private WebElement resetBtn;
    @FindBy(id = "disable-btn")          private WebElement disableBtn;
    @FindBy(id = "form-result")          private WebElement formResult;

    public FormsTabPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    public void fillTextInput(String value)     { textInput.clear(); textInput.sendKeys(value); }
    public void fillNumberInput(String value)   { numberInput.clear(); numberInput.sendKeys(value); }
    public void fillEmailInput(String value)    { emailInput.clear(); emailInput.sendKeys(value); }
    public void fillPasswordInput(String value) { passwordInput.clear(); passwordInput.sendKeys(value); }
    public void fillDateInput(String value)     { dateInput.sendKeys(value); }
    public void fillTimeInput(String value)     { timeInput.sendKeys(value); }
    public void fillTelInput(String value)      { telInput.clear(); telInput.sendKeys(value); }
    public void fillUrlInput(String value)      { urlInput.clear(); urlInput.sendKeys(value); }
    public void fillSearchInput(String value)   { searchInput.clear(); searchInput.sendKeys(value); }
    public void fillTextarea(String value)      { textarea.clear(); textarea.sendKeys(value); }
    public void fillDatalist(String value)      { datalistInput.clear(); datalistInput.sendKeys(value); }

    public void selectDropdown(String visibleText) {
        new Select(dropdown).selectByVisibleText(visibleText);
    }

    public void selectMultipleOptions(String... values) {
        Select select = new Select(multiSelect);
        for (String v : values) select.selectByValue(v);
    }

    public void selectRadio(String priority) {
        driver.findElement(By.id("priority-" + priority)).click();
    }

    public void checkService(String serviceId) {
        WebElement chk = driver.findElement(By.id("chk-" + serviceId));
        if (!chk.isSelected()) chk.click();
    }

    public void setRangeSlider(int value) {
        js.executeScript("arguments[0].value = arguments[1]; " +
            "arguments[0].dispatchEvent(new Event('input'));", rangeSlider, value);
    }

    public String getRangeValue()      { return rangeValueLabel.getText(); }
    public boolean isSubmitDisabled()  { return !submitBtn.isEnabled(); }

    public void submit()               { submitBtn.click(); }
    public void reset()                { resetBtn.click(); }
    public void clickDisableSubmit()   { disableBtn.click(); }

    public boolean isFormResultVisible() {
        wait.until(ExpectedConditions.visibilityOf(formResult));
        return formResult.isDisplayed();
    }
}
