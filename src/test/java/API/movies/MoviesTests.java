package API.movies;

import API.config.BaseTest;
import API.dto.MovieListResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Tag("api")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoviesTests extends BaseTest {

    private static final String MOVIES_PATH = "/movies";
    private static final int COMEDY_GENRE_ID = 1;

    @Test
    @Order(1)
    void getAllMovies() {
        given()
                .baseUri(MOVIES_URL)
                .when()
                .get(MOVIES_PATH)
                .then()
                .statusCode(200)
                .body("movies", not(empty()));
    }

    @Test
    @Order(2)
    void filterMoviesByGenre() {
        given()
                .baseUri(MOVIES_URL)
                .queryParam("genreId", COMEDY_GENRE_ID)
                .when()
                .get(MOVIES_PATH)
                .then()
                .statusCode(200)
                .body("movies.genreId", everyItem(equalTo(COMEDY_GENRE_ID)));
    }

    @Test
    @Order(3)
    void sortMoviesByCreatedAtDesc() {
        given()
                .baseUri(MOVIES_URL)
                .queryParam("createdAt", "desc")
                .when()
                .get(MOVIES_PATH)
                .then()
                .statusCode(200)
                .body("movies", not(empty()));
    }

    @Test
    @Order(4)
    void paginateMovies() {
        MovieListResponse response = given()
                .baseUri(MOVIES_URL)
                .queryParam("page", 1)
                .queryParam("pageSize", 5)
                .when()
                .get(MOVIES_PATH)
                .then()
                .statusCode(200)
                .extract().as(MovieListResponse.class);

        Assertions.assertEquals(5, response.getMovies().size());
        Assertions.assertEquals(1, response.getPage());
        Assertions.assertEquals(5, response.getPageSize());
        Assertions.assertTrue(response.getPageCount() > 0);
    }
}