package UI.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import UI.pages.LoginPage;
import UI.pages.MainPage;
import UI.pages.RegisterPage;
import UI.utils.BaseTest;
import UI.utils.TestDataGenerator;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;

@Tag("ui")
class AuthTest extends BaseTest {

    @Test
    void successfulLogin() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();

        loginPage.setEmail("test1123123213@email.com")
                .setPassword("12345678Aa")
                .clickSubmit();

        $("div[role='status']")
                .shouldHave(text("Вы вошли в аккаунт"))
                .shouldBe(visible);

    }

    @Test
    void failedLoginWithInvalidCredentials() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();

        loginPage.setEmail("invalid@email.com")
                .setPassword("wrongpassword")
                .clickSubmit();

        $("div[role='status']")
                .shouldHave(text("Неверная почта или пароль"))
                .shouldBe(visible);
    }

    @Test
    void successfulRegistration() {
        String email = TestDataGenerator.randomEmail();
        String password = TestDataGenerator.randomPassword();

        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        RegisterPage registerPage = loginPage.clickRegisterLink();

        registerPage.setFullName("Сергеев Сергей Сергеевич")
                .setEmail(email)
                .setPassword(password)
                .setRepeatPassword(password)
                .clickSubmit();

        $("div[role='status']")
                .shouldHave(text("Вы зарегистрировались"))
                .shouldBe(visible);

    }

    @Test
    void registrationWithExistingEmail() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        RegisterPage registerPage = loginPage.clickRegisterLink();

        registerPage.setFullName("Иванов Иван Иванович")
                .setEmail("test1123123213@email.com")
                .setPassword("12345678Aa")
                .setRepeatPassword("12345678Aa")
                .clickSubmit();

        $("div[role='status']")
                .shouldHave(text("Пользователь с таким email уже существует"))
                .shouldBe(visible);
    }
}
