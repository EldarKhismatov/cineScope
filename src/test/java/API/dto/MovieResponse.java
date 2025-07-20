package API.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResponse {
    private int id;
    private String name;
    private int genreId;
    private Genre genre;
    private String location;
    private int price;
    private String description;
    private String imageUrl;
    private boolean published;
    private double rating;
    private String createdAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Genre {
        private String name;
    }
}
