package UI.tests;

import UI.pages.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import UI.utils.BaseTest;
import UI.utils.AuthHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("ui")
class PaymentTest extends BaseTest {

    @Test
    void successfulPayment() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();

        loginPage.setEmail("test1123123213@email.com")
                .setPassword("12345678Aa")
                .clickSubmit();

        mainPage.waitForProfileButton();

        MovieDetailsPage movieDetailsPage = mainPage.clickFirstReadMoreButton();

        PaymentPage paymentPage = movieDetailsPage.clickBuyTicketButton();

        paymentPage.setAmount(2)
                .setCardNumber("4242424242424242")
                .setCardHolder("IVAN IVANOV")
                .setExpiryDate("Декабрь", "2025")
                .setCvc("123")
                .submitPayment();

        $("div[role='status']")
                .shouldHave(text("Оплата прошла успешно"))
                .shouldBe(visible);
    }

    @Test
    void paymentWithInvalidCardNumber() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();

        loginPage.setEmail("test1123123213@email.com")
                .setPassword("12345678Aa")
                .clickSubmit();

        MovieDetailsPage movieDetailsPage = mainPage.clickFirstReadMoreButton();

        PaymentPage paymentPage = movieDetailsPage.clickBuyTicketButton();

        paymentPage.setAmount(1)
                .setCardNumber("1234567891234567")
                .setCardHolder("IVAN IVANOV")
                .setExpiryDate("Декабрь", "2025")
                .setCvc("123")
                .submitPayment();

        $("div[role='status']")
                .shouldHave(text("Неверные данные карты"))
                .shouldBe(visible);

    }

    @Test
    void paymentWithExpiredCard() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();

        loginPage.setEmail("test1123123213@email.com")
                .setPassword("12345678Aa")
                .clickSubmit();

        MovieDetailsPage movieDetailsPage = mainPage.clickFirstReadMoreButton();

        PaymentPage paymentPage = movieDetailsPage.clickBuyTicketButton();

        paymentPage.setAmount(1)
                .setCardNumber("4242424242424242")
                .setCardHolder("IVAN IVANOV")
                .setExpiryDate("Январь", "2025")
                .setCvc("123")
                .submitPayment();

        $("div[role='status']")
                .shouldHave(text("Неверные данные карты"))
                .shouldBe(visible);
    }

    @Test
    void paymentWithInvalidAmount() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();

        loginPage.setEmail("test1123123213@email.com")
                .setPassword("12345678Aa")
                .clickSubmit();
        MovieDetailsPage movieDetailsPage = mainPage.clickFirstReadMoreButton();

        PaymentPage paymentPage = movieDetailsPage.clickBuyTicketButton();

        paymentPage.setAmount(0)
                .setCardNumber("4242424242424242")
                .setCardHolder("IVAN IVANOV")
                .setExpiryDate("Декабрь", "2025")
                .setCvc("123")
                .submitPayment();

        String validationMessage = $("#amount").getAttribute("validationMessage");
        assertThat(validationMessage).isEqualTo("Значение должно быть больше или равно 1.");
    }
}
