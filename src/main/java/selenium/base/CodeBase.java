package selenium.base;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import selenium.util.TestUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class CodeBase {

    public static WebDriver driver;
    public static Properties prop;


    public CodeBase(){
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream( "src//main//java//selenium//config//config.properties");
            prop.load(ip);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void initialization() throws InterruptedException {
        String browserName = prop.getProperty("browser");

        if(browserName.equals("fox")){
            System.setProperty("webdriver.gecko.driver","browser-files//geckodriver.exe");
            driver = new FirefoxDriver();
        }
        else if(browserName.equals("chrome")){
            System.setProperty("webdriver.chrome.driver","browser-files//chromedriver.exe");
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        Thread.sleep(2000);
        driver.get(prop.getProperty("url"));
    }


}
