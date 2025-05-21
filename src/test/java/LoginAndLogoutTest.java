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

public class LoginAndLogoutTest {

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
    public void testSuccessfulLoginAndLogout() {
        try {

            // Login part

            WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("menu")));
            Actions actions = new Actions(driver);
            actions.moveToElement(logo).perform(); // Hover over logo (menu)


            WebElement menuLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app/master-layout/div/baasic-header/header/div/a/a")));
            menuLink.click();


            WebElement loginLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Login']")));
            loginLink.click();


            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
            WebElement passwordField = driver.findElement(By.name("password"));
            WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

            usernameField.sendKeys("Baki");
            passwordField.sendKeys("0okju76tfd");
            loginButton.click();

            //Logout part

            actions.moveToElement(logo).perform();
            menuLink.click();
            WebElement logoutLink= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Log out']")));
            logoutLink.click();


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
