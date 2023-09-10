package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.testng.Assert;
import org.testng.annotations.*;

public class ExceptionsTests {
    private WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    private void setUp(@Optional("chrome") String browser) {
        //Create driver
        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
                driver = new ChromeDriver();
                break;
            case "firefox":
                GeckoDriverService service = new GeckoDriverService.Builder().withLogOutput(System.out).build();
                System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
                driver = new FirefoxDriver(service);
                break;
            default:
                System.out.println("Do not know how to start" + browser + " , starting chrome instead");
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
                driver = new ChromeDriver();
                break;
        }
        //maximize browser window
        driver.manage().window().maximize();
    }

    @Test
    public void noSuchElementExceptionTest(){
        //Test case 1: NoSuchElementException
        //Open page
        String url = "https://practicetestautomation.com/practice-test-exceptions/";
        driver.get(url);

        //Click Add button
        WebElement addButton = driver.findElement(By.xpath("/html//button[@id='add_btn']"));
        addButton.click();

        try{
            Thread.sleep(10000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        //Verify Row 2 input field is displayed
        WebElement rowTwoInputField = driver.findElement(By.xpath("//div[@id='row2']/input"));
        Assert.assertTrue(rowTwoInputField.isDisplayed(), "Row 2 input field is not displayed");
    }

    @AfterMethod(alwaysRun = true)
    private void tearDown() {
        //close browser
        driver.quit();
    }
}
