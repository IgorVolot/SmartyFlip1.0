package smartyflip.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smartyflip.accounting.dao.UserRepository;
import smartyflip.accounting.model.UserAccount;

@Service
@RequiredArgsConstructor
public class CustomSecurity {

    private final UserRepository userRepository;

    public boolean hasUserAccessToUserId(String userName, Integer userId) {
        UserAccount userAccount = userRepository.findByUsername(userName).orElse(null);
        return userAccount != null && userAccount.getId().equals(userId);
    }
}

