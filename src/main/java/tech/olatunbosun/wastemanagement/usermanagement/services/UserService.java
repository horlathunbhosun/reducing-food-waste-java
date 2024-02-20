package tech.olatunbosun.wastemanagement.usermanagement.services;

import tech.olatunbosun.wastemanagement.usermanagement.models.User;

public interface UserService {

    User saveUser(User user);
    User updateUser(User user);
    User findUserById(Long id);
    User findUserByEmail(String email);

}
