package UI.pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static java.awt.SystemColor.text;

public class ReviewComponent {

    private final SelenideElement reviewInput = $("[data-qa-id='movie_review_input']");
    private final SelenideElement ratingDropdown = $("button[role='combobox']");
    private final SelenideElement submitButton = $("[data-qa-id='movie_review_submit_button']");

    public ReviewComponent fillReview(String review) {
        reviewInput.setValue(review);
        return this;
    }

    public ReviewComponent setRating(int rating) {
        ratingDropdown.click();
        $("div[role='listbox']").shouldBe(visible);
        $$("div[role='option']")
                .findBy(text(String.valueOf(rating)))
                .click();
        return this;
    }
    public void submit() {
        submitButton.shouldBe(visible).click();
    }
}
