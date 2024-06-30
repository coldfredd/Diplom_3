package diplom3.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.time.Duration.ofSeconds;

public class MainPage {
    private final WebDriver webDriver;
    private final By loginButtonLocator = By.xpath("//header[contains(@class,'header')]//p[text()='Личный Кабинет']"); // кнопка Личный Кабинет
    private final By loginAccountButtonLocator = By.xpath("//button[text()='Войти в аккаунт']");// кнопка Войти в аккаунт
    private final By loginAccountPageLocator= By.xpath("//a[text()='Профиль']");// Страница Профиля
    private final By loginPageShowLocator = By.xpath("//h2[text()='Вход']");// Экран Входа
    private final By tabBunsLocator = By.xpath("//span[text()='Булки']");//Таб Булки
    private final By tabBunsSelectedLocator = By.xpath("//div[contains(@class,'tab_tab_type')]/span[text()='Булки']");//Таб Булки Выбран
    private final By BunsLocator = By.xpath("//h2[text()='Булки']");//Раздел Булки
    private final By tabSaucesLocator = By.xpath("//span[text()='Соусы']");//Таб Соусы
    private final By tabSaucesSelectedLocator = By.xpath("//div[contains(@class,'tab_tab_type')]/span[text()='Соусы']");//Таб Соусы Выбран
    private final By SaucesLocator = By.xpath("//h2[text()='Соусы']");//Раздел Соусы
    private final By tabFillingsLocator = By.xpath("//span[text()='Начинки']");//Таб Начинки
    private final By tabFillingsSelectedLocator = By.xpath("//div[contains(@class,'tab_tab_type')]/span[text()='Начинки']");//Таб Начинки Выбран
    private final By FillingsLocator = By.xpath("//h2[text()='Начинки']");//Раздел Начинки
    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    public void clickTabBuns(){
        WebElement tabBunsButton = webDriver.findElement(tabBunsLocator);
        tabBunsButton.click();
        new WebDriverWait(webDriver, ofSeconds(5))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(BunsLocator));
    }
    public boolean showBuns(){
        new WebDriverWait(webDriver, ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(tabBunsSelectedLocator));
        return webDriver.findElement(BunsLocator).isDisplayed();
    }
    public void clickTabSauces(){
        new WebDriverWait(webDriver, ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(tabSaucesLocator));
        WebElement tabSaucesButton = webDriver.findElement(tabSaucesLocator);
        tabSaucesButton.click();
    }
    public boolean showSauces(){
         new WebDriverWait(webDriver, ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(tabSaucesSelectedLocator));
         return webDriver.findElement(SaucesLocator).isDisplayed();
    }
    public void clickTabFillings(){
        WebElement tabFillingsButton = webDriver.findElement(tabFillingsLocator);
        tabFillingsButton.click();
        new WebDriverWait(webDriver, ofSeconds(3))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(FillingsLocator));
    }
    public boolean showFillings(){
        new WebDriverWait(webDriver, ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(tabFillingsSelectedLocator));
        return webDriver.findElement(FillingsLocator).isDisplayed();
    }
    public void clickLoginButton(){
        WebElement loginAccountButton = webDriver.findElement(loginButtonLocator);
        loginAccountButton.click();
        new WebDriverWait(webDriver, ofSeconds(3))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(loginPageShowLocator));
    }
    public void clickLoginAccountProfileButton(){
        WebElement loginAccountButton = webDriver.findElement(loginButtonLocator);
        loginAccountButton.click();
        new WebDriverWait(webDriver, ofSeconds(3))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(loginAccountPageLocator));
    }
    public void clickLoginAccountButton(){
        WebElement loginAccountButton = webDriver.findElement(loginAccountButtonLocator);
        loginAccountButton.click();
        new WebDriverWait(webDriver, ofSeconds(3))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(loginPageShowLocator));
    }
    public boolean loginAccountPageIsDisplayed(){
        new WebDriverWait(webDriver, ofSeconds(5))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(loginAccountPageLocator));
        return webDriver.findElement(loginAccountPageLocator).isDisplayed();
    }
}
