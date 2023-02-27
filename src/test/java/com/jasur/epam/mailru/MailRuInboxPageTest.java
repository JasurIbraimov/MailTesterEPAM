package com.jasur.epam.mailru;

import com.jasur.epam.core.BaseSeleniumTest;
import com.jasur.epam.core.Letter;
import com.jasur.epam.core.TestValue;
import com.jasur.epam.yandex.YandexMailInboxPage;
import com.jasur.epam.yandex.YandexMailLoginPage;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class MailRuInboxPageTest extends BaseSeleniumTest {
    private MailRuInboxPage mailRuInboxPage;

    public void login() {
        MailRuLoginPage loginPage = new MailRuLoginPage(
                TestValue.MAIL_RU_USER_EMAIL,
                TestValue.MAIL_RU_USER_PASSWORD
        );
        mailRuInboxPage = loginPage.login();
    }
    @Test
    public void testSuccessfulLetterSent() {
        login();
        Assert.assertTrue(mailRuInboxPage.isSuccessfulLetter(
                new Letter(
                        TestValue.YANDEX_USER_EMAIL,
                        TestValue.MAIL_RU_LETTER_SUBJECT,
                        TestValue.MAIL_RU_LETTER_MESSAGE,
                        TestValue.MAIL_RU_USER_EMAIL
                )
        ));
    }

    @Test
    public void testUnableToSendLetterWithoutReceiver() {
        login();
        Assert.assertTrue(mailRuInboxPage.isLetterWithoutReceiver(
                new Letter(
                        "",
                        TestValue.MAIL_RU_LETTER_SUBJECT,
                        TestValue.MAIL_RU_LETTER_MESSAGE,
                        TestValue.MAIL_RU_USER_EMAIL
                )
        ));
    }

    @Test
    public void testUnableToSendLetterWithoutMessage() {
        login();
        Assert.assertTrue(mailRuInboxPage.isLetterWithoutMessage(
                new Letter(
                        TestValue.YANDEX_USER_EMAIL,
                        TestValue.MAIL_RU_LETTER_SUBJECT,
                        "",
                        TestValue.MAIL_RU_USER_EMAIL
                )
        ));
    }

    @Test
    public void testReceivingLetter() {
        Letter sentLetter =  new Letter(
                TestValue.MAIL_RU_USER_EMAIL,
                TestValue.YANDEX_LETTER_SUBJECT,
                TestValue.YANDEX_LETTER_MESSAGE,
                TestValue.YANDEX_USER_EMAIL
        );
        YandexMailLoginPage yandexMailLoginPage = new YandexMailLoginPage(
                TestValue.YANDEX_USER_LOGIN,
                TestValue.YANDEX_USER_PASSWORD
        );
        YandexMailInboxPage yandexMailInboxPage = yandexMailLoginPage.login();
        yandexMailInboxPage.writeLetter(sentLetter);
        tearDown();
        setUp();
        login();
        WebElement receivedLetterElement = mailRuInboxPage.receiveLetter(sentLetter);
        Assert.assertNotNull(receivedLetterElement);
        Letter readLetter = mailRuInboxPage.readLetter(receivedLetterElement);
        Assert.assertEquals(sentLetter, readLetter);
    }
}
