package smartyflip.accounting.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserLoginRequestDto {
    @Valid
    @NotBlank(message = "Username is mandatory")
    String username;
    @NotBlank(message = "Password is mandatory")
    String password;

    public String getUsername() {
        return username.toLowerCase();
    }
}
