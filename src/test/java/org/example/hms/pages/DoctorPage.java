package org.example.hms.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class DoctorPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "btn-add-doctor")    private WebElement addDoctorBtn;
    @FindBy(id = "search-doctors")    private WebElement searchInput;
    @FindBy(id = "filter-specialty")  private WebElement specialtyFilter;
    @FindBy(id = "filter-availability") private WebElement availabilityFilter;
    @FindBy(id = "doctors-grid")      private WebElement doctorsGrid;
    @FindBy(id = "doctor-modal")      private WebElement doctorModal;

    // Add Doctor form fields
    @FindBy(id = "d-name")       private WebElement nameInput;
    @FindBy(id = "d-specialty")  private WebElement specialtySelect;
    @FindBy(id = "d-email")      private WebElement emailInput;
    @FindBy(id = "d-phone")      private WebElement phoneInput;
    @FindBy(id = "d-experience") private WebElement experienceInput;
    @FindBy(id = "d-available")  private WebElement availableCheckbox;
    @FindBy(id = "save-doctor-btn")       private WebElement saveBtn;
    @FindBy(id = "close-doctor-modal")    private WebElement closeModalBtn;

    public DoctorPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    // ── Grid ────────────────────────────────────────────────────────────────

    public boolean isDoctorCardVisible(String doctorName) {
        wait.until(ExpectedConditions.visibilityOf(doctorsGrid));
        return doctorsGrid.getText().contains(doctorName);
    }

    public int getDoctorCardCount() {
        return doctorsGrid.findElements(By.cssSelector(".card")).size();
    }

    // ── Search & Filters ────────────────────────────────────────────────────

    public void searchDoctor(String name) {
        searchInput.clear();
        searchInput.sendKeys(name);
    }

    public void filterBySpecialty(String specialty) {
        new Select(specialtyFilter).selectByVisibleText(specialty);
    }

    public void filterByAvailability(String value) {
        new Select(availabilityFilter).selectByValue(value);
    }

    public void clearFilters() {
        searchInput.clear();
        new Select(specialtyFilter).selectByValue("");
        new Select(availabilityFilter).selectByValue("");
    }

    // ── Add Doctor Modal ─────────────────────────────────────────────────────

    public void openAddDoctorModal() {
        addDoctorBtn.click();
        wait.until(ExpectedConditions.visibilityOf(doctorModal));
    }

    public void fillDoctorForm(String name, String specialty, String email,
                                String phone, String experience, boolean available) {
        nameInput.clear();
        nameInput.sendKeys(name);
        new Select(specialtySelect).selectByVisibleText(specialty);
        emailInput.clear();
        emailInput.sendKeys(email);
        phoneInput.clear();
        phoneInput.sendKeys(phone);
        experienceInput.clear();
        experienceInput.sendKeys(experience);
        if (availableCheckbox.isSelected() != available) availableCheckbox.click();
    }

    public void saveDoctor() {
        saveBtn.click();
        wait.until(ExpectedConditions.invisibilityOf(doctorModal));
    }

    public void closeModal() { closeModalBtn.click(); }

    // ── View & Remove ────────────────────────────────────────────────────────

    public void clickViewDoctor(int doctorId) {
        driver.findElement(By.id("view-doctor-" + doctorId)).click();
    }

    public String getAlertText() {
        return wait.until(ExpectedConditions.alertIsPresent()).getText();
    }

    public void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }

    public void dismissAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).dismiss();
    }

    public void clickRemoveDoctor(int doctorId) {
        driver.findElement(By.id("remove-doctor-" + doctorId)).click();
    }

    public List<WebElement> getDoctorCards() {
        return doctorsGrid.findElements(By.cssSelector(".card"));
    }
}
