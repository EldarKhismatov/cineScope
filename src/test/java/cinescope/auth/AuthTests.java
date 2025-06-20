package cinescope.auth;

import cinescope.config.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthTests extends BaseTest {

    private final String staticEmail = "test1123123213@email.com";
    private final String staticPassword = "12345678Aa";

    private String refreshToken;
    private String testUserId;

    @BeforeEach
    void reLoginBeforeEachTest() {
        String loginJson = "{"
                + "\"email\":\"" + staticEmail + "\","
                + "\"password\":\"" + staticPassword + "\""
                + "}";

        var response = given()
                .baseUri(AUTH_URL)
                .contentType(ContentType.JSON)
                .body(loginJson)
                .when()
                .post("/login")
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .extract();

        accessToken = response.path("accessToken");
        refreshToken = response.path("refreshToken");
    }

    @Test

    @Order(1)
    void testRegisterWithMismatchedPasswords() {
        String body = "{"
                + "\"email\":\"mismatch@example.com\","
                + "\"fullName\":\"Имя Фамилия\","
                + "\"password\":\"12345678Aa\","
                + "\"passwordRepeat\":\"WrongPassword1\""
                + "}";

        given()
                .baseUri(AUTH_URL)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/register")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(2)
    void testRegisterWithInvalidEmail() {
        String body = "{"
                + "\"email\":\"not-an-email\","
                + "\"fullName\":\"Имя Фамилия\","
                + "\"password\":\"12345678Aa\","
                + "\"passwordRepeat\":\"12345678Aa\""
                + "}";

        given()
                .baseUri(AUTH_URL)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/register")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(3)
    void testRegisterWithWeakPassword() {
        String body = "{"
                + "\"email\":\"weak" + UUID.randomUUID() + "@mail.com\","
                + "\"fullName\":\"Имя Фамилия\","
                + "\"password\":\"123\","
                + "\"passwordRepeat\":\"123\""
                + "}";

        given()
                .baseUri(AUTH_URL)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/register")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(4)
    void testLoginUser() {
        String loginJson = "{"
                + "\"email\":\"" + staticEmail + "\","
                + "\"password\":\"" + staticPassword + "\""
                + "}";

        given()
                .baseUri(AUTH_URL)
                .contentType(ContentType.JSON)
                .body(loginJson)
                .when()
                .post("/login")
                .then()
                .statusCode(201)
                .body("accessToken", notNullValue());
    }

    @Test
    @Order(5)
    void testLogout() {
        given()
                .baseUri(AUTH_URL)
                .auth().oauth2(refreshToken)
                .when()
                .get("/logout")
                .then()
                .statusCode(anyOf(is(200), is(201)));
    }

    @Test
    @Order(6)
    void testRefreshTokens() {
        given()
                .baseUri(AUTH_URL)
                .cookie("refresh_token", refreshToken)
                .when()
                .get("/refresh-tokens")
                .then()
                .statusCode(anyOf(is(200), is(201)));
    }

    @Test
    @Order(7)
    void testCreateUser() {
        String email = "newuser" + UUID.randomUUID().toString().substring(0, 6) + "@email.com";

        String body = "{\n" +
                "  \"fullName\": \"Test User Test\",\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"password\": \"12345678Aa\",\n" +
                "  \"verified\": true,\n" +
                "  \"banned\": false\n" +
                "}";

        var response = given()
                .baseUri(AUTH_URL)
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/user")
                .then()
                .statusCode(201)
                .extract();

        testUserId = response.path("id");
    }

    @Test
    @Order(8)
    void testFindOneUser() {
        given()
                .baseUri(AUTH_URL)
                .auth().oauth2(accessToken)
                .when()
                .get("/user/" + testUserId)
                .then()
                .statusCode(200)
                .body("id", equalTo(testUserId));
    }

    @Test
    @Order(9)
    void testEditUser() {
        String patchJson = "{" +
                "\"fullName\":\"Updated User\"" +
                "}";

        given()
                .baseUri(AUTH_URL)
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(patchJson)
                .when()
                .patch("/user/" + testUserId)
                .then()
                .statusCode(200)
                .body("fullName", equalTo("Updated User"));
    }

    @Test
    @Order(10)
    void testFindAllUsers() {
        given()
                .baseUri(AUTH_URL)
                .auth().oauth2(accessToken)
                .when()
                .get("/user")
                .then()
                .statusCode(200)
                .body("users", not(empty()));
    }

    @Test
    @Order(11)
    void testDeleteUser() {
        given()
                .baseUri(AUTH_URL)
                .auth().oauth2(accessToken)
                .when()
                .delete("/user/" + testUserId)
                .then()
                .statusCode(200);
    }
}
