package tech.olatunbosun.wastemanagement.usermanagement.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.olatunbosun.wastemanagement.usermanagement.models.User;
import tech.olatunbosun.wastemanagement.usermanagement.repository.UserRepository;


@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User findUserById(Long id) {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }
}
