package API.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieListResponse {
    private List<MovieResponse> movies;
    private int count;
    private int page;
    private int pageSize;
    private int pageCount;
}
