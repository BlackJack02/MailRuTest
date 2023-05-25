package ru.iq.test.page;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.ru.И;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class AutorizePage {

    public static WebDriver driver;
    public static JavascriptExecutor jse;
    public static Properties properties;

    public static WebDriver getDriver() {
        return driver;
    }

    public static JavascriptExecutor getJse() {
        return jse;
    }

    public static Properties getProperties() {
        return properties;
    }

    @Before
    public static void initDriver() throws Exception {
        String sys =System.getProperty("os.name");
        if (sys.contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        } else
           if (sys.contains("Linux")){
              System.setProperty("webdriver.chrome.driver", "/opt/chromedriver");
           }

        properties = new Properties();
        InputStream input = new FileInputStream("target/classes/config.properties");
        properties.load(input);

        driver = new ChromeDriver();
//        driver.manage().window().maximize();
        driver.manage().window().setSize(new Dimension(1600, 950));
        driver.get(properties.getProperty("Environment"));

        jse = (JavascriptExecutor) driver;

            LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
            for (LogEntry entry : logEntries) {
                System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());

        }
    }

    @After
    public static void end(Scenario scenario) throws Exception {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
        }

        int iterationsCount = 10;
        while (iterationsCount > 0) {
            try {
                driver.quit();
                break;
            } catch (Exception e) {
                Thread.sleep(1000);
                iterationsCount--;
            }
        }
    }


    @И("выполнена авторизация")
    public void выполненаАвторизация() throws InterruptedException {
        WebDriverWait ulWait = new WebDriverWait(driver, 10);
        ulWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//button[@data-testid='enter-mail-primary']")));
        getDriver().findElement(By.xpath("//button[@data-testid='enter-mail-primary']")).click();
        int size = driver.findElements(By.tagName("iframe")).size();
        for(int i=0; i<size; i++){
            driver.switchTo().frame(i);
            int total=driver.findElements(By.xpath("//input[@name='username']")).size();
            if (total>0){
                ulWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//input[@name='username']")));
                getDriver().findElement(By.xpath("//input[@name='username']")).clear();
                getDriver().findElement(By.xpath("//input[@name='username']")).sendKeys(properties.getProperty("Login"));
                getDriver().findElement(By.xpath("//button[@data-test-id='next-button']")).click();
                ulWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//input[@name='password']")));
                getDriver().findElement(By.xpath("//input[@name='password']")).clear();
                getDriver().findElement(By.xpath("//input[@name='password']")).sendKeys(properties.getProperty("Pass"));
                getDriver().findElement(By.xpath("//button[@data-test-id='submit-button']")).click();
            }
            driver.switchTo().defaultContent();}

    }
}
