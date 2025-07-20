package UI.utils;

import UI.pages.LoginPage;
import UI.pages.MainPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class AuthHelper {
    private static final String ADMIN_EMAIL = "test1123123213@email.com";
    private static final String ADMIN_PASSWORD = "12345678Aa";

    public static void loginAsAdmin() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();

        loginPage.setEmail(ADMIN_EMAIL)
                .setPassword(ADMIN_PASSWORD)
                .clickSubmit();

        $("header").shouldBe(visible, Duration.ofSeconds(15));
    }
}
