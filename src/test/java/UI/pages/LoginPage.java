package UI.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    private final SelenideElement emailInput = $("[data-qa-id=login_email_input]");
    private final SelenideElement passwordInput = $("[data-qa-id=login_password_input]");
    private final SelenideElement registerLink = $("[href='/register']");
    private final SelenideElement submitButton = $("[data-qa-id=login_submit_button]");

    public LoginPage setEmail(String email) {
        emailInput.shouldBe(visible).setValue(email);
        return this;
    }

    public RegisterPage clickRegisterLink() {
        registerLink.click();
        return page(RegisterPage.class);
    }

    public LoginPage setPassword(String password) {
        passwordInput.shouldBe(visible).setValue(password);
        return this;
    }

    public MainPage clickSubmit() {
        submitButton.shouldBe(visible).click();
        return new MainPage();
    }
}
