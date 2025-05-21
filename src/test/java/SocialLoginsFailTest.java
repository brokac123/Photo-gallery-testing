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

public class SocialLoginsFailTest {
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
    public void testAllSocialLoginsFail() {
        try {

            WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("menu")));
            new Actions(driver).moveToElement(logo).perform();

            WebElement menuLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app/master-layout/div/baasic-header/header/div/a/a")));
            menuLink.click();

            WebElement loginLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Login']")));
            loginLink.click();

            String[] socialClasses = {
                    "btn--social--facebook",
                    "btn--social--twitter",
                    "btn--social--google",
                    "btn--social--github"
            };

            for (String className : socialClasses) {
                // Attempt login with each available social media option to verify whether authentication is possible or returns an expected error
                WebElement socialBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button." + className)));
                socialBtn.click();

                WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'undefined: Social login configuration not found.')]")));
                Assert.assertTrue(errorMsg.isDisplayed(), "Expected error message not shown for social login: " + className);


                driver.navigate().refresh();

                // Reopen login after refresh
                logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("menu")));
                new Actions(driver).moveToElement(logo).perform();
                menuLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app/master-layout/div/baasic-header/header/div/a/a")));
                menuLink.click();
                loginLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Login']")));
                loginLink.click();
            }

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
