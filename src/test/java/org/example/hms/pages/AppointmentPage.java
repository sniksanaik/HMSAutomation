package org.example.hms.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppointmentPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "btn-add-appointment")
    private WebElement addAppointmentButton;

    @FindBy(id = "a-patient")
    private WebElement patientNameInput;

    @FindBy(id = "a-doctor")
    private WebElement doctorSelect;

    @FindBy(id = "a-dept")
    private WebElement departmentSelect;

    @FindBy(id = "a-status")
    private WebElement statusSelect;

    @FindBy(id = "a-date")
    private WebElement dateInput;

    @FindBy(id = "a-time")
    private WebElement timeInput;

    @FindBy(id = "save-appt-btn")
    private WebElement saveButton;

    @FindBy(id = "close-appt-modal")
    private WebElement closeModalButton;

    @FindBy(id = "appointments-tbody")
    private WebElement tableBody;

    @FindBy(id = "appointment-modal")
    private WebElement appointmentModal;

    @FindBy(id = "filter-dept")
    private WebElement filterDeptSelect;

    @FindBy(id = "filter-status")
    private WebElement filterStatusSelect;

    public AppointmentPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void clickAddAppointment() {
        wait.until(ExpectedConditions.elementToBeClickable(addAppointmentButton)).click();
        wait.until(ExpectedConditions.visibilityOf(appointmentModal));
    }

    public void fillAppointmentForm(String patientName, String doctor, String department,
                                     String date, String time, String type) {
        patientNameInput.clear();
        patientNameInput.sendKeys(patientName);
        new Select(doctorSelect).selectByVisibleText(doctor);
        new Select(departmentSelect).selectByVisibleText(department);
        dateInput.sendKeys(date);
        timeInput.sendKeys(time);
        driver.findElement(By.cssSelector("input[name='appt-type'][value='" + type + "']")).click();
    }

    public void setStatus(String status) {
        new Select(statusSelect).selectByVisibleText(status);
    }

    public void saveAppointment() {
        saveButton.click();
        wait.until(ExpectedConditions.invisibilityOf(appointmentModal));
    }

    public boolean isAppointmentInTable(String patientName) {
        wait.until(ExpectedConditions.visibilityOf(tableBody));
        return tableBody.getText().contains(patientName);
    }

    public void filterByDepartment(String dept) {
        new Select(filterDeptSelect).selectByVisibleText(dept);
    }

    public void filterByStatus(String status) {
        new Select(filterStatusSelect).selectByVisibleText(status);
    }
}
