package org.example.hms.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.hms.utils.ConfigReader;
import org.example.hms.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    public WebDriver driver;
    protected WebDriverWait wait;
    protected static final String BASE_URL    = ConfigReader.getBaseUrl();
    protected static final String ADMIN_USER  = ConfigReader.getAdminUsername();
    protected static final String ADMIN_PASS  = ConfigReader.getAdminPassword();

    @BeforeMethod
    public void setUp() {
        driver = createDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        driver.get(BASE_URL + "/login.html");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    protected String takeScreenshot(String name) {
        return ScreenshotUtil.capture(driver, name);
    }

    protected String takeScreenshot(String name, String folder) {
        return ScreenshotUtil.capture(driver, name, folder);
    }

    private WebDriver createDriver() {
        boolean headless = ConfigReader.isHeadless();
        return switch (ConfigReader.getBrowser().toLowerCase()) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions opts = new FirefoxOptions();
                if (headless) opts.addArguments("-headless");
                yield new FirefoxDriver(opts);
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions opts = new EdgeOptions();
                if (headless) opts.addArguments("--headless");
                yield new EdgeDriver(opts);
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions opts = new ChromeOptions();
                opts.addArguments("--start-maximized");
                if (headless) opts.addArguments("--headless");
                yield new ChromeDriver(opts);
            }
        };
    }
}
