package UI.tests;

import UI.pages.MainPage;
import UI.pages.MovieDetailsPage;
import UI.pages.MoviesPage;
import UI.utils.AuthHelper;
import UI.utils.BaseTest;
import UI.utils.TestDataGenerator;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Tag("ui")
public class ReviewsTest extends BaseTest {

    @Test
    void addReviewToMovie() {
        AuthHelper.loginAsAdmin();
        MoviesPage moviesPage = new MainPage().clickShowMoreButton();

        MovieDetailsPage movieDetailsPage = moviesPage.openRandomMovieDetails();

        movieDetailsPage.getReviewComponent()
                .fillReview("Отличный фильм!")
                .setRating(5)
                .submit();

        $("div[role='status']")
                .shouldHave(text("Отзыв успешно создан"))
                .shouldBe(visible);
    }

    @Test
    void reviewValidationShouldFailWithShortText() {
        AuthHelper.loginAsAdmin();
        MoviesPage moviesPage = new MainPage().clickShowMoreButton();

        MovieDetailsPage movieDetailsPage = moviesPage.openRandomMovieDetails();

        movieDetailsPage.getReviewComponent()
                .fillReview("кл")
                .setRating(5)
                .submit();

        String validationMessage = Selenide.executeJavaScript(
                "return arguments[0].validationMessage;",
                $("textarea[data-qa-id='movie_review_input']")
        );

        String message = "Минимально допустимое количество символов: 5. Длина текста сейчас: 2.";

        assert validationMessage.equals(message);

    }
}
