package UI.pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage {
    private final SelenideElement adminPanelLink = $("a[href='/dashboard']");

    public DashboardPage clickAdminPanelLink() {
        adminPanelLink
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(interactable, Duration.ofSeconds(15))
                .click();
        return new DashboardPage();
    }
}
