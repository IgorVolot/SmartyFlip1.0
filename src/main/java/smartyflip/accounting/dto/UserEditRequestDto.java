package smartyflip.accounting.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserEditRequestDto {
    @Valid
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
    String firstName;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")
    String lastName;
}
