package com.jasur.epam.gmail;

import com.jasur.epam.core.BaseSeleniumTest;
import com.jasur.epam.core.Letter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class YandexMailInboxPageTest extends BaseSeleniumTest {
    private static final String USER_LOGIN = "t3stepam";
    private static final String USER_EMAIL = "t3stepam@yandex.ru";
    private static final String USER_PASSWORD = "b3)Q#9&Ly";
    private static final String COMPANION_EMAIL = "t3st.epam@mail.ru";
    private static final String SENT_LETTER_SUBJECT = "Test";
    private static final String SENT_LETTER_MESSAGE = "Hello, test user Mail!";
    private static final String RECEIVED_LETTER_SUBJECT = "Test";
    private static final String RECEIVED_LETTER_MESSAGE = "Hello, test user yandex mail!";


    private YandexMailInboxPage yandexMailInboxPage;

    @Before
    public void login() {
        YandexMailLoginPage loginPage = new YandexMailLoginPage(USER_LOGIN, USER_PASSWORD);
        yandexMailInboxPage = loginPage.login();
    }
    @Test
    public void testSuccessfulLetterSent() {
        Assert.assertTrue(yandexMailInboxPage.isSuccessfulLetter(
                new Letter(COMPANION_EMAIL, SENT_LETTER_SUBJECT, SENT_LETTER_MESSAGE, USER_EMAIL)
        ));
    }

    @Test
    public void testUnableToSendLetterWithoutReceiver() {
        Assert.assertTrue(yandexMailInboxPage.isLetterWithoutReceiver(
                new Letter("", SENT_LETTER_SUBJECT, SENT_LETTER_MESSAGE, USER_EMAIL)
        ));
    }

    @Test
    public void testReceivingLetter() {
        Letter sentLetter =  new Letter(USER_EMAIL, RECEIVED_LETTER_SUBJECT, RECEIVED_LETTER_MESSAGE, COMPANION_EMAIL);
        WebElement receivedLetterElement = yandexMailInboxPage.receiveLetter(sentLetter);
        Assert.assertNotNull(receivedLetterElement);
        Letter readLetter = yandexMailInboxPage.readLetter(receivedLetterElement);
        Assert.assertEquals(sentLetter, readLetter);
    }
}
