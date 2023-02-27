package com.jasur.epam.yandex;

import com.jasur.epam.core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class YandexMailLoginPage extends BaseSeleniumPage {
    private final String userEmail;
    private final String userPassword;

    @FindBy(id = "passp-field-login")
    private WebElement userEmailInput;

    public YandexMailLoginPage(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        driver.get("https://passport.yandex.ru/auth?retpath=https%3A%2F%2Fmail.yandex.ru");
        PageFactory.initElements(driver, this);
    }

    public YandexMailInboxPage login() {
        userEmailInput.sendKeys(userEmail, Keys.ENTER);
        WebElement userPasswordInput = driver.findElement(By.id("passp-field-passwd"));
        userPasswordInput.sendKeys(userPassword, Keys.ENTER);
        return new YandexMailInboxPage();
    }
    public boolean isEmailNotValid() {
        userEmailInput.sendKeys(userEmail, Keys.ENTER);
        return elementExists(By.id("field:input-login:hint"));
    }

    public boolean isPasswordIncorrect() {
        userEmailInput.sendKeys(userEmail, Keys.ENTER);
        WebElement userPasswordInput = driver.findElement(By.id("passp-field-passwd"));
        userPasswordInput.sendKeys(userPassword, Keys.ENTER);
        return elementExists(By.id("field:input-passwd:hint"));
    }
}
