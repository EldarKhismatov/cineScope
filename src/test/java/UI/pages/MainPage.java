package UI.pages;

import UI.pages.components.MovieCardComponent;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {
    private final SelenideElement loginButton = $("[data-qa-id=login_page_button]");
    private final SelenideElement profileButton = $("[data-qa-id=profile_page_button]");

    public MainPage() {
        $("header").shouldBe(visible);
    }

    public LoginPage clickLoginButton() {
        loginButton.shouldBe(visible).click();
        return page(LoginPage.class);
    }

    public void waitForProfileButton() {
        profileButton.shouldBe(visible);
    }

    public MovieDetailsPage clickFirstReadMoreButton() {
        // Find and click the first "Подробнее" button
        $$("button").findBy(text("Подробнее")).shouldBe(visible).click();
        return page(MovieDetailsPage.class);
    }

    public MoviesPage clickShowMoreButton() {
        $$("button").findBy(text("Показать еще")).shouldBe(visible).click();
        return page(MoviesPage.class);
    }

    public MovieCardComponent getFirstMovieCard() {
        return new MovieCardComponent($(".movie-card:first-child"));
    }
}
