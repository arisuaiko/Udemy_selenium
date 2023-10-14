package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

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

        //Explicit wait
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement rowTwoInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

        //Verify Row 2 input field is displayed
        Assert.assertTrue(rowTwoInputField.isDisplayed(), "Row 2 input field is not displayed");
    }

    @Test
    public void noIntractableElementExceptionTest(){
        //Test case 2: ElementNotInteractableException
        //Open page
        String url = "https://practicetestautomation.com/practice-test-exceptions/";
        driver.get(url);

        //Click Add button
        WebElement addButton = driver.findElement(By.xpath("/html//button[@id='add_btn']"));
        addButton.click();

        //Explicit wait
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement rowTwoInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

        //Type text into the second input field
        rowTwoInputField.sendKeys("Sushi");

        //Push Save button using locator By.name(“Save”)
        WebElement saveButton = driver.findElement(By.xpath("//div[@id='row2']/button[@name='Save']"));
        saveButton.click();

        //Verify text saved
        WebElement confirmationText = driver.findElement(By.xpath("/html//div[@id='confirmation']"));
        String expectedMessage = "Row 2 was saved";
        String actualMessage = confirmationText.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Actual message does not contain expected message.\nActual message: " + actualMessage + "\nExpected Message: " + expectedMessage);
    }

    @AfterMethod(alwaysRun = true)
    private void tearDown() {
        //close browser
        driver.quit();
    }
}
