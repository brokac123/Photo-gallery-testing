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

public class UploadInvalidFileTypeTest {

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
    public void testUploadInvalidFileType() {
        try {

            WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("menu")));
            new Actions(driver).moveToElement(logo).perform();

            WebElement menuLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/app/master-layout/div/baasic-header/header/div/a/a")));
            menuLink.click();

            WebElement loginLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[text()='Login']")));
            loginLink.click();

            WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
            WebElement password = driver.findElement(By.name("password"));
            WebElement loginBtn = driver.findElement(By.cssSelector("button[type='submit']"));
            username.sendKeys("Baki");
            password.sendKeys("0okju76tfd");
            loginBtn.click();


            WebElement albumThumbnail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.thumbnail__img")));
            new Actions(driver).moveToElement(albumThumbnail).perform();

            WebElement overlay = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.thumbnail--overlay")));
            overlay.click();

            WebElement uploadPhotoButton= wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app/master-layout/div/main/div/loader-component/div/baasic-photo-list-route/baasic-album-detail/div/div[1]/div[3]/button")));
            uploadPhotoButton.click();

            WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']")));
            fileInput.sendKeys("C:\\\\Users\\\\lukab\\\\IdeaProjects\\\\Testiranje projekti\\\\Photo Gallery Starter Kit Testing\\\\src\\\\test\\\\files\\\\invalidFileType.txt");


            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Allowed file types are: .jpeg / .jpg')]")));
            Assert.assertTrue(errorMsg.isDisplayed(), "Expected error message not displayed for invalid file type.");

            //Ensure upload button is still disabled
            WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
            String disabledAttr = submitButton.getAttribute("disabled");
            Assert.assertNotNull(disabledAttr, "Upload button is not disabled after selecting invalid file type.");


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

