package UI.pages;

import com.codeborne.selenide.SelenideElement;
import UI.pages.components.AddFilmFormComponent;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    private final SelenideElement moviesLink = $("[href='/dashboard/movies']");
    private final SelenideElement createButton = $("[data-qa-id='movie_create_button']");

    public DashboardPage openMoviesSection() {
        moviesLink.click();
        return this;
    }

    public AddFilmFormComponent clickCreateMovie() {
        createButton.click();
        return new AddFilmFormComponent().waitUntilLoaded();
    }
}
