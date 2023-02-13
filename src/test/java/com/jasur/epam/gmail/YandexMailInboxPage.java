package com.jasur.epam.gmail;

import com.jasur.epam.core.BaseSeleniumPage;
import com.jasur.epam.core.Letter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
public class YandexMailInboxPage extends BaseSeleniumPage {
    @FindBy(xpath = "//a[@href=\"#compose\"]")
    private WebElement writeLetterLink;

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

    public boolean isSuccessfulLetter(Letter letter) {
        writeLetter(letter);
        return elementExists(By.className("ComposeDoneScreen"));
    }

    public boolean isLetterWithoutReceiver(Letter letter) {
        writeLetter(letter);
        return elementExists(By.className("ComposeConfirmPopup-Button"));
    }
}
