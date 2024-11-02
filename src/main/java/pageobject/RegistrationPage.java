package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static helpers.Constant.DEFAULT_TIMEOUT;

public class RegistrationPage {
    private final By nameField = By.xpath("//fieldset[1]//input");
    private final By emailField = By.xpath("//fieldset[2]//input");
    private final By passwordField = By.xpath("//input[@name='Пароль']");
    private final By registrationButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By warningIncorrectPassword= By.xpath("//p[text()='Некорректный пароль']");
    private final By enterButton = By.className("Auth_link__1fOlj");
    private final WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }
    @Step("Установка имени")
    public void setName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }
    @Step("Установка почты")
    public void setEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }
    @Step("Установка пароля")
    public void setPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }
    @Step("Нажатие на кнопку Зарегистрироваться")
    public void clickRegistrationButton() {
        driver.findElement(registrationButton).click();
    }
    @Step("Регистрация пользователя")
    public void userRegistration(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegistrationButton();
    }
    @Step("Нажатие на кнопку Регистрация")
    public void clickEnterButton() {
        driver.findElement(enterButton).click();
    }
    @Step("Проверка отображения ошибки некорректного пароля")
    public String getWarningIncorrectPassword() {
        WebElement element = driver.findElement(warningIncorrectPassword);
        return element.getText();
    }
    public void waitError() {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(warningIncorrectPassword));
    }
}