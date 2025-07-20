package UI.tests;

import UI.dto.PaymentCardDto;
import UI.pages.MainPage;
import UI.pages.MovieDetailsPage;
import UI.pages.MoviesPage;
import UI.pages.PaymentPage;
import UI.utils.AuthHelper;
import UI.utils.BaseTest;
import UI.utils.TestDataGenerator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("ui")
public class PaymentTest extends BaseTest {

    @Test
    void makeSuccessfulPayment() {
        AuthHelper.loginAsAdmin();
        MoviesPage moviesPage = new MainPage().clickShowMoreButton();

        MovieDetailsPage movieDetails = moviesPage.openRandomMovieDetails();

        PaymentPage paymentPage = movieDetails.clickBuyTicketButton();

        PaymentCardDto card = PaymentCardDto.builder()
                .numberTickets(1)
                .cardNumber("4242424242424242")
                .cardHolder("Ivan Ivanov")
                .expiryMonth("Декабрь")
                .expiryYear("2025")
                .cvc("123")
                .build();

        paymentPage.fillCardDetails(card).submitPayment();

        $("div[role='status']")
                .shouldHave(text("Оплата прошла успешно"))
                .shouldBe(visible);
    }

    @Test
    void makePaymentWithInvalidCard() {
        AuthHelper.loginAsAdmin();
        MoviesPage moviesPage = new MainPage().clickShowMoreButton();

        MovieDetailsPage movieDetails = moviesPage.openRandomMovieDetails();

        PaymentPage paymentPage = movieDetails.clickBuyTicketButton();

        PaymentCardDto card = PaymentCardDto.builder()
                .numberTickets(1)
                .cardNumber("0000000000000000")
                .cardHolder("Ivan Ivanov")
                .expiryMonth("Декабрь")
                .expiryYear("2025")
                .cvc("123")
                .build();

        paymentPage.fillCardDetails(card).submitPayment();

        $("div[role='status']")
                .shouldHave(text("Неверные данные карты"))
                .shouldBe(visible);
    }

    /*@Test
    void paymentWithInvalidAmount() {
        AuthHelper.loginAsAdmin();
        MoviesPage moviesPage = new MainPage().clickShowMoreButton();

        MovieDetailsPage movieDetails = moviesPage.openRandomMovieDetails();

        PaymentPage paymentPage = movieDetails.clickBuyTicketButton();

        PaymentCardDto card = PaymentCardDto.builder()
                .numberTickets(0)
                .cardNumber("4242424242424242")
                .cardHolder("IVAN IVANOV")
                .expiryMonth("Декабрь")
                .expiryYear("2025")
                .cvc("123")
                .build();

        paymentPage.fillCardDetails(card).submitPayment();

        $("p.text-red-500.text-sm")
                .shouldBe(visible)
                .shouldHave(exactText("Не может быть меньше 1"));
    }*/
}
