package diplom3.steps;

import diplom3.model.User;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserSteps {
    private static final String USER_REGISTER = "https://stellarburgers.nomoreparties.site/api/auth/register";
    private static final String LOGIN = "https://stellarburgers.nomoreparties.site/api/auth/login";
    private static final String USER_DELETE = "https://stellarburgers.nomoreparties.site/api/auth/user";
    @Step("Create user")
    public ValidatableResponse createUser(User user){

        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(USER_REGISTER)
                .then();
    }

    @Step("Login user")
    public ValidatableResponse login(User user) {

        return  given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(LOGIN)
                .then();
    }

    @Step("Delete user")
    public static ValidatableResponse deleteUser(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .when()
                .delete(USER_DELETE)
                .then();
    }
}
