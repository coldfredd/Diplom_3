package diplom3;

import com.github.javafaker.Faker;
import diplom3.model.User;
import diplom3.page.*;
import diplom3.steps.UserSteps;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static diplom3.config.RestConfig.HOST;
import static junit.framework.TestCase.assertTrue;

public class AccountProfileTest {
    private static final String BROWSER = "yandex"; // Оставил для запуска отдельных тестов
    private WebDriver webDriver;
    private final UserSteps userSteps = new UserSteps();
    User user;
    Faker faker;

    @Before
    public void setup(){
        webDriver = WebDriverFactory.getWebDriver(BROWSER);
        webDriver.get(HOST);
        faker = new Faker();
        user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setName(faker.name().firstName());
        userSteps.createUser(user);
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
    @Test
    @DisplayName("Check the transition to the Account Profile")
    @Description("After authorization, login to your personal account")
    public void goAccountProfileTest(){
        MainPage mainPage = new MainPage(webDriver);
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.userLogin(user.getEmail(),user.getPassword());
        loginPage.clickEnterButton();
        mainPage.clickLoginAccountProfileButton();
        assertTrue(mainPage.loginAccountPageIsDisplayed());
    }
    @Test
    @DisplayName("Check the transition to the Constructor page from Account Profile")
    @Description("After authorization, open the Constructor page")
    public void goConstructorTest(){
        MainPage mainPage = new MainPage(webDriver);
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.userLogin(user.getEmail(),user.getPassword());
        loginPage.clickEnterButton();
        mainPage.clickLoginAccountProfileButton();
        AccountProfilePage accountProfilePage = new AccountProfilePage(webDriver);
        accountProfilePage.clickConstructorButton();
        assertTrue(accountProfilePage.mainPageIsDisplayed());
    }
    @Test
    @DisplayName("Verify opening the main page when clicking on the logo")
    @Description("Click logo from Account Profile")
    public void clickLogoTest(){
        MainPage mainPage = new MainPage(webDriver);
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.userLogin(user.getEmail(),user.getPassword());
        loginPage.clickEnterButton();
        mainPage.clickLoginAccountProfileButton();
        AccountProfilePage accountProfilePage = new AccountProfilePage(webDriver);
        accountProfilePage.clickLogoButton();
        assertTrue(accountProfilePage.mainPageIsDisplayed());
    }
    @Test
    @DisplayName("Verification of logging out from the account.")
    @Description("Verify logging out using the 'Logout' button in the personal account.")
    public void logoutTest() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.userLogin(user.getEmail(),user.getPassword());
        loginPage.clickEnterButton();
        mainPage.clickLoginAccountProfileButton();
        AccountProfilePage accountProfilePage = new AccountProfilePage(webDriver);
        accountProfilePage.clickExitButton();
        assertTrue(accountProfilePage.loginPageIsDisplayed());
    }
    @After
    public void tearDown(){
        webDriver.quit();
        String accessToken = userSteps.login(user).extract().body().path("accessToken");
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }
}
