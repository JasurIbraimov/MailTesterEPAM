package com.jasur.epam.core;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

abstract public class BaseSeleniumPage {
    protected static WebDriver driver;

    public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
    }

    public boolean elementExists(By findBy) {
        try {
            driver.findElement(findBy);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }
}
