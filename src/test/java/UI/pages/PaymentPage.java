package UI.pages;

import UI.dto.PaymentCardDto;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class PaymentPage {

    private final SelenideElement ticketsInput = $("[data-qa-id=payment_amount_input]");
    private final SelenideElement cardNumberInput = $("[data-qa-id=payment_card_number_input]");
    private final SelenideElement cardHolderInput = $("[data-qa-id=payment_card_holder_input]");
    private final SelenideElement monthSelect = $("[data-qa-id=payment_card_month_select]");
    private final SelenideElement yearSelect = $("[data-qa-id=payment_card_year_select]");
    private final SelenideElement cvcInput = $("[data-qa-id=payment_card_cvc_input]");
    private final SelenideElement payButton = $("[data-qa-id=payment_submit_button]");


    public PaymentPage fillCardDetails(PaymentCardDto card) {
        ticketsInput.shouldBe(visible).setValue(String.valueOf(card.getNumberTickets()));
        cardNumberInput.shouldBe(visible).setValue(card.getCardNumber());
        cardHolderInput.shouldBe(visible).setValue(card.getCardHolder());
        setExpiryDate(card.getExpiryMonth(), card.getExpiryYear());
        cvcInput.shouldBe(visible).setValue(card.getCvc());
        return this;
    }

    public PaymentPage setExpiryDate(String month, String year) {
        monthSelect.shouldBe(visible).click();
        $$("div[role='option']").findBy(text(month)).shouldBe(visible).click();

        yearSelect.shouldBe(visible).click();
        $$("div[role='option']").findBy(text(year)).shouldBe(visible).click();

        return this;
    }

    public PaymentPage submitPayment() {
        payButton.shouldBe(enabled).click();
        return this;
    }

    public PaymentPage shouldShowSuccess() {
        $("[role='status']")
                .shouldBe(visible)
                .shouldHave(text("Оплата прошла успешно"));
        return this;
    }

    public PaymentPage shouldShowError() {
        $("[role='alert']")
                .shouldBe(visible)
                .shouldHave(text("Неверные данные карты"));
        return this;
    }

    public SelenideElement getTicketsInput() {
        return ticketsInput;
    }

}
