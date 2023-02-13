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

    public boolean isSuccessfulLetter(Letter letter) {
        writeLetter(letter);
        return elementExists(By.xpath("//div[@__mediators=\"layout-manager\"]"));
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
