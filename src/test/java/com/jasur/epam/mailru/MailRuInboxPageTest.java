package com.jasur.epam.mailru;

import com.jasur.epam.core.BaseSeleniumTest;
import com.jasur.epam.core.Letter;
import org.junit.Assert;
import org.junit.Test;

public class MailRuInboxPageTest extends BaseSeleniumTest {
    private static final String USER_EMAIL = "epamtestuser@mail.ru";
    private static final String USER_PASSWORD = "b2)Q#9&Ly";
    private static final String LETTER_RECEIVER_EMAIL = "t3stepam@mail.yandex.ru";
    private static final String LETTER_SUBJECT = "Test";
    private static final String LETTER_MESSAGE = "Hello, test user yandex mail!";

    @Test
    public void testSuccessfulLetterSent() {
        MailRuLoginPage loginPage = new MailRuLoginPage(USER_EMAIL, USER_PASSWORD);
        MailRuInboxPage mailRuInboxPage = loginPage.login();
        Assert.assertTrue(mailRuInboxPage.isSuccessfulLetter(
                new Letter(LETTER_RECEIVER_EMAIL, LETTER_SUBJECT, LETTER_MESSAGE)
        ));
    }

    @Test
    public void testUnableToSendLetterWithoutReceiver() {
        MailRuLoginPage loginPage = new MailRuLoginPage(USER_EMAIL, USER_PASSWORD);
        MailRuInboxPage mailRuInboxPage = loginPage.login();
        Assert.assertTrue(mailRuInboxPage.isLetterWithoutReceiver(
                new Letter("", LETTER_SUBJECT, LETTER_MESSAGE)
        ));
    }

    @Test
    public void testUnableToSendLetterWithoutMessage() {
        MailRuLoginPage loginPage = new MailRuLoginPage(USER_EMAIL, USER_PASSWORD);
        MailRuInboxPage mailRuInboxPage = loginPage.login();
        Assert.assertTrue(mailRuInboxPage.isLetterWithoutMessage(
                new Letter(LETTER_RECEIVER_EMAIL, LETTER_SUBJECT, "")
        ));
    }
}
