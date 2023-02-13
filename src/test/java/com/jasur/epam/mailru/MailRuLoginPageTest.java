package com.jasur.epam.mailru;

import com.jasur.epam.core.BaseSeleniumTest;
import org.junit.Assert;
import org.junit.Test;


public class MailRuLoginPageTest extends BaseSeleniumTest {

    private static final String USER_EMAIL = "t3st.epam@mail.ru";
    private static final String USER_PASSWORD = "b2)Q#9&Ly";
    @Test
    public void testAbilityLoginToMailRuWithCorrectCredentials() {
        MailRuLoginPage mailRuLoginPage = new MailRuLoginPage(USER_EMAIL, USER_PASSWORD);
        MailRuInboxPage mailRuInboxPage = mailRuLoginPage.login();
        Assert.assertTrue(mailRuInboxPage.isWriteLetterLinkPresented());
    }

    @Test
    public void testImpossibilityLoginToMailRuWithWrongPassword() {
        MailRuLoginPage mailRuLoginPage = new MailRuLoginPage(USER_EMAIL, "wrong password");
        Assert.assertTrue(mailRuLoginPage.isPasswordIncorrect());
    }

    @Test
    public void testImpossibilityLoginToMailRuWithWrongEmail() {
        MailRuLoginPage mailRuLoginPage = new MailRuLoginPage("eamtestuser@mail.ru", USER_PASSWORD);
        Assert.assertTrue(mailRuLoginPage.isEmailNotRegistered());

    }

    @Test
    public void testImpossibilityLoginToMailRuWithEmptyPassword() {
        MailRuLoginPage mailRuLoginPage = new MailRuLoginPage(USER_EMAIL, "");
        Assert.assertTrue(mailRuLoginPage.isPasswordIncorrect());
    }

    @Test
    public void testImpossibilityLoginToMailRuWithEmptyEmail() {
        MailRuLoginPage mailRuLoginPage = new MailRuLoginPage("", USER_PASSWORD);
        Assert.assertTrue(mailRuLoginPage.isEmailEmpty());
    }

}
