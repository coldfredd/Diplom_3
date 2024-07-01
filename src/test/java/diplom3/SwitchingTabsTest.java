package diplom3;

import diplom3.page.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static diplom3.config.RestConfig.HOST;
import static junit.framework.TestCase.assertTrue;

public class SwitchingTabsTest {
    private static final String BROWSER = "chrome";
    private WebDriver webDriver;

    @Before
    public void setup(){
        webDriver = WebDriverFactory.getWebDriver(BROWSER);
        webDriver.get(HOST);
    }
    @Test
    @DisplayName("Switch Sauces")
    @Description("Verify that navigation to the 'Sauces' section works")
    public void switchTabSaucesTest(){
        MainPage mainPage = new MainPage(webDriver);
        mainPage.clickTabSauces();
        assertTrue(mainPage.showSauces());
    }
    @Test
    @DisplayName("Switch Fillings")
    @Description("Verify that navigation to the 'Fillings' section works")
    public void switchTabFillingsTest(){
        MainPage mainPage = new MainPage(webDriver);
        mainPage.clickTabFillings();
        assertTrue(mainPage.showFillings());
    }
    @Test
    @DisplayName("Switch Buns")
    @Description("Verify that navigation to the 'Buns' section works")
    public void switchTabBunsTest(){
        MainPage mainPage = new MainPage(webDriver);
        mainPage.clickTabFillings(); // Отдельно клик не сработает если мы не находимся в другой вкладке т.к. вкладка дефолтно уже выбрана
        mainPage.clickTabBuns(); // можно проверить правильно ли Ассерт работает убрав клик на вкладку Булки, то есть 52 строку
        assertTrue(mainPage.showBuns());
    }
    @After
    public void tearDown(){
        webDriver.quit();
    }
}
