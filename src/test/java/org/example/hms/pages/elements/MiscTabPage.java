package org.example.hms.pages.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MiscTabPage {

    private final WebDriver driver;
    private final Actions actions;
    private final JavascriptExecutor js;

    @FindBy(id = "tooltip-btn-1")     private WebElement tooltipBtn1;
    @FindBy(id = "draggable-item")    private WebElement draggableItem;
    @FindBy(id = "drop-zone")         private WebElement dropZone;
    @FindBy(id = "drop-text")         private WebElement dropText;
    @FindBy(id = "f-disabled")        private WebElement disabledInput;
    @FindBy(id = "f-readonly")        private WebElement readonlyInput;
    @FindBy(id = "f-disabled-select") private WebElement disabledSelect;
    @FindBy(id = "chk-disabled")      private WebElement disabledCheckbox;
    @FindBy(id = "scroll-target")     private WebElement scrollTarget;

    public MiscTabPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    public void hoverTooltip() {
        actions.moveToElement(tooltipBtn1).perform();
    }

    public void clickAccordionHeader(int index) {
        driver.findElement(By.id("acc-header-" + index)).click();
    }

    public boolean isAccordionBodyVisible(int index) {
        return driver.findElement(By.id("acc-body-" + index)).isDisplayed();
    }

    public void dragAndDrop() {
        actions.dragAndDrop(draggableItem, dropZone).perform();
    }

    public String getDropZoneText() { return dropText.getText(); }

    public boolean isDisabledInputNotEditable()  { return !disabledInput.isEnabled(); }
    public boolean isDisabledSelectNotEditable() { return !disabledSelect.isEnabled(); }
    public boolean isDisabledCheckboxChecked()   { return disabledCheckbox.isSelected(); }

    public boolean isReadonlyInputNotEditable() {
        return readonlyInput.getDomAttribute("readonly") != null;
    }

    public void scrollToTarget() {
        js.executeScript("arguments[0].scrollIntoView(true);", scrollTarget);
    }

    public boolean isScrollTargetVisible() {
        return (Boolean) js.executeScript(
            "const r = arguments[0].getBoundingClientRect(); " +
            "return r.top >= 0 && r.bottom <= window.innerHeight;", scrollTarget);
    }
}
