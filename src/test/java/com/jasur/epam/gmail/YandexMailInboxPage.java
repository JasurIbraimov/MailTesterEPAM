package com.jasur.epam.gmail;

import com.jasur.epam.core.BaseSeleniumPage;
import com.jasur.epam.core.Letter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.ArrayList;

public class YandexMailInboxPage extends BaseSeleniumPage {
    @FindBy(xpath = "//a[@href=\"#compose\"]")
    private WebElement writeLetterLink;
    @FindBy(className = "user-account__name")
    private WebElement userAccountNameElement;

    public YandexMailInboxPage(){
        PageFactory.initElements(driver, this);
    }

    public boolean isWriteLetterLinkPresented() {
        return writeLetterLink.isDisplayed();
    }

    public void writeLetter(Letter letter) {
        writeLetterLink.click();
        WebElement letterReceiverEmailInput =  driver.findElement(By.id("compose-field-1"));
        letterReceiverEmailInput.sendKeys(letter.receiver());
        WebElement letterSubjectInput = driver.findElement(By.name("subject"));
        letterSubjectInput.sendKeys(letter.subject());
        WebElement letterTextBox = driver.findElement(By.xpath("//div[@role=\"textbox\"]"));
        letterTextBox.sendKeys(letter.message());
        WebElement sendButton = driver.findElement(By.cssSelector(".ComposeSendButton button"));
        sendButton.click();
    }
    public WebElement receiveLetter(Letter sentLetter) {
        WebElement unreadLetter = driver.findElement(By.className(".mail-MessageSnippet.is-unread"));
        WebElement letterFromTextElement = unreadLetter.findElement(By.className("mail-MessageSnippet-FromText"));
        WebElement letterSubjectElement = unreadLetter.findElement(By.cssSelector(".mail-MessageSnippet-Item_subject span"));

        if (letterFromTextElement.getAttribute("title").equals(sentLetter.sender())
                && letterSubjectElement.getText().equals(sentLetter.subject())) {
            return unreadLetter;
        }
        return null;
    }

    public Letter readLetter(WebElement receivedLetter) {
        receivedLetter.click();
        String receivedLetterRecipient = userAccountNameElement.getText() + "@yandex.ru";
        String receivedLetterSubjectText = driver.findElement(By.className("Title_subject_tyZv5")).getText();
        String receivedLetterSenderEmail = driver.findElement(By.className("Sender_email_iWFMG")).getText();
        ArrayList<WebElement> receivedLetterBodyElements = (ArrayList<WebElement>) driver.findElements(By.cssSelector(".MessageBody_body_pmf3j > div"));
        StringBuilder receivedLetterBodyMessage = new StringBuilder();
        for (int i = 0; i < receivedLetterBodyElements.size() - 2; i++) {
            receivedLetterBodyMessage.append(receivedLetterBodyElements.get(i).getText());
        }
        return new Letter(
                receivedLetterRecipient,
                receivedLetterSubjectText,
                receivedLetterBodyMessage.toString(),
                receivedLetterSenderEmail
        );
    }

    public boolean isSuccessfulLetter(Letter letter) {
        writeLetter(letter);
        return elementExists(By.className("ComposeDoneScreen"));
    }

    public boolean isLetterWithoutReceiver(Letter letter) {
        writeLetter(letter);
        return elementExists(By.className("ComposeConfirmPopup-Button"));
    }

}
