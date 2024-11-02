import helpers.Constant;
import helpers.UserClient;
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.AuthorizationPage;
import pageobject.RegistrationPage;

import java.time.Duration;

import static helpers.Constant.DEFAULT_TIMEOUT;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegistrationPageTest {
    WebDriver driver;
    RegistrationPage registrationPage;
    AuthorizationPage authorizationPage;
    String accessToken;
    UserClient userClient;
    String email;
    String name;
    String password;
    Response response;

    @Before
    public void setUp() {
        driver = WebDriverFactory.getDriver();
        driver.get(Constant.REGISTER_PAGE_URL);
        registrationPage = new RegistrationPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        userClient = new UserClient();
        Faker faker = new Faker();
        email = faker.internet().emailAddress();
        password = faker.internet().password();
        name = faker.name().username();
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void checkSuccessRegistrationTest() {
        registrationPage.userRegistration(name, email, password);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.urlToBe(Constant.LOGIN_PAGE_URL));
        accessToken = userClient.authorizationUser(email, password).extract().body().path("accessToken");
        assertTrue(driver.findElement(authorizationPage.getEnterButton()).isDisplayed());
    }

    @Test
    @DisplayName("Регистрация с некорректным паролем")
    public void checkRegistrationWithIncorrectPasswordTest() {
        String wrongPassword = RandomStringUtils.randomAlphanumeric(4);
        registrationPage.userRegistration(name, email, wrongPassword);
        response = userClient.authorizationUserWithInvalidPassword(email, wrongPassword);
        accessToken = response.path("accessToken");
        registrationPage.waitError();
        assertEquals("Некорректный пароль", registrationPage.getWarningIncorrectPassword());
    }

    @After
    public void closeWithDelete() {
        driver.quit();
        if (accessToken != null) {
            userClient.deleteUser(accessToken)
                    .assertThat().statusCode(SC_ACCEPTED);
        }
    }
}