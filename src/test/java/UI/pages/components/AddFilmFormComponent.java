package UI.pages.components;

import UI.dto.FilmDto;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class AddFilmFormComponent {

    private final SelenideElement dialog = $("div[role='dialog']");
    private final SelenideElement titleInput = $("input[data-qa-id='movie_name_input']");
    private final SelenideElement descriptionTextarea = $("textarea[data-qa-id='movie_description_input']");
    private final SelenideElement priceInput = $("input[data-qa-id='movie_price_input']");
    private final SelenideElement locationDropdown = $("button[data-qa-id='movie_location_select']");
    private final SelenideElement imageUrlInput = $("input[data-qa-id='movie_image_url_input']");
    private final SelenideElement genreDropdown = $("button[data-qa-id='movie_genre_select']");
    private final SelenideElement publishedCheckbox = $("button[role='checkbox'][data-qa-id='movie_published_checkbox']");
    private final SelenideElement submitButton = $("button[data-qa-id='movie_submit_button']");

    private final SelenideElement titleError = titleInput.sibling(0);
    private final SelenideElement descriptionError = descriptionTextarea.sibling(0);
    private final SelenideElement imageUrlError = imageUrlInput.sibling(0);
    private final SelenideElement genreError = $x("//button[@data-qa-id='movie_genre_select']/following-sibling::p");

    private String urlImage = "https://i.pinimg.com/originals/b2/dc/9c/b2dc9c2cee44e45672ad6e3994563ac2.jpg";

    public AddFilmFormComponent waitUntilLoaded() {
        dialog.shouldBe(visible, Duration.ofSeconds(10));
        return this;
    }

    public void fillForm(FilmDto filmData) {
        setTitle(filmData.getTitle());
        setDescription(filmData.getDescription());
        setPrice(filmData.getPrice());

        if (filmData.getLocation() != null) {
            selectLocation(filmData.getLocation());
        }

        setImageUrl(filmData.getImageUrl());

        if (filmData.getGenre() != null) {
            selectGenre(filmData.getGenre());
        }

        setPublished(filmData.isPublished());
    }

    public void setTitle(String title) {
        titleInput.setValue(title);
    }

    public void setDescription(String description) {
        descriptionTextarea.setValue(description);
    }

    public void setPrice(int price) {
        priceInput.setValue(String.valueOf(price));
    }

    public void setImageUrl(String url) {

        imageUrlInput.setValue(urlImage);
    }

    public void setPublished(boolean state) {
        String currentState = publishedCheckbox.getAttribute("data-state");
        if (("checked".equals(currentState) != state)) {
            publishedCheckbox.click();
        }
    }

    public void submit() {
        submitButton.click();
    }

    public String getTitleError() {
        return titleError.shouldBe(visible).text();
    }

    public String getDescriptionError() {
        return descriptionError.shouldBe(visible).text();
    }

    public String getImageUrlError() {
        return imageUrlError.shouldBe(visible).text();
    }

    public String getGenreError() {
        return genreError.shouldBe(visible).text();
    }

    public void selectLocation(String location) {
        locationDropdown.click();
        $x(String.format("//div[@role='option' and contains(., '%s')]", location))
                .shouldBe(visible, Duration.ofSeconds(5))
                .click();
    }

    public void selectGenre(String genre) {
        genreDropdown.click();
        $x(String.format("//div[@role='option' and contains(., '%s')]", genre))
                .shouldBe(visible, Duration.ofSeconds(5))
                .click();
    }

    public void close() {
        $("button[class*='absolute right-4 top-4']").click();
    }
}
