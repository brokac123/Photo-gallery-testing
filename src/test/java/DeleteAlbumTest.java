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

public class DeleteAlbumTest {
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
    public void testDeleteAlbum() {
        try {

            WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("menu")));
            new Actions(driver).moveToElement(logo).perform();

            WebElement menuLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app/master-layout/div/baasic-header/header/div/a/a")));
            menuLink.click();

            WebElement loginLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Login']")));
            loginLink.click();

            WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
            WebElement password = driver.findElement(By.name("password"));
            WebElement loginBtn = driver.findElement(By.cssSelector("button[type='submit']"));
            username.sendKeys("Baki");
            password.sendKeys("0okju76tfd");
            loginBtn.click();



            new Actions(driver).moveToElement(logo).perform();
            menuLink.click();
            WebElement profileLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Profile']")));
            //Navigate to Profile page
            profileLink.click();


            WebElement albumOverlay = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.thumbnail__img")));
            new Actions(driver).moveToElement(albumOverlay).perform();

            WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.thumbnail__info__delete")));
            deleteBtn.click();


            WebElement confirmDelete = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn--warning")));
            confirmDelete.click();


            Thread.sleep(2500); // allow time for deletion to complete

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
