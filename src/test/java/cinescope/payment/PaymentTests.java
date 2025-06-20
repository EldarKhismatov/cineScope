package cinescope.payment;

import cinescope.config.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentTests extends BaseTest {

    private final String staticEmail = "test1123123213@email.com";
    private final String staticPassword = "12345678Aa";
    private static final int MOVIE_ID = 1265;
    private String userId;

    @BeforeEach
    void loginBeforeEach() {
        String loginJson = "{" +
                "\"email\":\"" + staticEmail + "\"," +
                "\"password\":\"" + staticPassword + "\"" +
                "}";

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
        userId = response.path("user.id");
    }

    private Map<String, Object> validCard = Map.of(
            "cardNumber", "4242424242424242",
            "cardHolder", "Ivan Ivanov",
            "expirationDate", "12/25",
            "securityCode", 123
    );

    @Test
    @Order(1)
    void testCreatePayment() {
        given()
                .baseUri(PAYMENT_URL)
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "movieId", MOVIE_ID,
                        "amount", 1,
                        "card", validCard
                ))
                .when()
                .post("/create")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(2)
    void testCreatePaymentWithInvalidCard() {
        String json = "{"
                + "\"movieId\":" + MOVIE_ID + ","
                + "\"amount\":1,"
                + "\"card\":{"
                + "\"cardNumber\":\"0000000000000000\","
                + "\"cardHolder\":\"\","
                + "\"expirationDate\":\"00/00\","
                + "\"securityCode\":123"
                + "}"
                + "}";

        given()
                .baseUri(PAYMENT_URL)
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/create")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(3)
    void testCreatePaymentWithoutAuth() {
        String json = "{"
                + "\"movieId\":" + MOVIE_ID + ","
                + "\"amount\":1,"
                + "\"card\":{"
                + "\"cardNumber\":\"1234567890123456\","
                + "\"cardHolder\":\"John Doe\","
                + "\"expirationDate\":\"12/26\","
                + "\"securityCode\":123"
                + "}"
                + "}";

        given()
                .baseUri(PAYMENT_URL)
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/create")
                .then()
                .statusCode(401);
    }

    @Test
    @Order(4)
    void testGetUserPayments() {
        given()
                .baseUri(PAYMENT_URL)
                .auth().oauth2(accessToken)
                .when()
                .get("/user")
                .then()
                .statusCode(200)
                .body("", notNullValue());
    }

    @Test
    @Order(5)
    void testGetPaymentsByUserId() {
        String userId = "8ac6b70c-6045-4262-8e26-9822eaf011ab"; // assumes API accepts "me" for current user

        given()
                .baseUri(PAYMENT_URL)
                .auth().oauth2(accessToken)
                .when()
                .get("/user/" + userId)
                .then()
                .statusCode(200); // access depends on roles
    }

    @Test
    @Order(6)
    void testGetAllPaymentsWithAuth() {
        given()
                .baseUri(PAYMENT_URL)
                .auth().oauth2(accessToken)
                .queryParam("page", 1)
                .queryParam("pageSize", 10)
                .when()
                .get("/find-all")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(7)
    void testGetAllPaymentsWithoutAuth() {
        given()
                .baseUri(PAYMENT_URL)
                .queryParam("page", 1)
                .queryParam("pageSize", 10)
                .when()
                .get("/find-all")
                .then()
                .statusCode(401);
    }
}