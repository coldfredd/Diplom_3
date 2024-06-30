package diplom3;

import com.github.javafaker.Faker;
import diplom3.model.User;
import diplom3.page.LoginPage;
import diplom3.page.MainPage;
import diplom3.page.RegistrationPage;
import diplom3.page.RestorePasswordPage;
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

public class LoginTest {
    private static final String BROWSER = "chrome";
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
    @DisplayName("Login to account from mainpage")
    @Description("Verification of logging in using the 'Sign In' button on the mainpage")
    public void loginMainPageTest(){
        MainPage mainPage = new MainPage(webDriver);
        clickLoginAccountButton(mainPage);
        LoginPage loginPage = new LoginPage(webDriver);
        userLogin(loginPage);
        clickEnterButton(loginPage);
        checkMainPageDisplayed(loginPage);
    }
    @Test
    @DisplayName("Login to account from 'Personal Account'")
    @Description("Verification of logging in through the 'Personal Account' button")
    public void loginHeaderTest(){
        MainPage mainPage = new MainPage(webDriver);
        clickLoginButton(mainPage);
        LoginPage loginPage = new LoginPage(webDriver);
        userLogin(loginPage);
        clickEnterButton(loginPage);
        checkMainPageDisplayed(loginPage);
    }

    @Test
    @DisplayName("Login to account from Registration form")
    @Description("Verification of logging in through the button in the registration form")
    public void loginFromRegistrationTest(){
        MainPage mainPage = new MainPage(webDriver);
        clickLoginButton(mainPage);
        LoginPage loginPage = new LoginPage(webDriver);
        clickRegistrationLink(loginPage);
        RegistrationPage registrationPage = new RegistrationPage(webDriver);
        clickLoginLink(registrationPage);
        userLogin(loginPage);
        clickEnterButton(loginPage);
        checkMainPageDisplayed(loginPage);
    }
    @Test
    @DisplayName("Login to account from Restore Password")
    @Description("Verification of logging in through the button in the password recovery form")
    public void loginFromRestorePasswordPageTest(){
        MainPage mainPage = new MainPage(webDriver);
        clickLoginButton(mainPage);
        LoginPage loginPage = new LoginPage(webDriver);
        clickRestorePasswordLink(loginPage);
        RestorePasswordPage restorePasswordPage = new RestorePasswordPage(webDriver);
        clickLoginLink(restorePasswordPage);
        userLogin(loginPage);
        clickEnterButton(loginPage);
        checkMainPageDisplayed(loginPage);
    }
    @Step("Click login link")
    private static void clickLoginLink(RestorePasswordPage restorePasswordPage) {
        restorePasswordPage.clickLoginLink();
    }
    @Step("Click restore password link")
    private static void clickRestorePasswordLink(LoginPage loginPage) {
        loginPage.clickRestorePasswordLink();
    }
    @Step("Check MainPage displayed")
    private static void checkMainPageDisplayed(LoginPage loginPage) {
        assertTrue(loginPage.mainPageIsDisplayed());
    }
    @Step("Click enter button")
    private static void clickEnterButton(LoginPage loginPage) {
        loginPage.clickEnterButton();
    }
    @Step("Login user with email and password")
    private void userLogin(LoginPage loginPage) {
        loginPage.userLogin(user.getEmail(), user.getPassword());
    }
    @Step("Click login account button")
    private static void clickLoginAccountButton(MainPage mainPage) {
        mainPage.clickLoginAccountButton();
    }
    @Step("Click login button")
    private static void clickLoginButton(MainPage mainPage) {
        mainPage.clickLoginButton();
    }
    @Step("Click Login link")
    private static void clickLoginLink(RegistrationPage registrationPage) {
        registrationPage.clickLoginLink();
    }
    @Step("Click Registration link")
    private static void clickRegistrationLink(LoginPage loginPage) {
        loginPage.clickRegistrationLink();
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
