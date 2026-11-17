import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

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

        WebElement suggestion = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath(
                    "//div[contains(@class, 'popup-box')]//a[contains(@class, 'popup-obj') and contains(., 'Yandex Metro')]")
            )
        );

        shot("otzovik_2_suggestions");
        suggestion.click();

        WebElement pageTitle = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
        );
        assertThat(pageTitle.getText())
            .as("Проверка заголовка страницы объекта")
            .contains("Yandex Metro");

        WebElement reviewsCounter = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                    "//*[contains(@class, 'reviews-counter') or self::a[contains(., 'Отзывы')]]")
            )
        );
        String counterText = reviewsCounter.getText().trim();
        shot("otzovik_c3_yandex_metro_page");

        assertThat(counterText)
            .as("Проверка, что количество отзывов не пусто")
            .isNotEmpty();
    }
}
