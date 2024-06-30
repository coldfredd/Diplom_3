package diplom3;

import com.github.javafaker.Faker;
import diplom3.model.User;
import diplom3.page.*;
import diplom3.steps.UserSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

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
        webDriver.get("https://stellarburgers.nomoreparties.site/");
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
        clickLoginButton(mainPage);
        LoginPage loginPage = new LoginPage(webDriver);
        userLogin(loginPage);
        clickEnterButton(loginPage);
        clickLoginAccountProfileButton(mainPage);
        checkAccountPageDisplayed(mainPage);
    }

    @Test
    @DisplayName("Check the transition to the Constructor page from Account Profile")
    @Description("After authorization, open the Constructor page")
    public void goConstructorTest(){
        MainPage mainPage = new MainPage(webDriver);
        clickLoginButton(mainPage);
        LoginPage loginPage = new LoginPage(webDriver);
        userLogin(loginPage);
        clickEnterButton(loginPage);
        clickLoginAccountProfileButton(mainPage);
        AccountProfilePage accountProfilePage = new AccountProfilePage(webDriver);
        clickConstructorButton(accountProfilePage);
        checkMainPageDisplayed(accountProfilePage);
    }
    @Test
    @DisplayName("Verify opening the main page when clicking on the logo")
    @Description("Click logo from Account Profile")
    public void clickLogoTest(){
        MainPage mainPage = new MainPage(webDriver);
        clickLoginButton(mainPage);
        LoginPage loginPage = new LoginPage(webDriver);
        userLogin(loginPage);
        clickEnterButton(loginPage);
        clickLoginAccountProfileButton(mainPage);
        AccountProfilePage accountProfilePage = new AccountProfilePage(webDriver);
        clickLogoButton(accountProfilePage);
        checkMainPageDisplayed(accountProfilePage);
    }


    @Test
    @DisplayName("Verification of logging out from the account.")
    @Description("Verify logging out using the 'Logout' button in the personal account.")
    public void logoutTest() {
        MainPage mainPage = new MainPage(webDriver);
        clickLoginButton(mainPage);
        LoginPage loginPage = new LoginPage(webDriver);
        userLogin(loginPage);
        clickEnterButton(loginPage);
        clickLoginAccountProfileButton(mainPage);
        AccountProfilePage accountProfilePage = new AccountProfilePage(webDriver);
        clickExitButton(accountProfilePage);
        checkLoginPageDisplayed(accountProfilePage);
    }
    @Step("Check Login page displayed")
    private static void checkLoginPageDisplayed(AccountProfilePage accountProfilePage) {
        assertTrue(accountProfilePage.loginPageIsDisplayed());
    }

    @Step("Click Exit button")
    private static void clickExitButton(AccountProfilePage accountProfilePage) {
        accountProfilePage.clickExitButton();
    }

    @Step("Check Account Page Displayed")
    private static void checkAccountPageDisplayed(MainPage mainPage) {
        assertTrue(mainPage.loginAccountPageIsDisplayed());
    }
    @Step("Click Account Profile button")
    private static void clickLoginAccountProfileButton(MainPage mainPage) {
        mainPage.clickLoginAccountProfileButton();
    }
    @Step("Click login button")
    private static void clickLoginButton(MainPage mainPage) {
        mainPage.clickLoginButton();
    }
    @Step("Login account with email and password")
    private void userLogin(LoginPage loginPage) {
        loginPage.userLogin(user.getEmail(), user.getPassword());
    }
    @Step("Click Enter Button")
    private static void clickEnterButton(LoginPage loginPage) {
        loginPage.clickEnterButton();
    }
    @Step("Check main page displayed")
    private static void checkMainPageDisplayed(AccountProfilePage accountProfilePage) {
        assertTrue(accountProfilePage.mainPageIsDisplayed());
    }

    @Step("Click Constructor button")
    private static void clickConstructorButton(AccountProfilePage accountProfilePage) {
        accountProfilePage.clickConstructorButton();
    }
    @Step("Click logo button")
    private static void clickLogoButton(AccountProfilePage accountProfilePage) {
        accountProfilePage.clickLogoButton();
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
