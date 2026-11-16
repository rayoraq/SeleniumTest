import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumTest extends BaseTest {
    private final String URL = "https://old.vyatsu.ru";

    @Before
    public void openMainPage() {
        driver.get(URL);
    }

    @Test
    public void openSiteTest() {
        shot("open_site");
        assertThat(driver.getCurrentUrl())
            .as("Проверка корректности открытого URL")
            .startsWith(URL);
    }

    @Test
    public void titleTest() {
        shot("1_open");
        assertThat(driver.getTitle())
            .as("Проверка заголовка главной страницы")
            .isEqualTo("Официальный сайт ВятГУ");
        shot("2_title_ok");
    }


    @Test
    @Ignore("демо падения")
    public void titleFailDemo() {
        assertThat(driver.getTitle())
            .as("Демонстрация падения при неверном заголовке")
            .isEqualTo("Неверный заголовок страницы");
    }

    @Test
    public void educationalStandardsMenuTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement firstLi = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"jsddm\"]/li[1]"))
        );

        Actions action = new Actions(driver);
        action.moveToElement(firstLi).perform();
        shot("menu_opened");

        WebElement targetMenuItem = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"jsddm\"]/li[1]//ul/li[contains(., \"Образовательные стандарты\")]")
            )
        );
        targetMenuItem.click();
    }
}
