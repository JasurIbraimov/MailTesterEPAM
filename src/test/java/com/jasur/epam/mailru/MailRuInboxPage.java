package com.jasur.epam.mailru;

import com.jasur.epam.core.BaseSeleniumPage;
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
}
