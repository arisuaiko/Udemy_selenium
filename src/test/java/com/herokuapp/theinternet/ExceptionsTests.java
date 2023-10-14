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

    @Test
    public void invalidElementStateExceptionTest(){
        //Test case 3: InvalidElementStateException
        //Open page
        String url = "https://practicetestautomation.com/practice-test-exceptions/";
        driver.get(url);

        //Clear input field
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement rowOneInputField = driver.findElement(By.xpath("//div[@id='row1']/input"));
        WebElement editButton = driver.findElement(By.xpath("//div[@id='row1']/button[@name='Edit']"));
        editButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(rowOneInputField));
        rowOneInputField.clear();

        //Type text into the input field
        rowOneInputField.sendKeys("Sushi");
        WebElement saveButton = driver.findElement(By.xpath("//div[@id='row1']/button[@name='Save']"));
        saveButton.click();

        //Verify text changed
        String value = rowOneInputField.getAttribute("value");
        Assert.assertEquals(value,"Sushi", "Input 1 field value is not expected");

        //Verify text saved
        WebElement confirmationText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html//div[@id='confirmation']")));
        String expectedMessage = "Row 1 was saved";
        String actualMessage = confirmationText.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Actual message does not contain expected message.\nActual message: " + actualMessage + "\nExpected Message: " + expectedMessage);
    }

    @Test
    public void staleElementReferenceExceptionTest(){
        //Test case 4: StaleElementReferenceException
        //Open page
        String url = "https://practicetestautomation.com/practice-test-exceptions/";
        driver.get(url);

        //Find the instructions text element
        WebElement instructionsText = driver.findElement(By.xpath("/html//p[@id='instructions']"));
        instructionsText.isDisplayed();

        //Push add button
        WebElement addButton = driver.findElement(By.xpath("/html//button[@id='add_btn']"));
        addButton.click();

        //Verify instruction text element is no longer displayed
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("instructions"))),"Instructions are still displayed");
    }

    @Test
    public void timeoutExceptionTest(){
        //Test case 5: TimeoutException
        //Open page
        String url = "https://practicetestautomation.com/practice-test-exceptions/";
        driver.get(url);

        //Click Add button
        WebElement addButton = driver.findElement(By.xpath("/html//button[@id='add_btn']"));
        addButton.click();

        //Wait for 3 seconds for the second input field to be displayed
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(6));
        WebElement rowTwoInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

        //Verify second input field is displayed
        Assert.assertTrue(rowTwoInputField.isDisplayed(), "Row 2 input field is not displayed");
    }

    @AfterMethod(alwaysRun = true)
    private void tearDown() {
        //close browser
        driver.quit();
    }
}
