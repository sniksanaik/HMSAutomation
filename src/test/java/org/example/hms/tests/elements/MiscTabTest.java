package org.example.hms.tests.elements;

import org.example.hms.base.BaseTest;
import org.example.hms.pages.ElementsPage;
import org.example.hms.pages.LoginPage;
import org.example.hms.pages.elements.MiscTabPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MiscTabTest extends BaseTest {

    private MiscTabPage miscTab;

    @BeforeMethod
    public void setup() {
        new LoginPage(driver, wait).login(ADMIN_USER, ADMIN_PASS);
        wait.until(ExpectedConditions.urlContains("index.html"));
        driver.get(BASE_URL + "/elements.html");
        new ElementsPage(driver, wait).switchToTab("misc");
        miscTab = new MiscTabPage(driver, wait);
    }

    @Test(description = "Accordion 1 opens and shows Cardiology content")
    public void testAccordionOpen() {
        miscTab.clickAccordionHeader(1);
        Assert.assertTrue(miscTab.isAccordionBodyVisible(1), "Accordion body 1 should be visible");
    }

    @Test(description = "Multiple accordions can be independently toggled")
    public void testMultipleAccordions() {
        miscTab.clickAccordionHeader(1);
        miscTab.clickAccordionHeader(2);
        Assert.assertTrue(miscTab.isAccordionBodyVisible(2), "Accordion body 2 should be visible");
    }

    @Test(description = "Disabled input field is not editable")
    public void testDisabledInput() {
        Assert.assertTrue(miscTab.isDisabledInputNotEditable(), "Disabled input should not be enabled");
    }

    @Test(description = "Readonly input has readonly attribute")
    public void testReadonlyInput() {
        Assert.assertTrue(miscTab.isReadonlyInputNotEditable(), "Readonly input should have readonly attribute");
    }

    @Test(description = "Disabled select is not interactable")
    public void testDisabledSelect() {
        Assert.assertTrue(miscTab.isDisabledSelectNotEditable(), "Disabled select should not be enabled");
    }

    @Test(description = "Disabled checkbox is pre-checked and not interactable")
    public void testDisabledCheckbox() {
        Assert.assertTrue(miscTab.isDisabledCheckboxChecked(), "Disabled checkbox should be checked");
    }

    @Test(description = "Scroll to target brings element into viewport")
    public void testScrollToElement() {
        miscTab.scrollToTarget();
        Assert.assertTrue(miscTab.isScrollTargetVisible(), "Scroll target should be in viewport after scroll");
    }

    @Test(description = "Drag and drop moves item to drop zone")
    public void testDragAndDrop() {
        miscTab.dragAndDrop();
    }
}
