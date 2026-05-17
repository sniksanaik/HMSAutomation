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
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.URL;
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
        String gridUrl = ConfigReader.getProperty("grid.url");

        ChromeOptions chromeOpts = new ChromeOptions();
        FirefoxOptions firefoxOpts = new FirefoxOptions();
        EdgeOptions edgeOpts = new EdgeOptions();

        if (headless) {
            chromeOpts.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
            firefoxOpts.addArguments("-headless");
            edgeOpts.addArguments("--headless");
        }

        if (gridUrl != null && !gridUrl.isBlank()) {
            String fullGridUrl = gridUrl.endsWith("/") ? gridUrl : gridUrl + "/";
            for (int attempt = 1; attempt <= 5; attempt++) {
                try {
                    return switch (ConfigReader.getBrowser().toLowerCase()) {
                        case "firefox" -> new RemoteWebDriver(new URL(fullGridUrl), firefoxOpts);
                        case "edge"    -> new RemoteWebDriver(new URL(fullGridUrl), edgeOpts);
                        default        -> new RemoteWebDriver(new URL(fullGridUrl), chromeOpts);
                    };
                } catch (Exception e) {
                    if (attempt == 5) throw new RuntimeException("Grid not available after 5 attempts: " + fullGridUrl, e);
                    System.out.println("Grid not ready, retrying in 5s... (attempt " + attempt + "/5)");
                    try { Thread.sleep(5000); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
                }
            }
        }

        return switch (ConfigReader.getBrowser().toLowerCase()) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver(firefoxOpts);
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                yield new EdgeDriver(edgeOpts);
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                chromeOpts.addArguments("--start-maximized");
                yield new ChromeDriver(chromeOpts);
            }
        };
    }
}
