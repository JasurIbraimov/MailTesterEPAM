package com.jasur.epam.yandex;

import com.jasur.epam.core.BaseSeleniumTest;
import com.jasur.epam.core.Letter;
import com.jasur.epam.core.TestValue;
import com.jasur.epam.mailru.MailRuInboxPage;
import com.jasur.epam.mailru.MailRuLoginPage;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class YandexMailInboxPageTest extends BaseSeleniumTest {
    private YandexMailInboxPage yandexMailInboxPage;
    public void login() {
        YandexMailLoginPage loginPage = new YandexMailLoginPage(
                TestValue.YANDEX_USER_LOGIN,
                TestValue.YANDEX_USER_PASSWORD
        );
        yandexMailInboxPage = loginPage.login();
    }
    @Test
    public void testSuccessfulLetterSent() {
        login();
        Assert.assertTrue(yandexMailInboxPage.isSuccessfulLetter(
                new Letter(
                        TestValue.MAIL_RU_USER_EMAIL,
                        TestValue.YANDEX_LETTER_SUBJECT,
                        TestValue.YANDEX_LETTER_MESSAGE,
                        TestValue.YANDEX_USER_EMAIL
                )
        ));
    }

    @Test
    public void testUnableToSendLetterWithoutReceiver() {
        login();
        Assert.assertTrue(yandexMailInboxPage.isLetterWithoutReceiver(
                new Letter(
                        "",
                        TestValue.YANDEX_LETTER_SUBJECT,
                        TestValue.YANDEX_LETTER_MESSAGE,
                        TestValue.YANDEX_USER_EMAIL
                )
        ));
    }

    @Test
    public void testReceivingLetter()  {
        Letter sentLetter =  new Letter(
                TestValue.YANDEX_USER_EMAIL,
                TestValue.MAIL_RU_LETTER_SUBJECT,
                TestValue.MAIL_RU_LETTER_MESSAGE,
                TestValue.MAIL_RU_USER_EMAIL
        );
        MailRuLoginPage mailRuLoginPage = new MailRuLoginPage(
                TestValue.MAIL_RU_USER_EMAIL,
                TestValue.MAIL_RU_USER_PASSWORD
        );
        MailRuInboxPage mailRuInboxPage = mailRuLoginPage.login();
        mailRuInboxPage.writeLetter(sentLetter);
        tearDown();
        setUp();
        login();
        WebElement receivedLetterElement = yandexMailInboxPage.receiveLetter(sentLetter);
        Assert.assertNotNull(receivedLetterElement);
        Letter readLetter = yandexMailInboxPage.readLetter(receivedLetterElement);
        Assert.assertEquals(sentLetter, readLetter);
    }
}
