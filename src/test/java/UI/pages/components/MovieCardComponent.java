package UI.pages.components;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class MovieCardComponent {
    private final SelenideElement root;
    private final SelenideElement title = $(".movie-title");
    private final SelenideElement price = $(".movie-price");
    private final SelenideElement detailsButton = $(".details-button");
    private final SelenideElement rating = $(".movie-rating");

    public MovieCardComponent(SelenideElement root) {
        this.root = root;
    }

    public MovieCardComponent shouldHaveTitle(String expectedTitle) {
        title.shouldHave(text(expectedTitle));
        return this;
    }

    public MovieCardComponent shouldHavePrice(int expectedPrice) {
        price.shouldHave(text(String.valueOf(expectedPrice) + " руб."));
        return this;
    }

    public MovieCardComponent shouldHaveRating(double expectedRating) {
        rating.shouldHave(text(String.valueOf(expectedRating)));
        return this;
    }

    public void clickDetails() {
        detailsButton.click();
    }
}
