import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class BaseTest {
    protected WebDriver driver;

    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver = new ChromeDriver(options);
    }

    protected void shot(String step) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Path dir = Paths.get("screenshots");
        try {
            Files.createDirectories(dir);
            String fileName = step.replaceAll("[^a-zA-Zа-яА-Я0-9._-]", "_") + ".png";
            Files.copy(screenshot.toPath(), dir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new RuntimeException("Не удалось сохранить скриншот для шага: " + step, exception);
        }
    }

    protected void shotWithAlert(String step) {
        Path dir = Paths.get("screenshots");
        try {
            Files.createDirectories(dir);
            String fileName = step.replaceAll("[^a-zA-Zа-яА-Я0-9._-]", "_") + ".png";
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            ImageIO.write(screenFullImage, "png", dir.resolve(fileName).toFile());
        } catch (Exception exception) {
            throw new RuntimeException("Не удалось сделать скриншот экрана для шага: " + step, exception);
        }
    }

    @Rule
    public TestWatcher watcher = new TestWatcher() {
        @Override
        protected void failed(Throwable throwable, Description description) {
            shot("FAILED_" + description.getMethodName());
        }

        @Override
        protected void finished(Description des) { //т.к. After выполняется раньше чем failed()
            driver.quit();
        }
    };
}
