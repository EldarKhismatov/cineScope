package UI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmDto {
    private String title;
    private String description;
    private int price;
    private String location;
    private String imageUrl;
    private String genre;
    private boolean published;
}

