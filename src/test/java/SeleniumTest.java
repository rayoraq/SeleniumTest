import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumTest extends BaseTest {
    private final String URL = "https://old.vyatsu.ru";

    @Test
    public void openSiteTest() {
        driver.get(URL);
        shot("open_site");
        assertThat(driver.getCurrentUrl())
            .as("Проверка корректности открытого URL")
            .startsWith(URL);
    }

    @Test
    public void titleTest() {
        driver.get(URL);
        shot("1_open");

        assertThat(driver.getTitle())
            .as("Проверка заголовка главной страницы")
            .isEqualTo("Официальный сайт ВятГУ");
        shot("2_title_ok");
    }
}
