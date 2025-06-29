package UI.pages;

import com.codeborne.selenide.SelenideElement;
import UI.pages.components.FilterComponent;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class MoviesPage {
    private final FilterComponent filterComponent = new FilterComponent();
    private final SelenideElement nextPageButton = $("a[aria-label='Go to next page']");
    private final SelenideElement readMoreButton = $("[data-qa-id=more_button]");

    public FilterComponent getFilterComponent() {
        return filterComponent;
    }

    public MoviesPage goToNextPage() {
        nextPageButton.shouldBe(visible).scrollTo().click();
        return this;
    }

    public MovieDetailsPage openFirstMovieDetails() {
        $$("button").findBy(text("Подробнее")).shouldBe(visible).click();
        return page(MovieDetailsPage.class);
    }

    public MoviesPage openReadMoreByFilm() {
        $$("button")
                .findBy(text("Подробнее"))
                .shouldBe(visible)
                .click();
        return this;
    }
}
