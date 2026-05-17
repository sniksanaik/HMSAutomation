package org.example.hms.pages.elements;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AlertsTabPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "btn-js-alert")         private WebElement jsAlertBtn;
    @FindBy(id = "btn-js-confirm")       private WebElement jsConfirmBtn;
    @FindBy(id = "btn-js-prompt")        private WebElement jsPromptBtn;
    @FindBy(id = "btn-info-modal")       private WebElement infoModalBtn;
    @FindBy(id = "btn-success-modal")    private WebElement successModalBtn;
    @FindBy(id = "btn-danger-modal")     private WebElement dangerModalBtn;
    @FindBy(id = "btn-nested-modal")     private WebElement nestedModalBtn;
    @FindBy(id = "info-modal-ok")        private WebElement infoModalOkBtn;
    @FindBy(id = "close-success-modal")  private WebElement closeSuccessModalBtn;
    @FindBy(id = "danger-modal-confirm") private WebElement dangerConfirmBtn;
    @FindBy(id = "danger-modal-cancel")  private WebElement dangerCancelBtn;
    @FindBy(id = "nested-dept")          private WebElement nestedDeptSelect;
    @FindBy(id = "nested-next-btn")      private WebElement nestedNextBtn;
    @FindBy(id = "nested-confirm-chk")   private WebElement nestedConfirmChk;
    @FindBy(id = "nested-finish-btn")    private WebElement nestedFinishBtn;
    @FindBy(id = "btn-toast-success")    private WebElement toastSuccessBtn;
    @FindBy(id = "btn-toast-error")      private WebElement toastErrorBtn;
    @FindBy(id = "toast")                private WebElement toast;

    public AlertsTabPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    // JS Alerts
    public void triggerJsAlert()   { jsAlertBtn.click(); }
    public void triggerJsConfirm() { jsConfirmBtn.click(); }
    public void triggerJsPrompt()  { jsPromptBtn.click(); }

    public String getAlertText() {
        return wait.until(ExpectedConditions.alertIsPresent()).getText();
    }

    public void acceptAlert()  { wait.until(ExpectedConditions.alertIsPresent()).accept(); }
    public void dismissAlert() { wait.until(ExpectedConditions.alertIsPresent()).dismiss(); }

    public void sendTextToPrompt(String text) {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.sendKeys(text);
        alert.accept();
    }

    // Modals
    public void openInfoModal() {
        infoModalBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("info-modal")));
    }
    public void closeInfoModal() { infoModalOkBtn.click(); }

    public void openSuccessModal() {
        successModalBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-modal")));
    }
    public void closeSuccessModal() { closeSuccessModalBtn.click(); }

    public void openDangerModal() {
        dangerModalBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("danger-modal")));
    }
    public void confirmDangerModal() { dangerConfirmBtn.click(); }
    public void cancelDangerModal()  { dangerCancelBtn.click(); }

    public void completeNestedModal(String department) {
        nestedModalBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nested-modal")));
        new Select(nestedDeptSelect).selectByVisibleText(department);
        nestedNextBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nested-modal-2")));
        if (!nestedConfirmChk.isSelected()) nestedConfirmChk.click();
        nestedFinishBtn.click();
    }

    // Toasts
    public void clickSuccessToast() { toastSuccessBtn.click(); }
    public void clickErrorToast()   { toastErrorBtn.click(); }

    public boolean isToastVisible() {
        wait.until(ExpectedConditions.visibilityOf(toast));
        return toast.isDisplayed();
    }
}
