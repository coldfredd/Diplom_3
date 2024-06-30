package diplom3;

import com.github.javafaker.Faker;
import diplom3.model.User;
import diplom3.page.LoginPage;
import diplom3.page.MainPage;
import diplom3.page.RegistrationPage;
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

public class RegistrationTest {
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
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
    @Test
    @DisplayName("Registration error")
    @Description("Verification of error message for incorrect password")
    public void incorrectPasswordTest(){
        MainPage mainPage = new MainPage(webDriver);
        clickLoginButton(mainPage);
        LoginPage loginPage = new LoginPage(webDriver);
        clickRegistrationLink(loginPage);
        RegistrationPage registrationPage = new RegistrationPage(webDriver);
        userRegistrationIncorrectPassword(registrationPage);
        clickRegistrationButton(registrationPage);
        checkErrorPassword(registrationPage);
    }
    @Test
    @DisplayName("Successful registration")
    @Description("Verification of successful registration")
    public void registrationSuccessTest(){
        MainPage mainPage = new MainPage(webDriver);
        clickLoginButton(mainPage);
        LoginPage loginPage = new LoginPage(webDriver);
        clickRegistrationLink(loginPage);
        RegistrationPage registrationPage = new RegistrationPage(webDriver);
        userRegistration(registrationPage);
        clickRegistrationButton(registrationPage);
        checkLoginPageDiaplsyed(registrationPage);
    }
    @Step("Check Login page displayed")
    private static void checkLoginPageDiaplsyed(RegistrationPage registrationPage) {
        assertTrue(registrationPage.loginPageIsDisplayed());
    }

    @Step("Registration user")
    private void userRegistration(RegistrationPage registrationPage) {
        registrationPage.userRegistration(user.getName(), user.getEmail(),user.getPassword());
    }

    @Step("Check error text with incorrect password")
    private static void checkErrorPassword(RegistrationPage registrationPage) {
        assertTrue(registrationPage.showPasswordError());
    }

    @Step("Click registration button")
    private static void clickRegistrationButton(RegistrationPage registrationPage) {
        registrationPage.clickRegistrationButton();
    }

    @Step("Try registration user with incorrect password")
    private void userRegistrationIncorrectPassword(RegistrationPage registrationPage) {
        registrationPage.userRegistration(user.getName(),user.getEmail(),"1234");
    }
    @Step("Click Registration link")
    private static void clickRegistrationLink(LoginPage loginPage) {
        loginPage.clickRegistrationLink();
    }

    @Step("Click login button")
    private static void clickLoginButton(MainPage mainPage) {
        mainPage.clickLoginButton();
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
