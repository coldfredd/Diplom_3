package diplom3.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.time.Duration.ofSeconds;

public class AccountProfilePage {
    private final WebDriver webDriver;
    private final By exitAccountLocator = By.xpath("//button[text()='Выход']");//Выход с аккаунта
    private final By loginPageShowLocator = By.xpath("//h2[text()='Вход']");// Экран Входа
    private final By logoButtonLocator = By.xpath("//div[@class='AppHeader_header__logo__2D0X2']");//Лого
    private final By constructorButtonLocator = By.xpath("//p[text()='Конструктор']");//Конструктор
    private final By mainPageShowFromProfileLocator = By.xpath("//h1[text()='Соберите бургер']");//Главная страница

    public AccountProfilePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    @Step("Click Exit button")
    public void clickExitButton(){
        WebElement exitButton = webDriver.findElement(exitAccountLocator);
        exitButton.click();
        new WebDriverWait(webDriver, ofSeconds(3))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(loginPageShowLocator));
    }
    @Step("Check Login page displayed")
    public boolean loginPageIsDisplayed(){
        return webDriver.findElement(loginPageShowLocator).isDisplayed();
    }
    @Step("Click logo button")
    public void clickLogoButton(){
        WebElement logoButton = webDriver.findElement(logoButtonLocator);
        logoButton.click();
        new WebDriverWait(webDriver, ofSeconds(3))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(mainPageShowFromProfileLocator));
    }
    @Step("Click Constructor button")
    public void clickConstructorButton(){
        WebElement constructorButton = webDriver.findElement(constructorButtonLocator);
        constructorButton.click();
        new WebDriverWait(webDriver, ofSeconds(3))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(mainPageShowFromProfileLocator));
    }
    @Step("Check main page displayed")
    public boolean mainPageIsDisplayed(){
        return webDriver.findElement(mainPageShowFromProfileLocator).isDisplayed();
    }

}
