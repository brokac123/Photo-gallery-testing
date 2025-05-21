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
import java.util.List;

public class SearchAndViewPhotoTest {

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
    public void testSearchAndViewNewPhoto() {
        try {


            WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app/master-layout/div/main/div/loader-component/div/home-route/div/cover/div[1]/form/input")));
            searchField.click();
            String input="mercedes";
            searchField.sendKeys(input);
            searchField.sendKeys(Keys.ENTER);
            //Thread.sleep(5000);

            // Wait until at least one thumbnail appears as a result of the search
            List<WebElement> thumbnails = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("div.thumbnail__img"), 0));
            Assert.assertFalse(thumbnails.isEmpty(), "No thumbnails found for search term.");


            //Hovering first photo
            Actions actions = new Actions(driver);
            WebElement firstThumbnail = thumbnails.getFirst();
            actions.moveToElement(firstThumbnail).perform();

            WebElement firstOverlay = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.thumbnail--overlay"))); //
            firstOverlay.click();

            WebElement closeButton=wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[title='Close photo']")));
            closeButton.click();

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
