package UI.pages;

import UI.pages.components.AddFilmFormComponent;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    private final SelenideElement loginButton = $("[data-qa-id=login_page_button]");
    private final SelenideElement profileButton = $("[data-qa-id=profile_page_button]");
    private final SelenideElement showMoreButton = $(byText("Показать еще"));
    private final SelenideElement firstReadMoreButton = $("[data-qa-id='more_button']");
    private final SelenideElement allMoviesButton = $("a[href='/movies?page=1']");
    private final SelenideElement movieCreateButton = $("[data-qa-id=movie_create_button]");
    private final SelenideElement profileAdmin = $("a[href='/dashboard']");

    public MoviesPage clickShowMoreButton() {
        showMoreButton.scrollIntoView(true).click();
        firstReadMoreButton.shouldHave(visible);
        return new MoviesPage();
    }

    public AddFilmFormComponent clickMovieCreateButton() {
        movieCreateButton.click();
        return new AddFilmFormComponent().waitUntilLoaded();
    }

    public LoginPage clickLoginButton() {
        loginButton.shouldBe(visible).click();
        return new LoginPage();
    }

    public MovieDetailsPage clickFirstReadMoreButton() {
        firstReadMoreButton.shouldBe(visible).click();
        return new MovieDetailsPage();
    }

    public DashboardPage clickProfileAdmin() {
        profileAdmin.click();
        return new DashboardPage();
    }

    public MoviesPage clickShowAllMoviesButton() {
        allMoviesButton.shouldBe(visible).click();
        return new MoviesPage();
    }

    public ProfilePage clickProfileButton() {
        profileButton
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();
        return new ProfilePage();
    }
}
