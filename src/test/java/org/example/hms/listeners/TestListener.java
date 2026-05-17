package org.example.hms.listeners;

import org.example.hms.base.BaseTest;
import org.example.hms.utils.ScreenshotUtil;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object instance = result.getInstance();
        if (instance instanceof BaseTest baseTest && baseTest.driver != null) {
            String testName = result.getTestClass().getRealClass().getSimpleName()
                    + "_" + result.getMethod().getMethodName();
            String path = ScreenshotUtil.capture(baseTest.driver, testName, ScreenshotUtil.FAILURE_DIR);
            System.err.println("FAILED: " + result.getMethod().getMethodName()
                    + " | Screenshot: " + path);
        }
    }
}
