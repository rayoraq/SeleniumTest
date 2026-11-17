import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
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

    @Test
    public void busCharacteristicsOnMapTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement citySelectEl = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("(//select)[1]"))
        );
        Select citySelect = new Select(citySelectEl);
        citySelect.selectByVisibleText("Киров");

        WebElement routeSelectEl = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("(//select)[2]"))
        );
        wait.until(d -> new Select(routeSelectEl).getOptions().size() > 1);
        Select routeSelect = new Select(routeSelectEl);
        routeSelect.selectByIndex(1);
        shot("cds_1_route_selected");

        WebElement mapBtn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath(
                    "//*[self::a or self::button " +
                        "or self::input or self::div " +
                        "or self::span][contains(normalize-space(.), 'Карта') " +
                        "or @value='Карта']")
            )
        );
        mapBtn.click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                    "//div[contains(@id, 'id_buttonMap') " +
                        "or contains(@class, 'map') " +
                        "or contains(@class, 'leaflet-container') " +
                        "or contains(@class, 'ymaps')]")
            )
        );
        shot("cds_2_route_on_map");
    }
}