package tech.olatunbosun.wastemanagement.usermanagement.services;

import org.springframework.security.core.userdetails.UserDetails;
import tech.olatunbosun.wastemanagement.usermanagement.models.User;
import tech.olatunbosun.wastemanagement.usermanagement.request.ChangePasswordDTO;
import tech.olatunbosun.wastemanagement.usermanagement.request.CreateUserDTO;
import tech.olatunbosun.wastemanagement.usermanagement.request.LoginDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.GenericResponseDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.UserResponseDTO;

import java.security.Principal;

public interface UserService {

    GenericResponseDTO saveUser(CreateUserDTO createUserDTO);
    UserResponseDTO updateUser(User user);
    UserResponseDTO findUserById(Long id);
    UserResponseDTO findUserByEmail(String email);

    GenericResponseDTO verifyUser(String token);
    GenericResponseDTO resendVerificationToken(String email);

    GenericResponseDTO forgetPassword(String email, String phoneNumber);
    GenericResponseDTO login(LoginDTO loginDTO);
    GenericResponseDTO changePassword(ChangePasswordDTO changePasswordDTO, Principal principal);
//    UserDetails loadUserByUsername(String username);

}
