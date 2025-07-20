package UI.tests;

import UI.pages.MovieDetailsPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import UI.pages.MainPage;
import UI.pages.MoviesPage;
import UI.utils.BaseTest;
import UI.utils.AuthHelper;

import static UI.utils.AuthHelper.loginAsAdmin;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Tag("ui")
public class MoviesTest extends BaseTest {

    @Test
    void filterMoviesByLocation() {
        loginAsAdmin();

        MoviesPage moviesPage = new MainPage()
                .clickShowAllMoviesButton();

        moviesPage.getFilterComponent()
                .selectLocation("SPB");

        /*moviesPage.getMovieCards().shouldHave(sizeGreaterThan(0));*/

    }

    /*@Test
    void paginationWorks() {
        MainPage mainPage = new MainPage();
        mainPage.clickLoginButton()
                .setEmail("test1123123213@email.com")
                .setPassword("12345678Aa")
                .clickSubmit();

        mainPage.waitForProfileButton();

        MoviesPage moviesPage = mainPage.clickShowMoreButton();

        int initialMovieCount = $$(".movie-card").size();
        moviesPage.goToNextPage();

        $$(".movie-card").shouldHave(size(initialMovieCount));
    }*/

    @Test
    void paginationWorks() {
        loginAsAdmin();

        MoviesPage moviesPage = new MainPage()
                .clickShowAllMoviesButton();

        int initialCount = moviesPage.getMovieCards().size();
        moviesPage.goToNextPage();
        moviesPage.getMovieCards().shouldHave(size(initialCount));
    }

    @Test
    void openMovieDetails() {
        loginAsAdmin();

        MoviesPage moviesPage = new MainPage()
                .clickShowMoreButton();

        moviesPage.openRandomMovieDetails();

        $("button").shouldBe(visible);
    }
}
