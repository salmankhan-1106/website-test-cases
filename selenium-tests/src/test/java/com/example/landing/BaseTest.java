package com.example.landing;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    void setupDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        String headless = System.getProperty("headless", "true");
        if ("true".equalsIgnoreCase(headless)) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1400,900");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected String baseUrl() {
        return System.getProperty("baseUrl", "http://localhost:9000/");
    }
}
