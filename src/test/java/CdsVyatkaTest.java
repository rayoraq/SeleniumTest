import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CdsVyatkaTest extends BaseTest {
    private static final String URL = "https://m.cdsvyatka.com/";

    @Before
    public void openMobileSite() {
        driver.manage().window().setSize(new Dimension(412, 915));
        driver.get(URL);

        List<WebElement> cookieBtns = driver.findElements(By.xpath(
            "//button[contains(., 'Принять')] | //a[contains(., 'Принять')]"));
        if (!cookieBtns.isEmpty() && cookieBtns.get(0).isDisplayed()) {
            cookieBtns.get(0).click();
        }
    }
}