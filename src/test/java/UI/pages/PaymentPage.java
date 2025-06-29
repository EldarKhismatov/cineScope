package UI.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class PaymentPage {
    private final SelenideElement amountInput = $("[data-qa-id=payment_amount_input]");
    private final SelenideElement cardNumberInput = $("[data-qa-id=payment_card_number_input]");
    private final SelenideElement cardHolderInput = $("[data-qa-id=payment_card_holder_input]");
    private final SelenideElement monthSelect = $("[data-qa-id=payment_card_month_select]");
    private final SelenideElement yearSelect = $("[data-qa-id=payment_card_year_select]");
    private final SelenideElement cvcInput = $("[data-qa-id=payment_card_cvc_input]");
    private final SelenideElement submitButton = $("[data-qa-id=payment_submit_button]");
    private final SelenideElement successMessage = $(".payment-success");
    private final SelenideElement errorMessage = $(".payment-error");

    public PaymentPage setAmount(int amount) {
        amountInput.setValue(String.valueOf(amount));
        return this;
    }

    public PaymentPage setCardNumber(String cardNumber) {
        cardNumberInput.setValue(cardNumber);
        return this;
    }

    public PaymentPage setCardHolder(String cardHolder) {
        cardHolderInput.setValue(cardHolder);
        return this;
    }

    public PaymentPage setExpiryDate(String month, String year) {
        monthSelect.shouldBe(visible).click();
        $$("div[role='option']").findBy(text(month)).shouldBe(visible).click();

        yearSelect.shouldBe(visible).click();
        $$("div[role='option']").findBy(text(year)).shouldBe(visible).click();

        return this;
    }

    public PaymentPage setCvc(String cvc) {
        cvcInput.setValue(cvc);
        return this;
    }

    public PaymentPage submitPayment() {
        submitButton.click();
        return this;
    }

    public PaymentPage shouldShowSuccess() {
        $("div[role='status']")
                .shouldHave(text("Оплата прошла успешно"))
                .shouldBe(visible);
        return this;
    }

    public PaymentPage shouldShowError(String errorText) {
        errorMessage.shouldBe(visible)
                .shouldHave(text(errorText));
        return this;
    }

    public PaymentPage fillCardDetails(String cardNumber, String cardHolder, String expiryDate, int cvc) {
        String[] expiryParts = expiryDate.split("/");
        return setCardNumber(cardNumber)
                .setCardHolder(cardHolder)
                .setExpiryDate(expiryParts[0], expiryParts[1])
                .setCvc(String.valueOf(cvc));
    }
}
