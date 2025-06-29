package UI.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class RegisterPage {
    private final SelenideElement fullNameInput = $("[data-qa-id=register_full_name_input]");
    private final SelenideElement emailInput = $("[data-qa-id=register_email_input]");
    private final SelenideElement passwordInput = $("[data-qa-id=register_password_input]");
    private final SelenideElement repeatPasswordInput = $("[data-qa-id=register_password_repeat_input]");
    private final SelenideElement submitButton = $("[data-qa-id=register_submit_button]");
    private final SelenideElement errorMessage = $("div[role='alert']");

    public RegisterPage setFullName(String fullName) {
        fullNameInput.shouldBe(visible);
        fullNameInput.setValue(fullName);
        return this;
    }

    public RegisterPage setEmail(String email) {
        emailInput.setValue(email);
        return this;
    }

    public RegisterPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    public RegisterPage setRepeatPassword(String password) {
        repeatPasswordInput.setValue(password);
        return this;
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public SelenideElement getErrorMessage() {
        return errorMessage;
    }
}
