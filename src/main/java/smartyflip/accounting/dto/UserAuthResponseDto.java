package smartyflip.accounting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthResponseDto {
    UserResponseDto user;
    @JsonProperty("access_token")
    String accessToken;
}
