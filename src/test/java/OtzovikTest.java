import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OtzovikTest extends BaseTest {
    private static final String URL = "https://otzovik.com";

    @Before
    public void openMainPage() {
        driver.get(URL);
    }

    @Test
    public void searchYandexMetroTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement searchInput = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input#header-search-input")
            )
        );
        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys("яндекс");
        shot("otzovik_1_search_input");
    }
}
