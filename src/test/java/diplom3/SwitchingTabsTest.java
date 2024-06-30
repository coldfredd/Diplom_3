package diplom3;

import diplom3.page.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static junit.framework.TestCase.assertTrue;

public class SwitchingTabsTest {
    private static final String BROWSER = "chrome";
    private WebDriver webDriver;

    @Before
    public void setup(){
        webDriver = WebDriverFactory.getWebDriver(BROWSER);
        webDriver.get("https://stellarburgers.nomoreparties.site/");
    }
    @Test
    @DisplayName("Switch Sauces")
    @Description("Verify that navigation to the 'Sauces' section works")
    public void switchTabSaucesTest(){
        MainPage mainPage = new MainPage(webDriver);
        clickTabSauces(mainPage);
        checkShowSaucesHeader(mainPage);
    }
    @Test
    @DisplayName("Switch Fillings")
    @Description("Verify that navigation to the 'Fillings' section works")
    public void switchTabFillingsTest(){
        MainPage mainPage = new MainPage(webDriver);
        clickTabFillings(mainPage);
        checkShowFillingsHeader(mainPage);
    }
    @Test
    @DisplayName("Switch Buns")
    @Description("Verify that navigation to the 'Buns' section works")
    public void switchTabBunsTest(){
        MainPage mainPage = new MainPage(webDriver);
        clickTabFillings(mainPage);
        clickTabBuns(mainPage);     // Отдельно клик не сработает если мы не находимся в другой вкладке т.к. вкладка дефолтно уже выбрана
        checkShowBunsHeader(mainPage); // можно проверить правильно ли Ассерт работает убрав клик на вкладку Булки, то есть 52 строку
    }
    @Step("Check show buns header")
    private static void checkShowBunsHeader(MainPage mainPage) {
        assertTrue(mainPage.showBuns());
    }
    @Step("Click tab buns")
    private static void clickTabBuns(MainPage mainPage) {
        mainPage.clickTabBuns();
    }
    @Step("Check show sauces header")
    private static void checkShowSaucesHeader(MainPage mainPage) {
        assertTrue(mainPage.showSauces());
    }
    @Step("Click tab sauces")
    private static void clickTabSauces(MainPage mainPage) {
        mainPage.clickTabSauces();
    }
    @Step("Check show fillings header")
    private static void checkShowFillingsHeader(MainPage mainPage) {
        assertTrue(mainPage.showFillings());
    }
    @Step("Click tab fillings")
    private static void clickTabFillings(MainPage mainPage) {
        mainPage.clickTabFillings();
    }
    @After
    public void tearDown(){
        webDriver.quit();
    }
}
