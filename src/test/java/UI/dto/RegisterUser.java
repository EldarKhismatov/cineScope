package UI.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUser {
    private String fullName;
    private String email;
    private String password;
    private String passwordRepeat;
}
