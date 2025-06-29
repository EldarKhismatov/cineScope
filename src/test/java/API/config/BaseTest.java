package API.config;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected static String AUTH_URL;
    protected static String MOVIES_URL;
    protected static String PAYMENT_URL;
    protected static String accessToken;

    @BeforeAll
    public static void globalSetup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        loadProperties();

        String loginJson = "{"
                + "\"email\":\"test1123123213@email.com\","
                + "\"password\":\"12345678Aa\""
                + "}";

        accessToken = given()
                .baseUri(AUTH_URL)
                .contentType(ContentType.JSON)
                .body(loginJson)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");

    }

    private static void loadProperties() {
        try (InputStream input = BaseTest.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("config.properties not found in classpath");
            }

            Properties props = new Properties();
            props.load(input);

            AUTH_URL = props.getProperty("base.url.auth");
            MOVIES_URL = props.getProperty("base.url.movies");
            PAYMENT_URL = props.getProperty("base.url.payment");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage(), e);
        }
    }
}
