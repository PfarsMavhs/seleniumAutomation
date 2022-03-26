package testcase;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import selenium.base.CodeBase;
import selenium.pages.LoginPage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginPageTest extends CodeBase {
    LoginPage loginPage;
    public  ExtentSparkReporter sparkReporter;
    public  ExtentReports extent;
    public  ExtentTest test;

    public LoginPageTest(){
        super();
    }

    @BeforeClass
    public void setup(){
        initialization();
        loginPage = new LoginPage();

    }
    @BeforeTest
    public void setExtentReport(){
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/test-output/citriiReport.html");

        sparkReporter.config().setDocumentTitle("Login Automation Report");
        sparkReporter.config().setReportName("Functional Report");
        sparkReporter.config().setTheme(Theme.DARK);

        extent=new ExtentReports();

        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Hostname", "LocalHost");
        extent.setSystemInfo("OS", "Windows10");
        extent.setSystemInfo("Tester Name", "Pfariso");
        extent.setSystemInfo("Browser", prop.getProperty("browser"));
    }

    @Test(priority = 0)
    public void verifyLoginPage(){
        test = extent.createTest("verifyLoginPage");
        Assert.assertEquals(loginPage.verifyIfLoginPage(),"Citrii");
    }

    @Test(priority = 1)
    public void loginPage()  throws InterruptedException{
       test =  extent.createTest("loginPage");
        loginPage.login(prop.getProperty("username"),prop.getProperty("password"));
    }

    @Test(priority = 2)
    public void verifyErrorMessage()throws InterruptedException{
        test = extent.createTest("verifyErrorMessage");
        Assert.assertEquals(loginPage.errorPage(),"Invalid username or password");
    }

    @AfterTest
    public void endReportSetup(){
        extent.flush();
    }

    @AfterMethod
    public void terry(ITestResult result) throws  IOException{
        if(result.getStatus() == ITestResult.FAILURE){
            test.log(Status.FAIL, "Test Case Failed is "+ result.getName());
            test.log(Status.FAIL, "Test Case Failed is "+ result.getThrowable());

            String screenshotPath =  LoginPageTest.getScreenshot(driver, result.getName());
            test.log(Status.FAIL,screenshotPath);
            test.addScreenCaptureFromPath(screenshotPath);

        }else if(result.getStatus() == ITestResult.SKIP){
            test.log(Status.SKIP, "Test case Skipped " + result.getName());

        }else if(result.getStatus() == ITestResult.SUCCESS){
            String screenshotPath =  LoginPageTest.getScreenshot(driver, result.getName());
            test.log(Status.PASS,screenshotPath);
            test.addScreenCaptureFromPath(screenshotPath);
            test.log(Status.PASS, "Test Case Passed Is " + result.getName());

        }
    ;
    }
    @AfterClass
    public void testClose(){
        driver.quit();
    }

    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException{
        String dateName  = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File file = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String destination = System.getProperty("user.dir") + "/ScreenshotsTaken/" + screenshotName + dateName + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(file, finalDestination);
        return destination;
    }


}
