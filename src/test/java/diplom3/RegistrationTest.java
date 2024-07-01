package diplom3;

import com.github.javafaker.Faker;
import diplom3.model.User;
import diplom3.page.LoginPage;
import diplom3.page.MainPage;
import diplom3.page.RegistrationPage;
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

public class RegistrationTest {
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
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
    @Test
    @DisplayName("Registration error")
    @Description("Verification of error message for incorrect password")
    public void incorrectPasswordTest(){
        MainPage mainPage = new MainPage(webDriver);
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.clickRegistrationLink();
        RegistrationPage registrationPage = new RegistrationPage(webDriver);
        registrationPage.userRegistration(user.getName(), user.getEmail(), "asd");
        registrationPage.clickRegistrationButton();
        assertTrue(registrationPage.showPasswordError());
    }
    @Test
    @DisplayName("Successful registration")
    @Description("Verification of successful registration")
    public void registrationSuccessTest(){
        MainPage mainPage = new MainPage(webDriver);
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.clickRegistrationLink();
        RegistrationPage registrationPage = new RegistrationPage(webDriver);
        registrationPage.userRegistration(user.getName(), user.getEmail(), user.getPassword());
        registrationPage.clickRegistrationButton();
        assertTrue(registrationPage.loginPageIsDisplayed());
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
