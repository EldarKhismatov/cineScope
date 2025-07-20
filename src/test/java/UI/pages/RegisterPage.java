package UI.pages;

import com.codeborne.selenide.SelenideElement;
import UI.utils.TestDataGenerator;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class RegisterPage {

    private final SelenideElement fullNameInput = $("[data-qa-id=register_full_name_input]");
    private final SelenideElement emailInput = $("[data-qa-id=register_email_input]");
    private final SelenideElement passwordInput = $("[data-qa-id=register_password_input]");
    private final SelenideElement passwordRepeatInput = $("[data-qa-id=register_password_repeat_input]");
    private final SelenideElement submitButton = $("[data-qa-id=register_submit_button]");

    public RegisterPage setFullName(String name) {
        fullNameInput.shouldBe(visible).setValue(name);
        return this;
    }

    public RegisterPage setEmail(String email) {
        emailInput.shouldBe(visible).setValue(email);
        return this;
    }

    public RegisterPage setPassword(String password) {
        passwordInput.shouldBe(visible).setValue(password);
        return this;
    }

    public RegisterPage setPasswordRepeat(String password) {
        passwordRepeatInput.shouldBe(visible).setValue(password);
        return this;
    }

    public MainPage clickSubmit() {
        submitButton.shouldBe(visible).click();
        return new MainPage();
    }

    // Optional helper
    public MainPage registerRandomValidUser() {
        return this.setFullName(TestDataGenerator.randomFullName())
                .setEmail(TestDataGenerator.randomEmail())
                .setPassword("12345678Aa")
                .setPasswordRepeat("12345678Aa")
                .clickSubmit();
    }
}
