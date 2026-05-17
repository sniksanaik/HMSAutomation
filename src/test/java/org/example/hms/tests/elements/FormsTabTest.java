package org.example.hms.tests.elements;

import org.example.hms.base.BaseTest;
import org.example.hms.pages.LoginPage;
import org.example.hms.pages.elements.FormsTabPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FormsTabTest extends BaseTest {

    private FormsTabPage formsTab;

    @BeforeMethod
    public void setup() {
        new LoginPage(driver, wait).login(ADMIN_USER, ADMIN_PASS);
        wait.until(ExpectedConditions.urlContains("index.html"));
        driver.get(BASE_URL + "/elements.html");
        // Forms tab is active by default
        formsTab = new FormsTabPage(driver, wait);
    }

    @Test(description = "Fill all basic text-type inputs")
    public void testFillTextInputs() {
        formsTab.fillTextInput("Selenium Test");
        formsTab.fillNumberInput("42");
        formsTab.fillEmailInput("test@example.com");
        formsTab.fillPasswordInput("secret123");
        formsTab.fillTelInput("555-7890");
        formsTab.fillUrlInput("https://example.com");
        formsTab.fillSearchInput("patient search");
        formsTab.fillTextarea("Some detailed notes here");
    }

    @Test(description = "Select a single dropdown option")
    public void testDropdownSelection() {
        formsTab.selectDropdown("Cardiology");
    }

    @Test(description = "Select multiple options in multi-select")
    public void testMultiSelect() {
        formsTab.selectMultipleOptions("mon", "wed", "fri");
    }

    @Test(description = "Select High priority radio button")
    public void testRadioButtonSelection() {
        formsTab.selectRadio("high");
    }

    @Test(description = "Check X-Ray, MRI and ECG service checkboxes")
    public void testCheckboxes() {
        formsTab.checkService("xray");
        formsTab.checkService("mri");
        formsTab.checkService("ecg");
    }

    @Test(description = "Range slider updates the displayed value label")
    public void testRangeSlider() {
        formsTab.setRangeSlider(75);
        Assert.assertEquals(formsTab.getRangeValue(), "75", "Range label should reflect slider value");
    }

    @Test(description = "Datalist autocomplete accepts a doctor name")
    public void testDatalistInput() {
        formsTab.fillDatalist("Dr. Adams");
    }

    @Test(description = "Submitting a filled form shows the result panel")
    public void testSubmitForm() {
        formsTab.fillTextInput("Test User");
        formsTab.fillNumberInput("30");
        formsTab.fillEmailInput("user@test.com");
        formsTab.selectDropdown("Neurology");
        formsTab.selectRadio("medium");
        formsTab.submit();
        Assert.assertTrue(formsTab.isFormResultVisible(), "Form result panel should be visible after submit");
    }

    @Test(description = "Disable Submit button toggle disables the button")
    public void testDisableSubmitButton() {
        formsTab.clickDisableSubmit();
        Assert.assertTrue(formsTab.isSubmitDisabled(), "Submit button should be disabled after toggle");
    }

    @Test(description = "Reset button clears filled form fields")
    public void testResetForm() {
        formsTab.fillTextInput("To be cleared");
        formsTab.fillNumberInput("99");
        formsTab.reset();
    }
}
