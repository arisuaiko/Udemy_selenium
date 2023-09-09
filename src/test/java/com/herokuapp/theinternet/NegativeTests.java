package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class NegativeTests {

    @Parameters({"username", "password", "expectedMessage"})
    @Test()
    public void negativeLoginTest(String username, String password, String expectedErrorMessage) {
        System.out.println("Starting negativeLoginTest with " + username + " and " + password);

        //Create driver
        GeckoDriverService service = new GeckoDriverService.Builder().withLogOutput(System.out).build();
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
        WebDriver driver = new FirefoxDriver(service);

        //maximize browser window
        driver.manage().window().maximize();

        //open test page
        String url = "https://the-internet.herokuapp.com/login";
        driver.get(url);
        System.out.println("Page is opened");

        //enter username
        WebElement usernameElement = driver.findElement(By.id("username"));
        usernameElement.sendKeys(username);
        //enter password
        WebElement passwordElement = driver.findElement(By.name("password"));
        passwordElement.sendKeys(password);
        //click login button
        WebElement logInButton = driver.findElement(By.tagName("button"));
        logInButton.click();

        //verifications:
        //same url
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, url, "Actual page URL is not the same as expected");

        //unsuccessful login message
        WebElement unSuccessMessage = driver.findElement(By.cssSelector("div#flash"));
        String actualMessage = unSuccessMessage.getText();
        Assert.assertTrue(actualMessage.contains(expectedErrorMessage), "Actual message does not contain expected message.\nActual message: " + actualMessage + "\nExpected Message: " + expectedErrorMessage);

        //close browser
        driver.quit();
    }

 /*   @Test(priority = 2, groups = {"negativeTests"})
    public void negativePasswordTest() {
        System.out.println("Starting negativePasswordTest");

        //Create driver
        GeckoDriverService service = new GeckoDriverService.Builder().withLogOutput(System.out).build();
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
        WebDriver driver = new FirefoxDriver(service);

        //maximize browser window
        driver.manage().window().maximize();

        //open test page
        String url = "https://the-internet.herokuapp.com/login";
        driver.get(url);
        System.out.println("Page is opened");

        //enter username
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("tomsmith");
        //enter password
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("SuperSecret");
        //click login button
        WebElement logInButton = driver.findElement(By.tagName("button"));
        logInButton.click();

        //verifications:
        //same url
        String actualUrl = driver.getCurrentUrl();

        Assert.assertEquals(actualUrl, url, "Actual page URL is not the same as expected");

        //unsuccessful login message
        WebElement unSuccessMessage = driver.findElement(By.cssSelector("div#flash"));
        String expectedMessage = "Your password is invalid!";
        String actualMessage = unSuccessMessage.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Actual message does not contain expected message.\nActual message: " + actualMessage + "\nExpected Message: " + expectedMessage);

        //close browser
        driver.quit();
    }*/
}
