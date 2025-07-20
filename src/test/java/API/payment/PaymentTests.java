package API.payment;

import API.config.BaseTest;
import API.dto.LoginRequest;
import API.dto.LoginResponse;
import API.dto.PaymentRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Tag("api")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PaymentTests extends BaseTest {

    private static final String PAYMENT_PATH = "/create";
    private static final int MOVIE_ID = 1265;
    private String userId;

    @BeforeEach
    void loginAsUser() {
        LoginRequest login = LoginRequest.builder()
                .email("test1123123213@email.com")
                .password("12345678Aa")
                .build();

        LoginResponse response = given()
                .baseUri(AUTH_URL)
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post("/login")
                .then()
                .statusCode(201)
                .extract()
                .as(LoginResponse.class);

        accessToken = response.getAccessToken();
    }

    private Map<String, Object> validCard = Map.of(
            "cardNumber", "4242424242424242",
            "cardHolder", "Ivan Ivanov",
            "expirationDate", "12/25",
            "securityCode", 123
    );

    @Test
    @Order(1)
    void makeSuccessfulPayment() {
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
                .post(PAYMENT_PATH)
                .then()
                .statusCode(201);
    }

    @Test
    @Order(2)
    void makePaymentWithInvalidCard() {
        PaymentRequest request = PaymentRequest.builder()
                .amount(1)
                .cardNumber("1111222233334444")
                .cardHolder("FAKE USER")
                .expiry("01/20")
                .cvc("000")
                .build();

        given()
                .baseUri(PAYMENT_URL)
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(PAYMENT_PATH)
                .then()
                .statusCode(400);
    }
}