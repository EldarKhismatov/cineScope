package UI.tests;

import UI.pages.MovieDetailsPage;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import UI.pages.MainPage;
import UI.pages.MoviesPage;
import UI.utils.BaseTest;
import UI.utils.AuthHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("ui")
class ReviewsTest extends BaseTest {

    @Test
    void addReviewToMovie() {
        AuthHelper.loginAsAdmin();

        MainPage mainPage = new MainPage();
        MovieDetailsPage movieDetailsPage = mainPage.clickFirstReadMoreButton();

        movieDetailsPage
                .getReviewComponent()
                .setReviewText("Отличный фильм!")
                .setRating(5)
                .submitReview();

        $("div[role='status']")
                .shouldHave(text("Отзыв успешно создан"))
                .shouldBe(visible);
    }

    @Test
    void reviewValidation() {
        AuthHelper.loginAsAdmin();

        MainPage mainPage = new MainPage();
        MovieDetailsPage movieDetailsPage = mainPage.clickFirstReadMoreButton();

        SelenideElement reviewTextarea = $("textarea[data-qa-id='movie_review_input']");

        movieDetailsPage
                .getReviewComponent()
                .setReviewText("кл")
                .setRating(5)
                .submitReview();

        String validationMessage = executeJavaScript(
                "return arguments[0].validationMessage;",
                reviewTextarea
        );
        assertThat(validationMessage)
                .isEqualTo("Минимально допустимое количество символов: 5. Длина текста сейчас: 2.");
    }
}
