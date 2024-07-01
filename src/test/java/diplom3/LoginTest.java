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

import static diplom3.config.RestConfig.HOST;
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
    @DisplayName("Login to account from mainpage")
    @Description("Verification of logging in using the 'Sign In' button on the mainpage")
    public void loginMainPageTest(){
        MainPage mainPage = new MainPage(webDriver);
        mainPage.clickLoginAccountButton();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.userLogin(user.getEmail(), user.getPassword());
        loginPage.clickEnterButton();
        assertTrue(loginPage.mainPageIsDisplayed());
    }
    @Test
    @DisplayName("Login to account from 'Personal Account'")
    @Description("Verification of logging in through the 'Personal Account' button")
    public void loginHeaderTest(){
        MainPage mainPage = new MainPage(webDriver);
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.userLogin(user.getEmail(), user.getPassword());
        loginPage.clickEnterButton();
        assertTrue(loginPage.mainPageIsDisplayed());
    }

    @Test
    @DisplayName("Login to account from Registration form")
    @Description("Verification of logging in through the button in the registration form")
    public void loginFromRegistrationTest(){
        MainPage mainPage = new MainPage(webDriver);
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.clickRegistrationLink();
        RegistrationPage registrationPage = new RegistrationPage(webDriver);
        registrationPage.clickLoginLink();
        loginPage.userLogin(user.getEmail(), user.getPassword());
        loginPage.clickEnterButton();
        assertTrue(loginPage.mainPageIsDisplayed());
    }
    @Test
    @DisplayName("Login to account from Restore Password")
    @Description("Verification of logging in through the button in the password recovery form")
    public void loginFromRestorePasswordPageTest(){
        MainPage mainPage = new MainPage(webDriver);
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.clickRestorePasswordLink();
        RestorePasswordPage restorePasswordPage = new RestorePasswordPage(webDriver);
        restorePasswordPage.clickLoginLink();
        loginPage.userLogin(user.getEmail(), user.getPassword());
        loginPage.clickEnterButton();
        assertTrue(loginPage.mainPageIsDisplayed());
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
