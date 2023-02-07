package com.jasur.epam.mailru;

import com.jasur.epam.core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MailRuLoginPage extends BaseSeleniumPage {
    private final String MAIL_RU_URL = "https://account.mail.ru/login/";
    private final String userEmail;
    private final String userPassword;
    @FindBy(name = "username")
    private WebElement userEmailInput;

    @FindBy(name = "password")
    private WebElement userPasswordInput;

    public MailRuLoginPage(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        driver.get(MAIL_RU_URL);
        PageFactory.initElements(driver, this);
    }
    private void typeAndSubmitInputElement(WebElement inputElement, String textToType) {
        inputElement.clear();
        inputElement.sendKeys(textToType, Keys.ENTER);
    }

    public MailRuInboxPage login() {
        typeAndSubmitInputElement(userEmailInput, userEmail);
        typeAndSubmitInputElement(userPasswordInput, userPassword);
        return new MailRuInboxPage();
    }


    public boolean isEmailNotEmpty() {
        typeAndSubmitInputElement(userEmailInput, userEmail);
        return !elementExists(By.xpath("//small[@data-test-id=\"required\"]"));
    }

    public boolean isEmailRegistered() {
        typeAndSubmitInputElement(userEmailInput, userEmail);
        return !elementExists(By.xpath("//small[@data-test-id=\"accountNotRegistered\"]"));
    }

    public boolean isPasswordCorrect() {
        typeAndSubmitInputElement(userEmailInput, userEmail);
        typeAndSubmitInputElement(userPasswordInput, userPassword);
        return !elementExists(By.xpath("//div[@data-test-id=\"password-input-error\"]"));
    }
}
