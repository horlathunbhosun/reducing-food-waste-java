package tech.olatunbosun.wastemanagement.usermanagement.services;

import tech.olatunbosun.wastemanagement.usermanagement.models.User;
import tech.olatunbosun.wastemanagement.usermanagement.request.CreateUserDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.GenericResponseDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.UserResponseDTO;

public interface UserService {

    GenericResponseDTO saveUser(CreateUserDTO createUserDTO);
    UserResponseDTO updateUser(User user);
    UserResponseDTO findUserById(Long id);
    UserResponseDTO findUserByEmail(String email);

}
