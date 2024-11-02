package helpers;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class UserClient extends RestClient {

    public ValidatableResponse createUser(String email, String password, String name) {
        UserRequest userRequest = new UserRequest(email, password, name);
        return given()
                .spec(getDefaultRequestSpec())
                .body(userRequest)
                .when()
                .post(REGISTER_USER)
                .then()
                .assertThat()
                .body("accessToken", notNullValue());
    }

    public ValidatableResponse authorizationUser(String email, String password) {
        UserRequest userRequest = new UserRequest(email, password);
        return given()
                .spec(getDefaultRequestSpec())
                .body(userRequest)
                .post(LOGIN_USER)
                .then()
                .assertThat()
                .body("accessToken", notNullValue());
    }

    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .header("authorization", accessToken)
                .spec(getDefaultRequestSpec())
                .delete(DELETE_USER)
                .then();
    }

    public Response authorizationUserWithInvalidPassword(String email, String password) {
        UserRequest userRequest = new UserRequest(email, password);
        return given()
                .spec(getDefaultRequestSpec())
                .body(userRequest)
                .post(LOGIN_USER);
    }
}