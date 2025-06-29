package API.utils;


import io.restassured.http.ContentType;

import java.util.UUID;

import static io.restassured.RestAssured.*;

public class TestDataFactory {

    private static final String AUTH_URL = "https://auth.dev-cinescope.krisqa.ru";
    private static final String MOVIES_URL = "https://api.dev-cinescope.krisqa.ru";

    public static class UserData {
        public final String email;
        public final String password;
        public final String accessToken;

        public UserData(String email, String password, String accessToken) {
            this.email = email;
            this.password = password;
            this.accessToken = accessToken;
        }
    }

    public static UserData createTestUser() {
        String email = "test" + UUID.randomUUID().toString().replace("-", "") + "@mail.com";
        String password = "Test123@"; // must match backend regex

        String registerJson = "{"
                + "\"email\":\"" + email + "\","
                + "\"fullName\":\"Test User\","
                + "\"password\":\"" + password + "\","
                + "\"passwordRepeat\":\"" + password + "\""
                + "}";

        // Register user
        given()
                .baseUri(AUTH_URL)
                .contentType(ContentType.JSON)
                .body(registerJson)
                .when()
                .post("/register")
                .then()
                .statusCode(201);

        // Login user
        String loginJson = "{"
                + "\"email\":\"" + email + "\","
                + "\"password\":\"" + password + "\""
                + "}";

        String token = given()
                .baseUri(AUTH_URL)
                .contentType(ContentType.JSON)
                .body(loginJson)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");

        return new UserData(email, password, token);
    }


    public static int createGenre(String token) {
        String genreName = "Genre-" + UUID.randomUUID().toString().substring(0, 8);

        String genreJson = "{"
                + "\"name\":\"" + genreName + "\""
                + "}";

        return given()
                .baseUri(MOVIES_URL)
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(genreJson)
                .when()
                .post("/genres")
                .then()
                .statusCode(201)
                .extract()
                .path("id");
    }


    public static int createMovie(String token, int genreId) {
        String movieName = "Movie-" + UUID.randomUUID().toString().substring(0, 8);

        String movieJson = "{"
                + "\"name\":\"" + movieName + "\","
                + "\"price\":300,"
                + "\"description\":\"Описание\","
                + "\"imageUrl\":\"https://image.url\","
                + "\"location\":\"MSK\","
                + "\"published\":true,"
                + "\"genreId\":" + genreId
                + "}";

        return given()
                .baseUri(MOVIES_URL)
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(movieJson)
                .when()
                .post("/movies")
                .then()
                .statusCode(201)
                .extract()
                .path("id");
    }
}
