import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class RegisterWithExistingUsernameTest {

    WebDriver driver;
    WebDriverWait wait;
    String baseUrl = "http://demo.baasic.com/angular/starterkit-photo-gallery/main";

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(baseUrl);
    }

    @Test
    public void testRegisterWithExistingUsername() {
        try {
            // 1. Open login menu
            WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("menu")));
            new Actions(driver).moveToElement(logo).perform();

            WebElement menuLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app/master-layout/div/baasic-header/header/div/a/a")));
            menuLink.click();

            WebElement registerLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Register']")));
            registerLink.click();


            WebElement email = driver.findElement(By.id("email"));
            WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));
            WebElement password = driver.findElement(By.id("password"));
            WebElement confirmPassword = driver.findElement(By.id("confirmPassword"));

            email.sendKeys("Baki2507@gmail.com");
            username.sendKeys("Baki");
            password.sendKeys("SomeValidPass123");
            confirmPassword.sendKeys("SomeValidPass123");


            WebElement registerBtn = driver.findElement(By.cssSelector("button[type='submit']"));
            registerBtn.click();


            //Verify error message appears
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Username taken!')]")));
            Assert.assertTrue(errorMsg.isDisplayed(), "Expected error message 'Username taken!' not displayed.");

        } catch (Exception e) {
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
