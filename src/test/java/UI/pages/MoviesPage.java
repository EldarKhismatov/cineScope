package UI.pages;

import UI.pages.components.FilterComponent;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class MoviesPage {

    private final FilterComponent filterComponent = new FilterComponent();
    private final SelenideElement nextPageButton = $("a[href*='/movies?page=']");
    private final ElementsCollection moreButtons = $$("[data-qa-id='more_button']");

    public MoviesPage goToNextPage() {
        nextPageButton.shouldBe(visible).click();
        return this;
    }

    public MovieDetailsPage openRandomMovieDetails() {
        moreButtons.shouldHave(sizeGreaterThan(0));

        int randomIndex = new Random().nextInt(moreButtons.size());
        SelenideElement randomButton = moreButtons.get(randomIndex);

        randomButton.scrollIntoView(true).shouldBe(visible, enabled).click();

        return new MovieDetailsPage();
    }

    public FilterComponent getFilterComponent() {
        return filterComponent;
    }

    public ElementsCollection getMovieCards() {
        return $$(".border-2.rounded-lg");
    }

}

