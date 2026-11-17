import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

        WebElement busMarker = wait.until(d -> {
            List<WebElement> markers = d.findElements(
                By.cssSelector(
                    ".leaflet-marker-icon, ymaps[class*='placemark'], [class*='ymaps'][class*='overlay'] img, img[src*='bus'], img[src*='troll']")
            );
            for (WebElement m : markers) {
                if (m.isDisplayed() && m.getSize().getWidth() > 0 && m.getSize().getHeight() > 0) {
                    return m;
                }
            }
            return null;
        });

        try {
            busMarker.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", busMarker);
        }

        WebElement busPopup = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".leaflet-popup-content, " +
                    ".ymaps-2-1-79-balloon__content, " +
                    "[class*='popup'], " +
                    "[class*='balloon']")
            )
        );
        String busInfo = busPopup.getText().trim();
        System.out.println("Об автобусе");
        System.out.println(busInfo);
        shot("cds_3_bus_characteristics");

        assertThat(busInfo)
            .as("Проверка отображения характеристик автобуса")
            .isNotBlank();
    }

    @Test
    public void stopForecastTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> selects = wait.until(d -> {
            List<WebElement> all = d.findElements(By.tagName("select"));
            return all.size() >= 3 ? all : null;
        });

        new Select(selects.get(0)).selectByVisibleText("Киров");

        wait.until(d -> new Select(d.findElements(By.tagName("select")).get(1)).getOptions().size() > 2);
        new Select(driver.findElements(By.tagName("select")).get(1)).selectByIndex(2);

        wait.until(d -> new Select(d.findElements(By.tagName("select")).get(2)).getOptions().size() > 1);
        new Select(driver.findElements(By.tagName("select")).get(2)).selectByIndex(1);

        WebElement forecastBtn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//*[self::a " +
                    "or self::button " +
                    "or self::input][contains(., 'Прогноз') " +
                    "or @value='Прогноз']")
            )
        );
        forecastBtn.click();
        shot("cds_4_forecast_opened");

        WebElement forecastResult = wait.until(d -> {
            List<WebElement> blocks = d.findElements(
                By.xpath("//table[.//td] | //div[contains(@id, 'forecast') " +
                    "or contains(@class, 'forecast') " +
                    "or contains(@id, 'result')]")
            );
            for (WebElement b : blocks) {
                String text = b.getText().trim();
                if (b.isDisplayed() && !text.isEmpty() && text.matches("(?s).*\\d+.*")) {
                    return b;
                }
            }
            return null;
        });

        String forecastText = forecastResult.getText().trim();
        System.out.println("Получение прогноза");
        System.out.println(forecastText);
        shot("cds_5_forecast_verified");

        assertThat(forecastText)
            .as("Проверка наличия номера автобуса и времени ожидания на странице прогноза")
            .isNotBlank()
            .containsPattern("\\d+");
    }
}