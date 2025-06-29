package API.movies;

import API.config.BaseTest;
import API.utils.TestDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Tag("api")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MoviesTests extends BaseTest {

    private final String staticEmail = "test1123123213@email.com";
    private final String staticPassword = "12345678Aa";
    private static final int MOVIE_ID = 1265;
    private static final int GENRE_ID = 5;
    private int createdGenreId;

    @BeforeEach
    void reLoginBeforeEachTest() {
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
    }

    @Test
    @Order(1)
    void testGetAllMovies() {
        given()
                .baseUri(MOVIES_URL)
                .when()
                .get("/movies")
                .then()
                .statusCode(200)
                .body("movies", not(empty()));
    }

    @Test
    @Order(2)
    void testGetMovieById() {
        given()
                .baseUri(MOVIES_URL)
                .when()
                .get("/movies/" + MOVIE_ID)
                .then()
                .statusCode(200)
                .body("id", equalTo(MOVIE_ID));
    }

    @Test
    @Order(3)
    void testCreateMovie() {
        String name = "newMuvie" + UUID.randomUUID().toString().substring(0, 6);

        String body = "{" +
                "\"name\":\"" + name + "\"," +
                "\"description\":\"Some description\"," +
                "\"price\":500," +
                "\"location\":\"MSK\"," +
                "\"imageUrl\":\"http://test.com/img.png\"," +
                "\"published\":true," +
                "\"genreId\":" + GENRE_ID +
                "}";

        given()
                .baseUri(MOVIES_URL)
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/movies")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(4)
    void testEditMovie() {
        String updateJson = "{\n" +
                "   \"name\": \"ДюнаАа\",\n" +
                "  \"description\": \"Наследник знаменитого дома Атрейдесов Пол отправляется вместе с семьей на одну из самых опасных планет во Вселенной — Арракис. Здесь нет ничего, кроме песка, палящего солнца, гигантских чудовищ и основной причины межгалактических конфликтов — невероятно ценного ресурса, который называется меланж. В результате захвата власти Пол вынужден бежать и скрываться, и это становится началом его эпического путешествия. Враждебный мир Арракиса приготовил для него множество тяжелых испытаний, но только тот, кто готов взглянуть в глаза своему страху, достоин стать избранным.\",\n" +
                "  \"price\": 1000,\n" +
                "  \"location\": \"SPB\",\n" +
                "  \"imageUrl\": \"https://yandex-images.clstorage.net/b472OMN35/050773NDOd3/bYxEwXEvQ6hlkrvwEE-xSjpevDr-swSozoLoIpnCvJiipL6HF_x-xIyPjbs2kPgLHOBClj_7x2TFxioujYoyJzhdUBAmvoOpIIB5YxbMtU9pWez_rjVFqRjB6DsCS2HGbG_nDMpitBZZTEx6Jdd92Ed6hknh5Ue6QRItbJ0bJjhnwwjAsDjAYKRCP3IZBPNH6BzxOWNx2mlKVPH2SwYshKdh4wZG6Tka08cEPWEMetaKtBLALiVKew2TZChWnqw07wyBwDZ1yuptS3YxHg88zueQdfyks5iiSZz6vojHoctgLmDPQuo_WpQEDvgpQLoXiz_ehujiDrGFHKkpE9umuG9LiUT1uoUp6gqxdB7Kuw_iHfT6LTGSq8JecbLJRScAYO7ngMHjtxKRi8iw6wg4kYKz1o7m4kv6TVZgbxLeJ_ysi0WFtbXLqG1E9jxRDzwHo5zwc6E5H6pD0DU2AQCvj69qZ8sIpHfcEsXBPedGO9VMNlrELyfGsQdVbO6dHSV6pUTIA7Y4QyIlhTE8GIIwhavWfrske53oDx9wdkdB4saqJusEB6LzHhROCXCrhvXRDPxbCeAgijEIEKau39SkPKTMx8-2tQZvasGz_NWNss7ol38yaLIa6wVX-fZHQCUEZ-2jRM0ot9DTy0i94cV40Az824qsKMN6Bx4uZ5eU5n9ji0QHM3JALaKHd3Sdw3xOqtNwPO9536CMkzJ4zUelR22lJQLPJLRa348Et6iCt9HIddePaSdKOI9cKWcWE6o3JwcORrByg6ujjzYxkUz0BuIWc_Ro9JFiApz1eI4Lo0zhLakMyWr9UNLOxfqoBzWewrNWhGYohTNNmaztF9Yr_qnKj4t3tkVgbYi8tJEN9oJoELl87L9RbAaZsPgGTueFa-eoRQFpsh5aSwE54ce_U0QxXgdqb4Y4RpRo6xuepHbuig1G8PFBqSSI_TvSx3VGpBG7Na272i9HVfV8DQ\",\n" +
                "  \"published\": true,\n" +
                "  \"genreId\": 5 \n" +
                "}";

        given()
                .baseUri(MOVIES_URL)
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(updateJson)
                .when()
                .patch("/movies/" + MOVIE_ID)
                .then()
                .statusCode(200);
    }

    @Test
    @Order(5)
    void testGetGenres() {
        given()
                .baseUri(MOVIES_URL)
                .when()
                .get("/genres")
                .then()
                .statusCode(200)
                .body("", not(empty()));
    }

    @Test
    @Order(6)
    void testCreateGenre() {
        String nameGenre = "Жанр-" + UUID.randomUUID().toString().substring(0, 6);

        createdGenreId = given()
                .baseUri(MOVIES_URL)
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body("{\"name\":\"" + nameGenre + "\"}")
                .when()
                .post("/genres")
                .then()
                .statusCode(201)
                .extract()
                .path("id");
    }

    @Test
    @Order(7)
    void testDeleteMovie() {
        int tempMovieId = TestDataFactory.createMovie(accessToken, GENRE_ID);

        given()
                .baseUri(MOVIES_URL)
                .auth().oauth2(accessToken)
                .when()
                .delete("/movies/" + tempMovieId)
                .then()
                .statusCode(200);
    }

    @Test
    @Order(8)
    void testCreateMovieReview() {
        given()
                .baseUri(MOVIES_URL)
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body("{\"rating\":5,\"text\":\"Потрясающе!\"}")
                .when()
                .post("/movies/" + MOVIE_ID + "/reviews")
                .then()
                .statusCode(anyOf(is(201), is(409)));
    }

    @Test
    @Order(9)
    void testGetMovieReviewById() {
        given()
                .baseUri(MOVIES_URL)
                .auth().oauth2(accessToken)
                .when()
                .get("/movies/" + MOVIE_ID + "/reviews")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(10)
    void testEditMovieReview() {
        given()
                .baseUri(MOVIES_URL)
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body("{\"rating\":4,\"text\":\"Обновил отзыв.\"}")
                .when()
                .put("/movies/" + MOVIE_ID + "/reviews")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(11)
    void testPatchHideReview() {
        given()
                .baseUri(MOVIES_URL)
                .auth().oauth2(accessToken)
                .when()
                .patch("/movies/" + MOVIE_ID + "/reviews/hide")
                .then()
                .statusCode(anyOf(is(200), is(404)));
    }

    @Test
    @Order(12)
    void testPatchShowReview() {
        given()
                .baseUri(MOVIES_URL)
                .auth().oauth2(accessToken)
                .when()
                .patch("/movies/" + MOVIE_ID + "/reviews/show")
                .then()
                .statusCode(anyOf(is(200), is(404)));
    }

    @Test
    @Order(13)
    void testGetGenreById() {
        given()
                .baseUri(MOVIES_URL)
                .when()
                .get("/genres/" + createdGenreId)
                .then()
                .statusCode(200)
                .body("id", equalTo(createdGenreId));
    }

    @Test
    @Order(14)
    void testDeleteGenre() {
        given()
                .baseUri(MOVIES_URL)
                .auth().oauth2(accessToken)
                .when()
                .delete("/genres/" + createdGenreId)
                .then()
                .statusCode(200);
    }

    @Test
    @Order(15)
    void testDeleteMovieReview() {
        String userId = "me";

        given()
                .baseUri(MOVIES_URL)
                .auth().oauth2(accessToken)
                .when()
                .delete("/movies/" + MOVIE_ID + "/reviews?userId=" + userId)
                .then()
                .statusCode(anyOf(is(200), is(404)));
    }
}
