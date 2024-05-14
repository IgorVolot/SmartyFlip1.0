package smartyflip.accounting.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserRegistrationRequestDto {

    @Valid
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_-]{2,11}$", message = "Username must be between 5 and 20 characters long and contain only letters, digits, hyphens, and underscores")
    String username;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!$#%])[A-Za-z\\d!$#%]{8,}$", message = "Incorrect password format. Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one of the following symbols: !, $, #, %.")
    String password;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
    @NotBlank(message = "First name is mandatory")
    String firstName;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")
    @NotBlank(message = "Last name is mandatory")
    String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email has invalid format")
    String email;

    public String getUsername() {
        return username.toLowerCase();
    }

    public String getEmail() {
        return email.toLowerCase();
    }
}
