package smartyflip.accounting.service;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import smartyflip.accounting.dao.EmailActivationRepository;
import smartyflip.accounting.dao.RoleRepository;
import smartyflip.accounting.dao.UserRepository;
import smartyflip.accounting.dto.*;
import smartyflip.accounting.dto.exceptions.EmailActivationIdNotFoundException;
import smartyflip.accounting.dto.exceptions.RoleNotFoundException;
import smartyflip.accounting.dto.exceptions.UserAlreadyExistsException;
import smartyflip.accounting.dto.exceptions.UserNotFoundException;
import smartyflip.accounting.model.EmailActivationKey;
import smartyflip.accounting.model.Role;
import smartyflip.accounting.model.UserAccount;
import smartyflip.security.UserDetailsServiceImpl;
import smartyflip.utils.PagedDataResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService, CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final EmailActivationRepository emailActivationRepository;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Transactional
    @Override
    public UserResponseDto registerUser(UserRegistrationRequestDto data) {

        //check if user with such email or username already exists
        if (userRepository.existsByUsername(data.getUsername()) || userRepository.existsByEmail(data.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        UserAccount userAccount = modelMapper.map(data, UserAccount.class);
        userAccount.addRole(roleRepository.findByName("USER").get());
        String pass = passwordEncoder.encode(userAccount.getPassword());
        userAccount.setPassword(pass);
        UserAccount savedUser = userRepository.save(userAccount);
        // sending email activation key, it will be saved to Database
        emailService.sendActivationEmail(savedUser.getId());
        return modelMapper.map(userAccount, UserResponseDto.class);
    }

    @Override
    public UserResponseDto loginUser(UserLoginRequestDto data) {
        String username = data.getUsername();
        Optional<UserAccount> userAccount = userRepository.findByUsername(username);

        if (userAccount.isEmpty()) {
            userAccount = userRepository.findByEmail(username); // trying to find by Email
        }

        if (userAccount.isPresent()) {
            try {
                UserAccount user = userAccount.get();
                Boolean isPasswordsMatch = BCrypt.checkpw(data.getPassword(), user.getPassword());

                if (isPasswordsMatch) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),
                            passwordEncoder.encode(user.getPassword())
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return modelMapper.map(user, UserResponseDto.class);

                } else {
                    throw new BadCredentialsException("Wrong password");
                }

            } catch (BadCredentialsException e) {
                throw new RuntimeException();
            }
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<Cookie> logoutUser() {
        List<Cookie> cookies = new ArrayList<>();
        cookies.add(resetCookie("access-token"));
        cookies.add(resetCookie("refresh-token"));
        return cookies;
    }


    @Override
    public UserResponseDto findUserByUsername(String username) {
        UserAccount userAccount = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return modelMapper.map(userAccount, UserResponseDto.class);
    }

    @Override
    public UserResponseDto findUserById(Integer userId) {
        UserAccount userAccount = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return modelMapper.map(userAccount, UserResponseDto.class);
    }

    @Transactional
    @Override
    public UserResponseDto deleteUserById(Integer userId) {
        UserAccount userAccount = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(userId);
        return modelMapper.map(userAccount, UserResponseDto.class);
    }

    @Override
    public PagedDataResponseDto<UserResponseDto> findAll(PageRequest pageRequest) {
        Page<UserAccount> userAccounts = userRepository.findAll(pageRequest);
        PagedDataResponseDto<UserResponseDto> pagedDataResponseDto = new PagedDataResponseDto<>();
        Page<UserResponseDto> pageMapped = userAccounts.map(u -> modelMapper.map(u, UserResponseDto.class));
        pagedDataResponseDto.setData(pageMapped.getContent());
        pagedDataResponseDto.setTotalElements(pageMapped.getTotalElements());
        pagedDataResponseDto.setTotalPages(pageMapped.getTotalPages());
        pagedDataResponseDto.setCurrentPage(pageMapped.getNumber());
        return pagedDataResponseDto;
    }

    @Transactional
    @Override
    public UserResponseDto updateUser(Integer userId, UserEditRequestDto data) {
        UserAccount userAccount = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (data.getFirstName() != null && !data.getFirstName().equals(userAccount.getFirstName())) {
            userAccount.setFirstName(data.getFirstName());
        }

        if (data.getLastName() != null && !data.getLastName().equals(userAccount.getLastName())) {
            userAccount.setLastName(data.getLastName());
        }
        userRepository.save(userAccount);
        return modelMapper.map(userAccount, UserResponseDto.class);
    }

    @Transactional
    @Override
    public UserResponseDto activateUserEmail(String key) {
        EmailActivationKey keyObj = emailActivationRepository.findById(UUID.fromString(key)).orElseThrow(EmailActivationIdNotFoundException::new);
        UserAccount userAccount = userRepository.findById(keyObj.getUserId()).orElseThrow(UserNotFoundException::new);
        userAccount.setEmailActivated(true);
        userRepository.save(userAccount);
        emailActivationRepository.delete(keyObj);
        return modelMapper.map(userAccount, UserResponseDto.class);
    }

    @Override
    public ChangeRoleResponseDto changeRoleList(Integer userId, String roleName, Boolean isAddRole) {
        UserAccount userAccount = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Role role = roleRepository.findByName(roleName.toUpperCase()).orElseThrow(RoleNotFoundException::new);
        boolean res;
        if (isAddRole) {
            res = userAccount.addRole(role);
        } else {
            res = userAccount.removeRole(role);
        }
        if (res) {
            userRepository.save(userAccount);
        }
        return modelMapper.map(userAccount, ChangeRoleResponseDto.class);
    }

    @Override
    public void changePassword(Integer userId, String password) {
        UserAccount userAccount = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userAccount.setPassword(passwordEncoder.encode(password));
        userRepository.save(userAccount);
    }

    @Override
    public void uploadAvatar(Integer userId, MultipartFile file) {
        String fileName = file.getOriginalFilename();
//        try {
//            file.transferTo(new File("D:\\Folder\\" + fileName));
//            String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("/download/")
//                    .path(fileName)
//                    .toUriString();
//            ResponseClass response = new ResponseClass(fileName,
//                    downloadUrl,
//                    file.getContentType(),
//                    file.getSize());
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
    }

    @Override
    public void removeAvatar(Integer userId) {

    }

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.findByName("ADMINISTRATOR").isEmpty()) {
            roleRepository.save(new Role(1, "ADMINISTRATOR"));
        }

        if (roleRepository.findByName("USER").isEmpty()) {
            roleRepository.save(new Role(2, "USER"));
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            UserAccount userAccount = new UserAccount();
            userAccount.setUsername("admin");
            userAccount.setPassword(passwordEncoder.encode("Admin1234!"));
            userAccount.setEmail("admin@gmail.com");
            userAccount.setEmailActivated(true);
            userAccount.setFirstName("Admin");
            userAccount.setLastName("Adminov");
            userAccount.addRole(roleRepository.findByName("USER").get());
            userAccount.addRole(roleRepository.findByName("ADMINISTRATOR").get());
            userRepository.save(userAccount);
        }

        if (userRepository.findByUsername("user").isEmpty()) {
            UserAccount userAccount = new UserAccount();
            userAccount.setUsername("user");
            userAccount.setPassword(passwordEncoder.encode("User1234!"));
            userAccount.setEmail("user@gmail.com");
            userAccount.setEmailActivated(true);
            userAccount.setFirstName("User");
            userAccount.setLastName("Userov");
            userAccount.addRole(roleRepository.findByName("USER").get());
            userRepository.save(userAccount);
        }
    }

    private Cookie resetCookie(String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        return cookie;
    }
}
