package UI.utils;

import UI.pages.LoginPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Arrays;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class BaseTest {

    @BeforeAll
    public static void setupAll() {
        System.setProperty("chromedriver.exe", "src/test/resources");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=ru");

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://dev-cinescope.t-qa.ru";
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 30000;
        Configuration.headless = false;
        Configuration.browserCapabilities = options;


        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
                .includeSelenideSteps(true));
    }

    @BeforeEach
    public void setup() {
        open("/");
        $("body").shouldBe(visible, Duration.ofSeconds(15));
        $("header").shouldBe(visible, Duration.ofSeconds(10));
    }

    @AfterEach
    public void teardown() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
        Selenide.closeWebDriver();
    }
}
