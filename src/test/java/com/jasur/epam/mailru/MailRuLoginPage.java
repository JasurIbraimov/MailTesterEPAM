package com.jasur.epam.mailru;

import com.jasur.epam.core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MailRuLoginPage extends BaseSeleniumPage {
    private final String MAIL_RU_LOGIN_URL = "https://account.mail.ru/login/";
    private final String userEmail;
    private final String userPassword;
    @FindBy(name = "username")
    private WebElement userEmailInput;

    @FindBy(name = "password")
    private WebElement userPasswordInput;

    public MailRuLoginPage(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        driver.get(MAIL_RU_LOGIN_URL);
        PageFactory.initElements(driver, this);
    }

    public MailRuInboxPage login() {
        userEmailInput.sendKeys(userEmail, Keys.ENTER);
        userPasswordInput.sendKeys(userPassword, Keys.ENTER);
        return new MailRuInboxPage();
    }


    public boolean isEmailEmpty() {
        userEmailInput.sendKeys(userEmail, Keys.ENTER);
        return elementExists(By.xpath("//small[@data-test-id=\"required\"]"));
    }

    public boolean isEmailNotRegistered() {
        userEmailInput.sendKeys(userEmail, Keys.ENTER);
        return elementExists(By.xpath("//small[@data-test-id=\"accountNotRegistered\"]"));
    }

    public boolean isPasswordIncorrect() {
        userEmailInput.sendKeys(userEmail, Keys.ENTER);
        userPasswordInput.sendKeys(userPassword, Keys.ENTER);
        return elementExists(By.xpath("//div[@data-test-id=\"password-input-error\"]"));
    }
}
