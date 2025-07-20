package API.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
    private String email;
    private String fullName;
    private String[] roles;
    private boolean verified;
    private boolean banned;
    private LocalDateTime createdAt;
}

