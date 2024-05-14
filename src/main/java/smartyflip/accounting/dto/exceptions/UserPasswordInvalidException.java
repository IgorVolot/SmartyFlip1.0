package smartyflip.accounting.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Username or password is incorrect")
public class UserPasswordInvalidException extends RuntimeException {
}