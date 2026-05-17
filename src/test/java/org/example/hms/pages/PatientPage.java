package org.example.hms.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PatientPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "btn-add-patient")
    private WebElement addPatientButton;

    @FindBy(id = "p-name")
    private WebElement nameInput;

    @FindBy(id = "p-age")
    private WebElement ageInput;

    @FindBy(id = "p-email")
    private WebElement emailInput;

    @FindBy(id = "p-phone")
    private WebElement phoneInput;

    @FindBy(id = "p-blood")
    private WebElement bloodGroupSelect;

    @FindBy(id = "p-dept")
    private WebElement departmentSelect;

    @FindBy(id = "p-insurance")
    private WebElement insuranceCheckbox;

    @FindBy(id = "p-emergency")
    private WebElement emergencyCheckbox;

    @FindBy(id = "save-patient-btn")
    private WebElement saveButton;

    @FindBy(id = "close-patient-modal")
    private WebElement closeModalButton;

    @FindBy(id = "patients-tbody")
    private WebElement tableBody;

    @FindBy(id = "search-patients")
    private WebElement searchInput;

    @FindBy(id = "patient-modal")
    private WebElement patientModal;

    public PatientPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void clickAddPatient() {
        wait.until(ExpectedConditions.elementToBeClickable(addPatientButton)).click();
        wait.until(ExpectedConditions.visibilityOf(patientModal));
    }

    public void fillPatientForm(String name, String age, String gender, String bloodGroup, String department) {
        nameInput.clear();
        nameInput.sendKeys(name);
        ageInput.clear();
        ageInput.sendKeys(age);
        driver.findElement(By.cssSelector("input[name='gender'][value='" + gender + "']")).click();
        new Select(bloodGroupSelect).selectByValue(bloodGroup);
        new Select(departmentSelect).selectByVisibleText(department);
    }

    public void setInsurance(boolean check) {
        if (insuranceCheckbox.isSelected() != check) insuranceCheckbox.click();
    }

    public void setEmergency(boolean check) {
        if (emergencyCheckbox.isSelected() != check) emergencyCheckbox.click();
    }

    public void savePatient() {
        saveButton.click();
        wait.until(ExpectedConditions.invisibilityOf(patientModal));
    }

    public void closeModal() { closeModalButton.click(); }

    public boolean isPatientInTable(String name) {
        wait.until(ExpectedConditions.visibilityOf(tableBody));
        return tableBody.getText().contains(name);
    }

    public void searchPatient(String name) {
        searchInput.clear();
        searchInput.sendKeys(name);
    }

    public void clickEditForPatient(String name) {
        wait.until(ExpectedConditions.visibilityOf(tableBody));
        tableBody.findElements(By.tagName("tr")).stream()
            .filter(row -> row.getText().contains(name))
            .findFirst()
            .ifPresent(row -> row.findElement(By.cssSelector("button[onclick*='editPatient']")).click());
    }

    public void clickDeleteForPatient(String name) {
        wait.until(ExpectedConditions.visibilityOf(tableBody));
        tableBody.findElements(By.tagName("tr")).stream()
            .filter(row -> row.getText().contains(name))
            .findFirst()
            .ifPresent(row -> row.findElement(By.cssSelector("button[onclick*='deletePatient']")).click());
    }

    public void confirmDelete() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("confirm-delete-btn"))).click();
    }
}
