package API.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditUserRequest {
    private String[] roles;
    private boolean verified;
    private boolean banned;
    private String fullName;
}
