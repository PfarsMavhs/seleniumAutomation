package selenium.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import selenium.base.CodeBase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReporting extends CodeBase {

    public static ExtentSparkReporter sparkReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    public static  void extentInitializer(){
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

    public static void endExtentReport(){
        extent.flush();
    }

    public static ExtentTest createTest(String strComment){
        return extent.createTest(strComment);

    }

    public static void tearDown(ITestResult result) throws IOException{
        if(result.getStatus() == ITestResult.FAILURE){
            test.log(Status.FAIL, "Test Case Failed is "+ result.getName());
            test.log(Status.FAIL, "Test Case Failed is "+ result.getThrowable());

            String screenshotPath =  ExtentReporting.getScreenshot(driver, result.getName());

            test.addScreenCaptureFromPath(screenshotPath);
        }else if(result.getStatus() == ITestResult.SKIP){
            test.log(Status.SKIP, "Test case Skipped " + result.getName());
        }else if(result.getStatus() == ITestResult.SUCCESS){
            test.log(Status.PASS, "Test Case Passed Is " + result.getName());
        }
    }

    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException{
        String dateName  = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File file = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(file, finalDestination);
        return destination;
    }

}
