import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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
    }

    @After
    public void quit() {
        driver.quit();
    }
}
