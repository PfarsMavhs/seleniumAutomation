package selenium.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.base.CodeBase;
import selenium.util.TestUtil;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.*;

public class LoginPage extends CodeBase {

    @FindBy(xpath ="//input[@id='uxUsername']")
    WebElement userName;

    @FindBy(xpath ="//input[@id='uxPassword']")
    WebElement password;

    @FindBy(xpath = "//span[contains(text(),'Login')]")
    WebElement loginButton;

    //@FindBy(xpath = "//div[contains(text(),'Invalid username or password'")
    @FindBy(xpath = "//div[contains(text(),'Unable to login')]")
    WebElement titleToast;

    @FindBy(xpath = "//div[contains(text(),'Invalid username or password')]")
    WebElement toastMessage;

    public String verifyIfLoginPage(){
        return driver.getTitle();
    }

    public LoginPage(){
        PageFactory.initElements(driver,this);
    }

    public String errorPage() throws InterruptedException {

        sleep(2000);
        if(titleToast.isDisplayed()){
            return toastMessage.getText();
        }else{
            return "null";
        }
    }

    public void login(String username, String Password) throws InterruptedException {
        sleep(1000);
        userName.sendKeys(username);
        sleep(1000);
        password.sendKeys(Password);
        sleep(1000);
        loginButton.click();
    }



}
