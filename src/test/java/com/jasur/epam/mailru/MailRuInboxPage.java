package com.jasur.epam.mailru;

import com.jasur.epam.core.BaseSeleniumPage;
import com.jasur.epam.core.Letter;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

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
    public WebElement receiveLetter(Letter sentLetter) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By letterXpath = By.xpath("//div[contains(@class, 'llc__avatar_unread')]/parent::a");
        WebElement unreadLetter = null;
        while(unreadLetter == null) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(letterXpath));
                unreadLetter = driver.findElement(letterXpath);
            } catch (TimeoutException e) {
                driver.navigate().refresh();
            }
        }
        WebElement senderElement = unreadLetter.findElement(By.cssSelector(".llc__item_correspondent span"));
        WebElement letterSubjectElement = unreadLetter.findElement(By.cssSelector(".llc__subject span"));
        String senderElementTitle = senderElement.getAttribute("title");
        String senderEmail = senderElementTitle.substring(senderElementTitle.indexOf('<') + 1, senderElementTitle.indexOf('>'));
        if (senderEmail.equals(sentLetter.sender())
                && letterSubjectElement.getText().equals(sentLetter.subject())) {
            return unreadLetter;
        }
        return null;
    }

    public Letter readLetter(WebElement receivedLetter) {
        receivedLetter.click();
        String receivedLetterRecipient = driver.findElement(By.cssSelector(".letter__recipients .letter-contact")).getAttribute("title");
        String receivedLetterSubjectText = driver.findElement(By.className("thread-subject")).getText();
        String receivedLetterSenderEmail = driver.findElement(By.cssSelector(".letter__author span")).getAttribute("title");
        WebElement receivedBodyContainer = driver.findElement(By.xpath("//*[starts-with(@id, 'style_') ]/div"));
        ArrayList<WebElement> receivedLetterBodyElements = (ArrayList<WebElement>) receivedBodyContainer.findElements(By.tagName("div"));
        StringBuilder receivedLetterBodyMessage = new StringBuilder();
        for (WebElement receivedLetterBodyElement : receivedLetterBodyElements) {
            receivedLetterBodyMessage.append(receivedLetterBodyElement.getText());
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
