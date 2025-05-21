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

public class CreateNewAlbumTest {
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
    public void testCreateNewAlbum() {
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


            WebElement createAlbumBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn--primary")));
            createAlbumBtn.click();


            WebElement albumNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("albumName")));
            albumNameInput.sendKeys("Testni Album");


            WebElement saveAlbumBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
            saveAlbumBtn.click();


            WebElement placeholder = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.placeholder")));
            placeholder.click();


            WebElement uploadInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("photoInput")));
            uploadInput.sendKeys("C:\\Users\\lukab\\IdeaProjects\\Testiranje projekti\\Photo Gallery Starter Kit Testing\\src\\test\\images\\japan.jpg");

            WebElement uploadCoverBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit'].btn--primary")));
            uploadCoverBtn.click();
            Thread.sleep(4000); // Wait for album to be completely uploaded
            driver.navigate().refresh();


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
