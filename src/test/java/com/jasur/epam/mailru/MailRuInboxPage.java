package com.jasur.epam.mailru;

import com.jasur.epam.core.BaseSeleniumPage;
import com.jasur.epam.core.Letter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MailRuInboxPage extends BaseSeleniumPage {
    @FindBy(xpath = "//a[@data-title-shortcut=\"N\"]")
    private WebElement writeLetterLink;

    public MailRuInboxPage(){
        PageFactory.initElements(driver, this);
    }

    public boolean isWriteLetterLinkPresented() {
        return writeLetterLink.isDisplayed();
    }

    public void writeLetter(Letter letter) {
        writeLetterLink.click();
        WebElement letterReceiverEmailInput =  driver.findElement(By.xpath("//div[@data-name=\"to\"]//input"));
        letterReceiverEmailInput.sendKeys(letter.receiver());
        WebElement letterSubjectInput = driver.findElement(By.name("Subject"));
        letterSubjectInput.sendKeys(letter.subject());
        WebElement letterTextBox = driver.findElement(By.xpath("//div[@role=\"textbox\"]//div"));
        letterTextBox.sendKeys(letter.message());
        WebElement sendButton = driver.findElement(By.xpath("//button[@data-test-id=\"send\"]"));
        sendButton.click();

    }
//    public WebElement receiveLetter(Letter sentLetter) {
//        WebElement unreadLetter = driver.findElement(By.className(".mail-MessageSnippet.is-unread"));
//        WebElement letterFromTextElement = unreadLetter.findElement(By.className("mail-MessageSnippet-FromText"));
//        WebElement letterSubjectElement = unreadLetter.findElement(By.cssSelector(".mail-MessageSnippet-Item_subject span"));
//
//        if (letterFromTextElement.getAttribute("title").equals(sentLetter.sender())
//                && letterSubjectElement.getText().equals(sentLetter.subject())) {
//            return unreadLetter;
//        }
//        return null;
//    }
//
//    public Letter readLetter(WebElement receivedLetter) {
//        receivedLetter.click();
//        String receivedLetterRecipient = userAccountNameElement.getText() + "@yandex.ru";
//        String receivedLetterSubjectText = driver.findElement(By.className("Title_subject_tyZv5")).getText();
//        String receivedLetterSenderEmail = driver.findElement(By.className("Sender_email_iWFMG")).getText();
//        ArrayList<WebElement> receivedLetterBodyElements = (ArrayList<WebElement>) driver.findElements(By.cssSelector(".MessageBody_body_pmf3j > div"));
//        StringBuilder receivedLetterBodyMessage = new StringBuilder();
//        for (int i = 0; i < receivedLetterBodyElements.size() - 2; i++) {
//            receivedLetterBodyMessage.append(receivedLetterBodyElements.get(i).getText());
//        }
//        return new Letter(
//                receivedLetterRecipient,
//                receivedLetterSubjectText,
//                receivedLetterBodyMessage.toString(),
//                receivedLetterSenderEmail
//        );
//    }
    public boolean isSuccessfulLetter(Letter letter) {
        writeLetter(letter);
        if (elementExists(By.xpath("//div[@__mediators=\"layout-manager\"]"))) {
            WebElement closeButton = driver.findElement(By.className("button2_close"));
            closeButton.click();
            return true;
        }
        return false;
    }

    public boolean isLetterWithoutMessage(Letter letter) {
        writeLetter(letter);
        return elementExists(By.xpath("//div[@data-test-id=\"confirmation:empty-letter\"]"));
    }

    public boolean isLetterWithoutReceiver(Letter letter) {
        writeLetter(letter);
        return elementExists(By.xpath("//div[@data-name=\"to\"]/following-sibling::div"));
    }


}
