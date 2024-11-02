package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthorizationPage {
    private final By emailField = By.xpath("//input[@name='name']");
    private final By passwordField = By.xpath("//input[@name='Пароль']");
    private final By enterButton = By.xpath("//button[text()='Войти']");
    private final By registrationLink = By.xpath("//a[@href='/register']");
    private final By recoverPasswordLink = By.xpath("//a[@href='/forgot-password']");
    private final By logo = By.xpath(".//div[@class='AppHeader_header__logo__2D0X2']/a[@href='/']");
    private final By enterLink = By.xpath("//a[contains(@href,'/login')]");
    private final WebDriver driver;

    public AuthorizationPage(WebDriver driver) {
        this.driver = driver;
    }
    public String getLink() {
        return driver.getCurrentUrl();
    }
    @Step("Установка почты")
    public void setEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }
    @Step("Установка пароля")
    public void setPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }
    @Step("Нажатие на кнопку Войти")
    public void clickToEnterButton() {
        driver.findElement(enterButton).click();
    }
    @Step("Авторизация пользователя")
    public void userLogin(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickToEnterButton();
    }
    @Step("Нажатие на ссылку Регистрация")
    public void clickRegistrationHref() {
        driver.findElement(registrationLink).click();
    }
    @Step("Нажатие на ссылку Восстановление пароля")
    public void clickRecoverPasswordHref() {
        driver.findElement(recoverPasswordLink).click();
    }
    @Step("Нажатие на логотип")
    public void clickLogo() {
        driver.findElement(logo).click();
    }

    public By getEnterButton() {
        return enterButton;
    }
    @Step("Нажатие на ссылку Войти")
    public void clickEnterHref() {
        driver.findElement(enterLink).click();
    }
}