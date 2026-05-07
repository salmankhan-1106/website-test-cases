package com.example.landing;

import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LandingPageTests extends BaseTest {
    private WebDriverWait createWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test
    void titleContainsBrand() {
        driver.get(baseUrl());
        Assertions.assertTrue(driver.getTitle().contains("VoltEdge"));
    }

    @Test
    void heroHeadlineVisible() {
        driver.get(baseUrl());
        WebElement hero = createWait().until(
            ExpectedConditions.visibilityOfElementLocated(By.id("hero-title"))
        );
        Assertions.assertTrue(hero.getText().contains("VoltEdge"));
    }

    @Test
    void primaryCtaPresent() {
        driver.get(baseUrl());
        WebElement cta = createWait().until(
            ExpectedConditions.visibilityOfElementLocated(By.id("primary-cta"))
        );
        Assertions.assertEquals("Start Free Trial", cta.getText());
    }

    @Test
    void secondaryCtaLinksToFeatures() {
        driver.get(baseUrl());
        WebElement cta = createWait().until(
            ExpectedConditions.visibilityOfElementLocated(By.id("secondary-cta"))
        );
        Assertions.assertTrue(cta.getAttribute("href").contains("#features"));
    }

    @Test
    void navLinksCount() {
        driver.get(baseUrl());
        int navCount = driver.findElements(By.cssSelector(".nav-links a")).size();
        Assertions.assertEquals(4, navCount);
    }

    @Test
    void featuresSectionVisible() {
        driver.get(baseUrl());
        WebElement section = createWait().until(
            ExpectedConditions.visibilityOfElementLocated(By.id("features"))
        );
        Assertions.assertTrue(section.isDisplayed());
    }

    @Test
    void featureCardsCount() {
        driver.get(baseUrl());
        int cards = driver.findElements(By.cssSelector(".feature-card")).size();
        Assertions.assertEquals(3, cards);
    }

    @Test
    void pricingCardsCount() {
        driver.get(baseUrl());
        int cards = driver.findElements(By.cssSelector(".price-card")).size();
        Assertions.assertEquals(3, cards);
    }

    @Test
    void popularBadgeVisible() {
        driver.get(baseUrl());
        WebElement badge = createWait().until(
            ExpectedConditions.visibilityOfElementLocated(By.id("popular-badge"))
        );
        Assertions.assertEquals("Most Popular", badge.getText());
    }

    @Test
    void testimonialsVisible() {
        driver.get(baseUrl());
        WebElement section = createWait().until(
            ExpectedConditions.visibilityOfElementLocated(By.id("testimonials"))
        );
        Assertions.assertTrue(section.isDisplayed());
    }

    @Test
    void footerYearVisible() {
        driver.get(baseUrl());
        WebElement year = createWait().until(
            ExpectedConditions.visibilityOfElementLocated(By.id("footer-year"))
        );
        Assertions.assertEquals("2026", year.getText());
    }

    @Test
    void contactFieldsPresent() {
        driver.get(baseUrl());
        Assertions.assertTrue(driver.findElement(By.id("contact-name")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("contact-email")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("contact-message")).isDisplayed());
    }

    @Test
    void emptyContactFormShowsError() {
        driver.get(baseUrl());
        driver.findElement(By.id("submit-button")).click();
        WebElement error = createWait().until(
            ExpectedConditions.visibilityOfElementLocated(By.id("form-error"))
        );
        Assertions.assertEquals("Please fill in all fields.", error.getText());
    }

    @Test
    void invalidEmailShowsError() {
        driver.get(baseUrl());
        driver.findElement(By.id("contact-name")).sendKeys("Taylor");
        driver.findElement(By.id("contact-email")).sendKeys("invalid-email");
        driver.findElement(By.id("contact-message")).sendKeys("Need a demo.");
        driver.findElement(By.id("submit-button")).click();
        WebElement error = createWait().until(
            ExpectedConditions.visibilityOfElementLocated(By.id("form-error"))
        );
        Assertions.assertEquals("Please enter a valid email address.", error.getText());
    }

    @Test
    void validContactFormShowsSuccess() {
        driver.get(baseUrl());
        driver.findElement(By.id("contact-name")).sendKeys("Taylor");
        driver.findElement(By.id("contact-email")).sendKeys("taylor@example.com");
        driver.findElement(By.id("contact-message")).sendKeys("Need a demo.");
        driver.findElement(By.id("submit-button")).click();
        WebElement success = createWait().until(
            ExpectedConditions.visibilityOfElementLocated(By.id("form-success"))
        );
        Assertions.assertEquals("Thanks! We will reach out shortly.", success.getText());
    }
}
