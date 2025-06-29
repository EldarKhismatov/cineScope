package UI.pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FilterComponent {
    private final SelenideElement locationFilter = $("[data-qa-id='movies_filter_location_select']").parent();
    private final SelenideElement genreFilter = $("[data-qa-id='movies_filter_genre_select']").parent();
    private final SelenideElement sortFilter = $("[data-qa-id='movies_filter_created_at_select']").parent();

    public FilterComponent selectLocation(String location) {
        locationFilter.shouldBe(visible).click();
        $$("div[role='option']").findBy(text(location)).shouldBe(visible).click();
        return this;
    }

    public FilterComponent selectGenre(String genre) {
        genreFilter.shouldBe(visible).click();
        $$("div[role='option']").findBy(text(genre)).shouldBe(visible).click();
        return this;
    }

    public FilterComponent selectSort(String sortOption) {
        sortFilter.shouldBe(visible).click();
        $$("div[role='option']").findBy(text(sortOption)).shouldBe(visible).click();
        return this;
    }
}
