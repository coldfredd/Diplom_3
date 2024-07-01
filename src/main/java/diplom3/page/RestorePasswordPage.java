package diplom3.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.time.Duration.ofSeconds;

public class RestorePasswordPage {
    private final WebDriver webDriver;
    private final By loginLinkFromRestorePageLocator = By.xpath("//a[text()='Войти']");//ссылка Войти
    private final By loginPageShowLocator = By.xpath("//h2[text()='Вход']");// Экран Входа
    public RestorePasswordPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    @Step("Click login link")
    public void clickLoginLink(){
        WebElement loginLink = webDriver.findElement(loginLinkFromRestorePageLocator);
        loginLink.click();
        new WebDriverWait(webDriver, ofSeconds(3))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(loginPageShowLocator));
    }
}
