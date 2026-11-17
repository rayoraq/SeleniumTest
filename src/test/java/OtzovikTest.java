import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    public void writeReviewValidationTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement writeReviewBtn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(., 'Написать отзыв') or contains(@href, 'postreview')]")
            )
        );
        writeReviewBtn.click();

        WebElement objectInput = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[type='text'][name*='content_product']")
            )
        );
        objectInput.clear();
        objectInput.sendKeys("Ботанический сад ВятГУ");

        WebElement kirovGardenOption = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath(
                    "//a[contains(@class, 'popup-obj') and contains(., 'Ботанический сад') and contains(., 'Киров')]")
            )
        );

        shot("otzovik_4_select_object");
        kirovGardenOption.click();

        WebElement titleInput = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[name*='title'], input[name*='impression'], textarea[name*='title']")
            )
        );
        titleInput.sendKeys("Прекрасное место для прогулок в центре Кирова");

        WebElement previewBtn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@value='Предпросмотр'] | //button[contains(., 'Предпросмотр')]")
            )
        );
        previewBtn.click();

        WebElement firstError = wait.until(driver -> {
            List<WebElement> messages = driver.findElements(By.cssSelector(".field-error-msg, .alertframe.error"));
            for (WebElement msg : messages) {
                if (msg.isDisplayed() && !msg.getText().trim().isEmpty()) {
                    return msg;
                }
            }
            return null;
        });

        String errorText = driver.findElements(By.cssSelector(".field-error-msg, .alertframe.error")).stream()
            .filter(WebElement::isDisplayed)
            .map(el -> el.getText().trim())
            .filter(text -> !text.isEmpty())
            .collect(Collectors.joining("\n"));

        System.out.println("Ошибки");
        System.out.println(errorText);
        shot("otzovik_5_validation_errors");

        assertThat(errorText)
            .as("Проверка появления сообщений об ошибках валидации при неполном заполнении")
            .isNotBlank();
    }
}
