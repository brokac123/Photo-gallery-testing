import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class SearchWithEmptyInputTest {
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
    public void testSearchWithEmptyInput() {
        try {

            driver.get(baseUrl);


            WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app/master-layout/div/main/div/loader-component/div/home-route/div/cover/div[1]/form/input")));
            searchField.click();

            searchField.clear();

            // Submit empty search
            searchField.sendKeys(Keys.SPACE);
            searchField.sendKeys(Keys.ENTER);


            // Wait for and check expected error message
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'The request is invalid.')]")));
            Assert.assertTrue(errorMsg.isDisplayed(), "Expected error message was not displayed for empty search.");

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
