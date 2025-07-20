package API.auth;

import API.config.BaseTest;
import API.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.asynchttpclient.util.Assertions.assertNotNull;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("api")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthTests extends BaseTest {

    private final String staticEmail = "test1123123213@email.com";
    private final String staticPassword = "12345678Aa";

    private String refreshToken;
    private String testUserId;

    @BeforeAll
    public static void setupJackson() {
        RestAssured.config = RestAssured.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> {
                                    ObjectMapper mapper = new ObjectMapper();
                                    mapper.registerModule(new JavaTimeModule());
                                    return mapper;
                                }
                        ));
    }

    @BeforeEach
    void loginAsAdmin() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email(staticEmail)
                .password(staticPassword)
                .build();

        Response response = given()
                .baseUri(AUTH_URL)
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("/login")
                .then().statusCode(201)
                .extract().response();

        accessToken = response.jsonPath().getString("accessToken");
        refreshToken = response.getCookie("refresh_token");
    }

    @AfterAll
    void cleanUp() {
        if (testUserId != null) {
            loginAsAdmin();

            given()
                    .baseUri(AUTH_URL)
                    .auth().oauth2(accessToken)
                    .when().delete("/user/" + testUserEmail)
                    .then().statusCode(anyOf(is(200), is(204)));
        }
    }


    @Test
    @Order(1)
    void testRegisterWithMismatchedPasswords() {
        RegisterRequest request = RegisterRequest.builder()
                .email("mismatch@example.com")
                .fullName("Test User")
                .password("12345678Aa")
                .passwordRepeat("WrongPassword")
                .build();

        given()
                .baseUri(AUTH_URL)
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/register")
                .then().statusCode(400);
    }

    @Test
    @Order(2)
    void testRegisterWithInvalidEmail() {
        RegisterRequest body = RegisterRequest.builder()
                .email("invalid-email")
                .fullName("Фейк Фамилия")
                .password("12345678Aa")
                .passwordRepeat("12345678Aa")
                .build();

        given()
                .baseUri(AUTH_URL)
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/register")
                .then().statusCode(400);
    }

    @Test
    @Order(3)
    void testLoginWithValidCredentials() {
        LoginRequest body = LoginRequest.builder()
                .email(staticEmail)
                .password(staticPassword)
                .build();

        given()
                .baseUri(AUTH_URL)
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/login")
                .then().statusCode(201)
                .body("accessToken", notNullValue())
                .cookie("refresh_token", notNullValue());
    }

    @Test
    @Order(4)
    void testLogout() {
        given()
                .baseUri(AUTH_URL)
                .cookie("refresh_token", refreshToken)
                .when().get("/logout")
                .then().statusCode(200);
    }

    @Test
    @Order(5)
    void testRefreshTokens() {
        given()
                .baseUri(AUTH_URL)
                .cookie("refresh_token", refreshToken)
                .when().get("/refresh-tokens")
                .then().statusCode(201);
    }

    @Test
    @Order(6)
    void testCreateUserAsAdmin() {
        CreateUserRequest request = CreateUserRequest.builder()
                .fullName("Admin Created User")
                .email("created" + UUID.randomUUID() + "@test.com")
                .password("12345678Aa")
                .verified(true)
                .banned(false)
                .build();

        UserResponse response = given()
                .baseUri(AUTH_URL)
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/user")
                .then()
                .log().body()
                .statusCode(201)
                .extract().as(UserResponse.class);

        testUserEmail = response.getEmail();
        assertNotNull(testUserEmail, "User Email should be present in creation response");
    }

    @Test
    @Order(7)
    void testEditUserAsAdmin() {
        CreateUserRequest request = CreateUserRequest.builder()
                .fullName("User To Edit")
                .email("edit-" + UUID.randomUUID() + "@test.com")
                .password("12345678Aa")
                .verified(true)
                .banned(false)
                .build();

        // Создание пользователя
        given()
                .baseUri(AUTH_URL)
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/user")
                .then().statusCode(201);

        // Получение ID по email
        Response userResponse = given()
                .baseUri(AUTH_URL)
                .auth().oauth2(accessToken)
                .when().get("/user/" + request.getEmail())
                .then().statusCode(200)
                .extract().response();

        String userId = userResponse.jsonPath().getString("id");
        assertNotNull(userId, "User ID must be obtained from user info");

        // Создаем тело запроса как Map (убираем лишние поля)
        Map<String, Object> editRequest = new HashMap<>();
        editRequest.put("verified", false);
        editRequest.put("banned", true);
        editRequest.put("roles", Arrays.asList("USER", "ADMIN")); // Используем List вместо массива

        // Отправляем запрос на редактирование с логированием
        given()
                .baseUri(AUTH_URL)
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(editRequest)
                .when().patch("/user/" + userId)
                .then()
                .log().all()
                .statusCode(200);

        // Проверяем изменения
        Response updatedUser = given()
                .baseUri(AUTH_URL)
                .auth().oauth2(accessToken)
                .when().get("/user/" + userId)
                .then().statusCode(200)
                .extract().response();

        assertFalse(updatedUser.jsonPath().getBoolean("verified"));
        assertTrue(updatedUser.jsonPath().getBoolean("banned"));
        assertTrue(updatedUser.jsonPath().getList("roles", String.class).contains("ADMIN"));

        // Удаляем пользователя
        given()
                .baseUri(AUTH_URL)
                .auth().oauth2(accessToken)
                .when().delete("/user/" + userId)
                .then().statusCode(200);
    }

    @Test
    @Order(8)
    void testDeleteUserAsAdmin() {
        // Создаем пользователя
        CreateUserRequest request = CreateUserRequest.builder()
                .fullName("User To Delete")
                .email("delete-" + UUID.randomUUID() + "@test.com")
                .password("12345678Aa")
                .verified(true)
                .banned(false)
                .build();

        // Отправляем запрос на создание
        given()
                .baseUri(AUTH_URL)
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/user")
                .then().statusCode(201);

        // Получаем пользователя по email, чтобы извлечь ID
        Response userResponse = given()
                .baseUri(AUTH_URL)
                .auth().oauth2(accessToken)
                .when().get("/user/" + request.getEmail())
                .then().statusCode(200)
                .extract().response();

        String userId = userResponse.jsonPath().getString("id");
        assertNotNull(userId, "User ID must be retrieved from user info");

        // Удаляем пользователя
        given()
                .baseUri(AUTH_URL)
                .auth().oauth2(accessToken)
                .when().delete("/user/" + userId)
                .then().statusCode(200);

        // Проверяем удаление
        given()
                .baseUri(AUTH_URL)
                .auth().oauth2(accessToken)
                .when().get("/user/" + userId)
                .then().statusCode(200);
    }
}


