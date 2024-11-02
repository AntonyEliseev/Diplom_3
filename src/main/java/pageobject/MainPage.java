package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static helpers.Constant.DEFAULT_TIMEOUT;

public class MainPage {
    private final By personalAreaButton = By.xpath("//a[contains(@href,'/account')]");
    private final By enterAccountButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By bunsSwitch = By.xpath("//span[text()='Булки']");
    private final By saucesSwitch = By.xpath("//span[text()='Соусы']");
    private final By fillingsSwitch = By.xpath("//span[text()='Начинки']");
    private final By bunsIsSelected = By.xpath(".//span[text()='Булки']/parent::div[contains(@class,'tab_tab_type_current__2BEPc')]");
    private final By saucesIsSelected = By.xpath(".//span[text()='Соусы']/parent::div[contains(@class,'tab_tab_type_current__2BEPc')]");
    private final By fillingsIsSelected = By.xpath(".//span[text()='Начинки']/parent::div[contains(@class,'tab_tab_type_current__2BEPc')]");
    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }
    public String getLink() {
        return driver.getCurrentUrl();
    }
    @Step("Переход в личный кабинет")
    public void clickPersonalAreaButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.elementToBeClickable(personalAreaButton));
        driver.findElement(personalAreaButton).click();
    }
    @Step("Нажатие на кнопку Войти в аккаунт")
    public void clickEnterAccountButton() {
        driver.findElement(enterAccountButton).click();
    }
    @Step("Нажатие на кнопку Булки")
    public void clickBunsButton() {
        driver.findElement(bunsSwitch).click();
    }
    @Step("Нажатие на кнопку Соусы")
    public void clickSaucesButton() {
        driver.findElement(saucesSwitch).click();
    }
    @Step("Нажатие на кнопку Начинки")
    public void clickFillingsButton() {
        driver.findElement(fillingsSwitch).click();
    }
    @Step("Проверка отображения заголовка Булки")
    public boolean bunsHeaderIsDisplayed() {
        return driver.findElement(bunsIsSelected).isDisplayed();
    }
    @Step("Проверка отображения заголовка Соусы")
    public boolean saucesHeaderIsDisplayed() {
        return driver.findElement(saucesIsSelected).isDisplayed();
    }
    @Step("Проверка отображения заголовка Начинки")
    public boolean fillingsHeaderIsDisplayed() {
        return driver.findElement(fillingsIsSelected).isDisplayed();
    }
}