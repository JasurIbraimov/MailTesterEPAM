package com.jasur.epam.gmail;

import com.jasur.epam.core.BaseSeleniumTest;
import org.junit.Assert;
import org.junit.Test;

public class YandexMailLoginPageTest extends BaseSeleniumTest {

    private static final String USER_EMAIL = "t3stepam";
    private static final String USER_PASSWORD = "b3)Q#9&Ly";
    @Test
    public void testAbilityLoginToYandexMailWithCorrectCredentials() {
        YandexMailLoginPage yandexMailLoginPage = new YandexMailLoginPage(USER_EMAIL, USER_PASSWORD);
        YandexMailInboxPage yandexMailInboxPage = yandexMailLoginPage.login();
        Assert.assertTrue(yandexMailInboxPage.isWriteLetterLinkPresented());
    }

    @Test
    public void testImpossibilityLoginToYandexMailWithWrongPassword() {
        YandexMailLoginPage yandexMailLoginPage = new YandexMailLoginPage(USER_EMAIL, "wrong password");
        Assert.assertTrue(yandexMailLoginPage.isPasswordIncorrect());
    }

    @Test
    public void testImpossibilityLoginToYandexMailWithWrongEmail() {
        YandexMailLoginPage yandexMailLoginPage = new YandexMailLoginPage("123123", USER_PASSWORD);
        Assert.assertTrue(yandexMailLoginPage.isEmailNotValid());
    }

    @Test
    public void testImpossibilityLoginToYandexMailWithEmptyPassword() {
        YandexMailLoginPage yandexMailLoginPage = new YandexMailLoginPage(USER_EMAIL, "");
        Assert.assertTrue(yandexMailLoginPage.isPasswordIncorrect());
    }

    @Test
    public void testImpossibilityLoginToYandexMailWithEmptyEmail() {
        YandexMailLoginPage yandexMailLoginPage = new YandexMailLoginPage("", USER_PASSWORD);
        Assert.assertTrue(yandexMailLoginPage.isEmailNotValid());
    }
}
