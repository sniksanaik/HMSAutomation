package org.example.hms.pages.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TablesTabPage {

    private final WebDriver driver;

    @FindBy(id = "table-search")        private WebElement searchInput;
    @FindBy(id = "table-rows-per-page") private WebElement rowsPerPageSelect;
    @FindBy(id = "sortable-tbody")      private WebElement tableBody;

    public TablesTabPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void searchTable(String text) {
        searchInput.clear();
        searchInput.sendKeys(text);
    }

    public void setRowsPerPage(String value) {
        new Select(rowsPerPageSelect).selectByValue(value);
    }

    public void clickColumnHeader(int columnIndex) {
        List<WebElement> headers = driver.findElements(
            By.cssSelector("#sortable-table thead th"));
        headers.get(columnIndex).click();
    }

    public int getVisibleRowCount() {
        return tableBody.findElements(By.tagName("tr")).size();
    }
}
