package UI.utils;

import UI.dto.FilmDto;

import java.util.UUID;

public class FilmDataFactory {

    private static String urlImageMovie = "https://i.pinimg.com/originals/b2/dc/9c/b2dc9c2cee44e45672ad6e3994563ac2.jpg";

    public static FilmDto.FilmDtoBuilder validFilmBuilder() {
        return FilmDto.builder()
                .title("Фильм " + System.currentTimeMillis())
                .description("Описание для автотеста")
                .price(250)
                .location("SPB")
                .imageUrl(urlImageMovie)
                .genre("Военный")
                .published(true);
    }

    public static FilmDto validFilm() {
        return validFilmBuilder().build();
    }

    public static FilmDto withEmptyFields() {
        return FilmDto.builder()
                .title("")
                .price(0)
                .description("")
                .imageUrl("")
                .genre("")
                .build();
    }
}
