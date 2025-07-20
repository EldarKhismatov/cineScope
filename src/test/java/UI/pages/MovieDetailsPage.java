package UI.pages;

import UI.pages.components.ReviewComponent;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class MovieDetailsPage {

    private final SelenideElement buyTicketButton = $$("button").findBy(text("Купить билет"));
    private final ReviewComponent reviewComponent = new ReviewComponent();

    public PaymentPage clickBuyTicketButton() {
        buyTicketButton.shouldBe(visible).click();
        return new PaymentPage();
    }

    public ReviewComponent getReviewComponent() {
        return reviewComponent;
    }

    public MovieDetailsPage shouldHavePrice() {
        buyTicketButton.shouldHave(matchText("\\d+ руб\\."));
        return this;
    }
}
