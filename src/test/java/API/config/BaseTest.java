package API.config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.filters;

public class BaseTest {
    protected static final String AUTH_URL = "https://auth.dev-cinescope.krisqa.ru";
    protected static final String MOVIES_URL = "https://api.dev-cinescope.krisqa.ru";
    protected static final String PAYMENT_URL = "https://payment.dev-cinescope.krisqa.ru";

    protected String accessToken;
    protected String refreshToken;
    public String testUserEmail;

    @BeforeAll
    public static void globalSetup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        filters(new AllureRestAssured());
    }
}
