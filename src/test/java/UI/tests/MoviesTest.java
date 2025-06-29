package UI.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import UI.pages.MainPage;
import UI.pages.MoviesPage;
import UI.utils.BaseTest;
import UI.utils.AuthHelper;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("ui")
class MoviesTest extends BaseTest {

    @Test
    void filterMoviesByLocation() {
        MainPage mainPage = new MainPage();
        mainPage.clickLoginButton()
                .setEmail("test1123123213@email.com")
                .setPassword("12345678Aa")
                .clickSubmit();

        mainPage.waitForProfileButton();

        MoviesPage moviesPage = mainPage.clickShowMoreButton();

        moviesPage.getFilterComponent()
                .selectLocation("SPB");
    }

    @Test
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
    }

    @Test
    void openMovieDetails() {
        MainPage mainPage = new MainPage();
        mainPage.clickLoginButton()
                .setEmail("test1123123213@email.com")
                .setPassword("12345678Aa")
                .clickSubmit();

        mainPage.waitForProfileButton();

        MoviesPage moviesPage = mainPage.clickShowMoreButton();

        moviesPage.openFirstMovieDetails()
                .shouldHavePrice();
    }
}
