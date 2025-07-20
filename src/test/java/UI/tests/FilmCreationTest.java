package UI.tests;

import UI.dto.FilmDto;
import UI.pages.DashboardPage;
import UI.pages.MainPage;
import UI.pages.ProfilePage;
import UI.pages.components.AddFilmFormComponent;
import UI.utils.AuthHelper;
import UI.utils.BaseTest;
import UI.utils.FilmDataFactory;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmCreationTest extends BaseTest {

    @Test
    void shouldCreateFilmWithValidData() {
        AuthHelper.loginAsAdmin();

        MainPage mainPage = new MainPage();
        ProfilePage profilePage = mainPage.clickProfileButton();
        DashboardPage dashboardPage = profilePage.clickAdminPanelLink();

        AddFilmFormComponent form = dashboardPage.openMoviesSection()
                .clickCreateMovie();

        form.fillForm(FilmDataFactory.validFilm());
        form.submit();

        $("div[role='status'][aria-live='polite']")
                .shouldHave(text("Фильм успешно добавлен"))
                .shouldBe(visible);
    }

    @Test
    void shouldShowValidationErrors() {
        AuthHelper.loginAsAdmin();

        MainPage mainPage = new MainPage();
        ProfilePage profilePage = mainPage.clickProfileButton();
        DashboardPage dashboardPage = profilePage.clickAdminPanelLink();

        AddFilmFormComponent form = dashboardPage.openMoviesSection()
                .clickCreateMovie();

        form.fillForm(FilmDataFactory.withEmptyFields());
        form.submit();

        String validationMessage = Selenide.executeJavaScript(
                "return arguments[0].validationMessage;",
                $("[data-qa-id='movie_price_input']")
        );

        assert validationMessage.equals("Значение должно быть больше или равно 1.");
    }

}
