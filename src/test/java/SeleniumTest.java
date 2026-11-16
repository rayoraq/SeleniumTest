import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumTest {
    WebDriver driver;
    final String URL = "https://old.vyatsu.ru";

    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver = new ChromeDriver(options);
    }

    @Test
    public void openSiteTest() {
        driver.get(URL);
        assertThat(driver.getCurrentUrl())
            .as("Проверка корректности открытого URL")
            .startsWith(URL);
    }

    public void titleTest() {
        driver.get(URL);
        assertThat(driver.getTitle())
            .as("Проверка заголовка главной страницы")
            .isEqualTo("Официальный сайт ВятГУ");
    }

    @After
    public void quit() {
        driver.quit();
    }
}
