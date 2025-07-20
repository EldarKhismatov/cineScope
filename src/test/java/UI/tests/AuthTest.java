package UI.tests;

import UI.dto.FilmDto;
import UI.pages.DashboardPage;
import UI.pages.LoginPage;
import UI.pages.MainPage;
import UI.pages.RegisterPage;
import UI.pages.components.AddFilmFormComponent;
import UI.utils.BaseTest;
import UI.utils.FilmDataFactory;
import UI.utils.TestDataGenerator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("ui")
public class AuthTest extends BaseTest {

    @Test
    void loginWithValidCredentials() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();

        loginPage.setEmail("test1123123213@email.com")
                .setPassword("12345678Aa")
                .clickSubmit();

        $("[data-qa-id=profile_page_button]").shouldBe(visible);
    }
    @Test
    void loginWithInvalidPassword() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();

        loginPage.setEmail("test1123123213@email.com")
                .setPassword("WrongPass123!")
                .clickSubmit();

        $("div[role='status']")
                .shouldBe(visible)
                .shouldHave(text("Неверная почта или пароль"));
    }

    @Test
    void registerWithValidData() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        RegisterPage registerPage = loginPage.clickRegisterLink();

        String email = TestDataGenerator.randomEmail();
        String password = TestDataGenerator.randomPassword();

        registerPage.setFullName(TestDataGenerator.randomFullName())
                .setEmail(email)
                .setPassword(password)
                .setPasswordRepeat(password)
                .clickSubmit();

        $("div[role='status']")
                .shouldBe(visible)
                .shouldHave(text("Вы зарегистрировались"));
    }

    @Test
    void registerWithMismatchedPasswords() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        RegisterPage registerPage = loginPage.clickRegisterLink();

        registerPage.setFullName("User Test")
                .setEmail(TestDataGenerator.randomEmail())
                .setPassword("12345678Aa")
                .setPasswordRepeat("12345678Ab")
                .clickSubmit();

        $("p[role='alert']").shouldHave(text("Введенные пароли не совпадают"));
    }
}
