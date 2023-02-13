package com.jasur.epam.gmail;

import com.jasur.epam.core.BaseSeleniumTest;
import com.jasur.epam.core.Letter;
import org.junit.Assert;
import org.junit.Test;

public class YandexMailInboxPageTest extends BaseSeleniumTest {
    private static final String USER_LOGIN = "t3stepam";
    private static final String USER_PASSWORD = "b3)Q#9&Ly";
    private static final String LETTER_RECEIVER_EMAIL = "t3st.epam@mail.ru";
    private static final String LETTER_SUBJECT = "Test";
    private static final String LETTER_MESSAGE = "Hello, test user mailRU!";

    @Test
    public void testSuccessfulLetterSent() {
        YandexMailLoginPage loginPage = new YandexMailLoginPage(USER_LOGIN, USER_PASSWORD);
        YandexMailInboxPage yandexMailInboxPage = loginPage.login();
        Assert.assertTrue(yandexMailInboxPage.isSuccessfulLetter(
                new Letter(LETTER_RECEIVER_EMAIL, LETTER_SUBJECT, LETTER_MESSAGE)
        ));
    }

    @Test
    public void testUnableToSendLetterWithoutReceiver() {
        YandexMailLoginPage loginPage = new YandexMailLoginPage(USER_LOGIN, USER_PASSWORD);
        YandexMailInboxPage yandexMailInboxPage = loginPage.login();
        Assert.assertTrue(yandexMailInboxPage.isLetterWithoutReceiver(
                new Letter("", LETTER_SUBJECT, LETTER_MESSAGE)
        ));
    }
}
