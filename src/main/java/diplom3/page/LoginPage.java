package diplom3.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.time.Duration.ofSeconds;

public class LoginPage {
    private final WebDriver webDriver;
    private final By registrationLinkLocator = By.xpath("//div[contains(@class,'login')]//a[text()='Зарегистрироваться']"); // ссылка Зарегистрироваться
    private final By emailInputLoginLocator = By.xpath("//fieldset[1]/div/div/input"); // Поле Email
    private final By passwordInputLoginLocator = By.xpath("//fieldset[2]/div/div/input"); // Поле Пароль
    private final By registrationWindowLocator = By.xpath("//h2[text()='Регистрация']");// Экран Регистрация
    private final By enterButtonLocator = By.xpath("//button[text()='Войти']");//Кнопка Войти
    private final By mainPageShowLocator = By.xpath("//h1[text()='Соберите бургер']");//Главная страница
    private final By restorePasswordLinkLocator = By.xpath("//a[text()='Восстановить пароль']");//Ссылка на восстановление пароля
    private final By restorePasswordPageLocator = By.xpath("//h2[text()='Восстановление пароля']");//Страница Восстановление пароля
    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    public void clickRegistrationLink(){
        WebElement registrationLink = webDriver.findElement(registrationLinkLocator);
        registrationLink.click();
        new WebDriverWait(webDriver, ofSeconds(3))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(registrationWindowLocator));
    }

    public void userLogin(String email, String password){

        WebElement emailInput = webDriver.findElement(emailInputLoginLocator);
        emailInput.sendKeys(email);

        WebElement passwordInput = webDriver.findElement(passwordInputLoginLocator);
        passwordInput.sendKeys(password);
    }
    public void clickRestorePasswordLink(){
        WebElement restorePasswordLink = webDriver.findElement(restorePasswordLinkLocator);
        restorePasswordLink.click();
        new WebDriverWait(webDriver, ofSeconds(8))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(restorePasswordPageLocator));
    }

    public void clickEnterButton(){
        WebElement enterButton = webDriver.findElement(enterButtonLocator);
        enterButton.click();
        new WebDriverWait(webDriver, ofSeconds(3))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(mainPageShowLocator));
    }
    public boolean mainPageIsDisplayed(){
        new WebDriverWait(webDriver, ofSeconds(3))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(mainPageShowLocator));
        return webDriver.findElement(mainPageShowLocator).isDisplayed();
    }


}
