import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class DeletePhotoTest {

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
    public void testDeletePhoto() {
        try {
            // 1. Login
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


            WebElement albumThumbnail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.thumbnail__img")));
            Actions actions = new Actions(driver);
            actions.moveToElement(albumThumbnail).perform();    // Hover over album

            WebElement overlay = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.thumbnail--overlay")));
            overlay.click();
            //Thread.sleep(2000);  Used only to visually observe the step during test review.

            List<WebElement> photosBefore = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div.thumbnail__img")));
            int initialCount = photosBefore.size();
            Assert.assertTrue(initialCount > 0, "No photos available for deletion."); //Count number of images before deletion

            WebElement firstPhoto = photosBefore.getFirst();
            actions.moveToElement(firstPhoto).perform();  //Hover over the first image to reveal delete button
            // Thread.sleep(2000);

            WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.thumbnail__info__delete")));
            deleteButton.click();
            //Thread.sleep(2000);
            WebElement confirmDelete = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Delete')]")));
            confirmDelete.click();
            //Thread.sleep(2000);
            Thread.sleep(4000);
            driver.navigate().refresh();  //Should display 1 photo less after refreshing and reduce total number of photos by 1

            // Final validation
            wait.until(driver -> driver.findElements(By.cssSelector("div.thumbnail__img")).size() == initialCount - 1);
            int finalCount = driver.findElements(By.cssSelector("div.thumbnail__img")).size();
            Assert.assertEquals(finalCount, initialCount - 1, "Photo was not deleted successfully.");


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
