package ru.iq.test.page;

import cucumber.api.java.ru.И;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static ru.iq.test.page.AutorizePage.*;
public class InBoxPage {

    @И("нажата кнопка написать письмо")
    public void нажатаКнопкаНаписатьПисьмо() throws InterruptedException {
        WebDriverWait ulWait = new WebDriverWait(driver, 10);
        ulWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[contains(text(), \"Написать письмо\")]")));
        getDriver().findElement(By.xpath("//span[contains(text(), \"Написать письмо\")]")).click();

    }

    @И("заполнено поле Кому")
    public void заполненоПолеКому() throws InterruptedException {
        WebDriverWait ulWait = new WebDriverWait(driver, 10);
        ulWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//input[contains(concat(' ', @class, ' '), 'container--H9L5q size_s--3_M-_')]")));
        getDriver().findElement(By.xpath("//input[contains(concat(' ', @class, ' '), 'container--H9L5q size_s--3_M-_')]")).clear();
        getDriver().findElement(By.xpath("//input[contains(concat(' ', @class, ' '), 'container--H9L5q size_s--3_M-_')]")).sendKeys(properties.getProperty("To"));
    }

    @И("заполнено поле Тема")
    public void заполненоПолеТема() throws InterruptedException {
        WebDriverWait ulWait = new WebDriverWait(driver, 10);
        ulWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//input[@name='Subject']")));
        getDriver().findElement(By.xpath("//input[@name='Subject']")).clear();
        getDriver().findElement(By.xpath("//input[@name='Subject']")).sendKeys(properties.getProperty("Subject"));
    }

    @И("заполнено тело письма")
    public void заполненоТелоПисьма() throws InterruptedException {
        Actions assigner = new Actions(getDriver());
        assigner.moveToElement(getDriver().findElement(By.xpath("//div[@role='textbox']")));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("return arguments[0].innerText =''", getDriver().findElement(By.xpath("//div[@role='textbox']")));
        assigner.click();
        assigner.sendKeys(properties.getProperty("Body"));
        assigner.build().perform();
        Thread.sleep(2000);
    }

    @И("нажата кнопка Отправить письмо")
    public void нажатаКнопкаОтправитьПисьмо() throws InterruptedException {
        WebDriverWait ulWait = new WebDriverWait(driver, 10);
        ulWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//button[@data-test-id='send']")));
        getDriver().findElement(By.xpath("//button[@data-test-id='send']")).click();
        ulWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='layer__header']")));

    }
}
