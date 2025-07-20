package UI.pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class MovieCardComponent {

    private final SelenideElement root;

    public MovieCardComponent(SelenideElement root) {
        this.root = root;
    }

    public MovieCardComponent shouldHaveTitle(String title) {
        root.$(".movie-title").shouldHave(text(title));
        return this;
    }

    public MovieCardComponent shouldHavePrice(int price) {
        root.$(".movie-price").shouldHave(text(price + " руб."));
        return this;
    }

    public MovieCardComponent shouldHaveRating(double rating) {
        root.$(".movie-rating").shouldHave(text(String.valueOf(rating)));
        return this;
    }

    public void clickDetails() {
        root.$(".details-button").click();
    }
}
