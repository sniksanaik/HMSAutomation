package org.example.hms.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    public static final String PASS_DIR    = "screenshots/pass";
    public static final String FAILURE_DIR = "screenshots/failures";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    public static String capture(WebDriver driver, String screenshotName) {
        return capture(driver, screenshotName, PASS_DIR);
    }

    public static String capture(WebDriver driver, String screenshotName, String folder) {
        try {
            Path dir = Paths.get(folder);
            if (!Files.exists(dir)) Files.createDirectories(dir);

            String timestamp = LocalDateTime.now().format(FORMATTER);
            String fileName = screenshotName + "_" + timestamp + ".png";
            Path destination = dir.resolve(fileName);

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(srcFile.toPath(), destination);

            System.out.println("Screenshot saved: " + destination.toAbsolutePath());
            return destination.toAbsolutePath().toString();
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
            return null;
        }
    }
}
