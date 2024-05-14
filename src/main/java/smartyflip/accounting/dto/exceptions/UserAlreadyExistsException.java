package smartyflip.accounting.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "A user with this login or email already exists")
public class UserAlreadyExistsException extends RuntimeException {
    //TODO make separate exceptions on login and email
}