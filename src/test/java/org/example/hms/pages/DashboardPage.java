package org.example.hms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "total-patients")
    private WebElement totalPatients;

    @FindBy(id = "total-appointments")
    private WebElement totalAppointments;

    @FindBy(id = "total-emergency")
    private WebElement totalEmergency;

    @FindBy(id = "btn-new-patient")
    private WebElement newPatientButton;

    @FindBy(id = "btn-new-appointment")
    private WebElement newAppointmentButton;

    @FindBy(id = "btn-emergency")
    private WebElement emergencyButton;

    @FindBy(id = "btn-report")
    private WebElement reportButton;

    @FindBy(id = "logout-btn")
    private WebElement logoutButton;

    @FindBy(id = "search-patients")
    private WebElement searchInput;

    @FindBy(id = "patients-tbody")
    private WebElement patientsTableBody;

    public DashboardPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        wait.until(ExpectedConditions.visibilityOf(newPatientButton));
        return driver.getCurrentUrl().contains("index.html");
    }

    public String getTotalPatients() {
        wait.until(ExpectedConditions.visibilityOf(totalPatients));
        return totalPatients.getText();
    }

    public void clickNewPatient() { newPatientButton.click(); }

    public void clickNewAppointment() { newAppointmentButton.click(); }

    public void clickEmergency() { emergencyButton.click(); }

    public void clickReport() { reportButton.click(); }

    public void logout() { logoutButton.click(); }

    public void searchPatient(String name) {
        searchInput.clear();
        searchInput.sendKeys(name);
    }
}
