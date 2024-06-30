package diplom3.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.time.Duration.ofSeconds;

public class RegistrationPage {
    private final WebDriver webDriver;
    private final By nameInputLocator = By.xpath("//fieldset[1]/div/div/input"); // Поле Имя
    private final By emailInputLocator = By.xpath("//fieldset[2]/div/div/input"); // Поле Email
    private final By passwordInputLocator = By.xpath("//fieldset[3]/div/div/input"); // Поле Пароль
    private final By registrationButtonLocator = By.xpath("//button[text()='Зарегистрироваться']"); // Кнопка Зарегистрироваться
    private final By passwordErrorLocator = By.xpath("//p[text()='Некорректный пароль']");// Текст некорректном пароле
    private final By loginPageShowLocator = By.xpath("//h2[text()='Вход']");// Экран Входа
    private final By loginLinkLocator = By.xpath("//a[text()='Войти']");//ссылка Войти
    public RegistrationPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void userRegistration(String name,String email, String password){
        WebElement nameInput = webDriver.findElement(nameInputLocator);
        nameInput.sendKeys(name);

        WebElement emailInput = webDriver.findElement(emailInputLocator);
        emailInput.sendKeys(email);

        WebElement passwordInput = webDriver.findElement(passwordInputLocator);
        passwordInput.sendKeys(password);
    }

    public void clickRegistrationButton(){
        WebElement registrationButton = webDriver.findElement(registrationButtonLocator);
        registrationButton.click();
    }
    public void clickLoginLink(){
        WebElement loginLink = webDriver.findElement(loginLinkLocator);
        loginLink.click();
        new WebDriverWait(webDriver, ofSeconds(3))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(loginPageShowLocator));
    }
    public boolean loginPageIsDisplayed(){
        new WebDriverWait(webDriver, ofSeconds(3))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(loginPageShowLocator));
        return webDriver.findElement(loginPageShowLocator).isDisplayed();
    }
    public boolean showPasswordError() {
        new WebDriverWait(webDriver, ofSeconds(3))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(passwordErrorLocator));
        return webDriver.findElement(passwordErrorLocator).isDisplayed();
    }

}
