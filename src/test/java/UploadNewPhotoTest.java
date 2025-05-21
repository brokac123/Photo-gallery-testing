
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class UploadNewPhotoTest {

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
    public void testUploadNewPhoto() {
        try {


            //Login part
            WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("menu")));
            Actions actions = new Actions(driver);
            actions.moveToElement(logo).perform();

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

           //Uploading a new photo part

           WebElement thumbnailImage= wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app/master-layout/div/main/div/loader-component/div/profile-detail/baasic-album-list/div/div[2]/div/span/div[2]")));
           thumbnailImage.click();

           WebElement uploadPhotoButton= wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app/master-layout/div/main/div/loader-component/div/baasic-photo-list-route/baasic-album-detail/div/div[1]/div[3]/button")));
           uploadPhotoButton.click();

            WebElement uploadButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app/master-layout/div/main/div/loader-component/div/baasic-photo-upload-route/baasic-photo-upload/div/form/div[2]/div[1]")));
            uploadButton.click();

            //Thread.sleep(2000); // Used only to visually observe the step during test review.
           WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']")));
           String imagePath = "C:\\Users\\lukab\\IdeaProjects\\Testiranje projekti\\Photo Gallery Starter Kit Testing\\src\\test\\images\\owl.jpg";
           fileInput.sendKeys(imagePath);
         //Thread.sleep(4000); // Used only to visually observe the step during test review.
           WebElement finalUploadButton = driver.findElement(By.xpath("/html/body/app/master-layout/div/main/div/loader-component/div/baasic-photo-upload-route/baasic-photo-upload/div/form/div[2]/div[2]/div[3]/button"));
           finalUploadButton.click();
           Thread.sleep(7000); // Used only to visually observe the step during test review and confirm everything works properly.
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
