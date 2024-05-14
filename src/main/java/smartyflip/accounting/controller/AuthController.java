package smartyflip.accounting.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import smartyflip.accounting.dto.UserLoginRequestDto;
import smartyflip.accounting.dto.UserRegistrationRequestDto;
import smartyflip.accounting.dto.UserResponseDto;
import smartyflip.accounting.dto.exceptions.UserAlreadyExistsException;
import smartyflip.accounting.service.AuthService;
import smartyflip.accounting.service.UserAccountService;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserAccountService userAccountService;
    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRegistrationRequestDto data, HttpServletResponse response) {
        try {
            UserResponseDto userResponseDto = userAccountService.registerUser(data);
            response.addCookie(authService.createAccessTokenCookie(userResponseDto.getUsername()));
            response.addCookie(authService.createRefreshToken(userResponseDto.getUsername()));
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser(@Valid @RequestBody UserLoginRequestDto data, HttpServletResponse response) {
        try {
            UserResponseDto userResponseDto = userAccountService.loginUser(data);
            response.addCookie(authService.createAccessTokenCookie(userResponseDto.getUsername()));
            response.addCookie(authService.createRefreshToken(userResponseDto.getUsername()));
            return ResponseEntity.ok(userResponseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response) {
        userAccountService.logoutUser().forEach(response::addCookie);
    }

    @GetMapping("/activation")
    public UserResponseDto activateUserEmail(@NotBlank @RequestParam String key) {
        return userAccountService.activateUserEmail(key);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
