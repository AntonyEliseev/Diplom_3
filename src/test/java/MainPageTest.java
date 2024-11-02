import helpers.Constant;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.MainPage;

import java.time.Duration;

import static helpers.Constant.DEFAULT_WAIT_MS;
import static org.junit.Assert.assertTrue;

public class MainPageTest {
    WebDriver driver;
    MainPage mainPage;

    @Before
    public void setUp() {
        driver = WebDriverFactory.getDriver();
        mainPage = new MainPage(driver);
        driver.get(Constant.MAIN_PAGE_URL);
    }

    @Test
    @DisplayName("Переключение раздела 'Булки'")
    public void checkBunsIsDisplayedTest() {
        mainPage.clickSaucesButton();
        mainPage.clickBunsButton();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(DEFAULT_WAIT_MS));
        assertTrue(mainPage.bunsHeaderIsDisplayed());
    }

    @Test
    @DisplayName("Переключение раздела 'Соусы'")
    public void checkSaucesIsDisplayedTest() {
        mainPage.clickSaucesButton();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(DEFAULT_WAIT_MS));
        assertTrue(mainPage.saucesHeaderIsDisplayed());
    }

    @Test
    @DisplayName("Переключение раздела 'Начинки'")
    public void checkFillingsIsDisplayedTest() {
        mainPage.clickFillingsButton();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(DEFAULT_WAIT_MS));
        assertTrue(mainPage.fillingsHeaderIsDisplayed());
    }

    @After
    public void close() {
        driver.quit();
    }
}