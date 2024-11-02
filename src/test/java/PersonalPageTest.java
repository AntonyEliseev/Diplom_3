import helpers.Constant;
import helpers.UserClient;
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.AuthorizationPage;
import pageobject.MainPage;
import pageobject.PersonalPage;

import java.time.Duration;

import static helpers.Constant.DEFAULT_TIMEOUT;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.junit.Assert.assertEquals;

public class PersonalPageTest {
    WebDriver driver;
    AuthorizationPage authorizationPage;
    MainPage mainPage;
    PersonalPage personalPage;
    String accessToken;
    UserClient userClient;
    String email;
    String password;
    String name;

    @Before
    public void setUp() {
        driver = WebDriverFactory.getDriver();
        driver.get(Constant.LOGIN_PAGE_URL);
        authorizationPage = new AuthorizationPage(driver);
        mainPage = new MainPage(driver);
        personalPage = new PersonalPage(driver);
        userClient = new UserClient();
        Faker faker = new Faker();
        email = faker.internet().emailAddress();
        password = faker.internet().password();
        name = faker.name().username();
        accessToken = userClient.createUser(email, password, name).extract().body().path("accessToken");
        authorizationPage.userLogin(email, password);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.urlToBe(Constant.MAIN_PAGE_URL));
    }

    @Test
    @DisplayName("Переход по клику в 'Личный кабинет'")
    public void checkPersonalAreaButtonTest() {
        mainPage.clickPersonalAreaButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.urlToBe(Constant.PERSONAL_PAGE_URL));
        assertEquals(Constant.PERSONAL_PAGE_URL, personalPage.getLink());
    }

    @Test
    @DisplayName("Выход из аккаунта по кнопке")
    public void checkLogOutButtonTest() {
        mainPage.clickPersonalAreaButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.urlToBe(Constant.PERSONAL_PAGE_URL));
        personalPage.clickExitButton();
        wait.until(ExpectedConditions.urlToBe(Constant.LOGIN_PAGE_URL));
        assertEquals(Constant.LOGIN_PAGE_URL, authorizationPage.getLink());
    }

    @Test
    @DisplayName("Переход в конструктор при нажатии на логотип")
    public void checkLogoButtonTest() {
        mainPage.clickPersonalAreaButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.urlToBe(Constant.PERSONAL_PAGE_URL));
        personalPage.clickLogo();
        assertEquals(Constant.MAIN_PAGE_URL, personalPage.getLink());
    }

    @Test
    @DisplayName("Переход в конструктор при нажатии на 'Конструктор'")
    public void checkConstructorButtonTest() {
        mainPage.clickPersonalAreaButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.urlToBe(Constant.PERSONAL_PAGE_URL));
        personalPage.clickConstructorButton();
        assertEquals(Constant.MAIN_PAGE_URL, personalPage.getLink());
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