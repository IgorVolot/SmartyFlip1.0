package smartyflip.accounting.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import smartyflip.accounting.dto.ChangeRoleResponseDto;
import smartyflip.accounting.dto.UserEditRequestDto;
import smartyflip.accounting.dto.UserResponseDto;
import smartyflip.accounting.service.UserAccountService;
import smartyflip.utils.PagedDataResponseDto;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserAccountService userAccountService;

    @GetMapping
    public PagedDataResponseDto<UserResponseDto> getAllUsers(
            @RequestParam(required = false, defaultValue = "25", name = "size") Integer size,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page
    ) {
        return userAccountService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/{userId}")
    public UserResponseDto findUserById(@PathVariable Integer userId) {
        return userAccountService.findUserById(userId);
    }

    @PutMapping("/{userId}")
    public UserResponseDto updateUser(@Valid @RequestBody UserEditRequestDto data, @PathVariable Integer userId) {
        return userAccountService.updateUser(userId, data);
    }

    @DeleteMapping("/{userId}")
    public UserResponseDto deleteUser(@PathVariable Integer userId) {
        return userAccountService.deleteUserById(userId);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Void> changePassword(@PathVariable Integer userId,
                                               @RequestHeader("X-Password")
                                               @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!$#%])[A-Za-z\\d!$#%]{8,}$", message = "")
                                               String password) {
        userAccountService.changePassword(userId, password);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/avatar")
    public ResponseEntity<Void> uploadAvatar(@RequestParam("file") MultipartFile file, @PathVariable Integer userId) {
        userAccountService.uploadAvatar(userId, file);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/avatar")
    public ResponseEntity<Void> removeAvatar(@PathVariable Integer userId) {
        userAccountService.removeAvatar(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/role/{role}")
    public ChangeRoleResponseDto addRole(@PathVariable String userId, @PathVariable String role) {
        try {
            int id = Integer.parseInt(userId);
            return userAccountService.changeRoleList(id, role, true);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect user ID format");
        }
    }

    @DeleteMapping("/{userId}/role/{role}")
    public ChangeRoleResponseDto removeRole(@PathVariable String userId, @PathVariable String role) {
        try {
            int id = Integer.parseInt(userId);
            return userAccountService.changeRoleList(id, role, false);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect user ID format");
        }
    }

}
