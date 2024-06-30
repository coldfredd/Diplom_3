package diplom3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverFactory {
    public static WebDriver getWebDriver(String browserType) {
        browserType = System.getProperty("browser", "yandex");
        if (browserType.equals("chrome")) {
            return  WebDriverManager.chromedriver().create();
        }
        if  (browserType.equals("yandex")) {
            System.setProperty("webdriver.chrome.driver", "/Users/user/Downloads/yandexdriver");
            return  new ChromeDriver();
        }
        return null;
    }
}
