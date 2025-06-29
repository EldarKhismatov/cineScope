package UI.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    private final SelenideElement emailInput = $("[data-qa-id=login_email_input]");
    private final SelenideElement passwordInput = $("[data-qa-id=login_password_input]");
    private final SelenideElement submitButton = $("[data-qa-id=login_submit_button]");
    private final SelenideElement registerLink = $("[href='/register']");
    private final SelenideElement errorMessage = $("div[role='alert']");

    public LoginPage() {
        emailInput.shouldBe(visible);
        passwordInput.shouldBe(visible);
    }

    public LoginPage setEmail(String email) {
        emailInput.setValue(email);
        return this;
    }

    public LoginPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public RegisterPage clickRegisterLink() {
        registerLink.click();
        return page(RegisterPage.class);
    }

    public SelenideElement getErrorMessage() {
        return errorMessage;
    }
}
