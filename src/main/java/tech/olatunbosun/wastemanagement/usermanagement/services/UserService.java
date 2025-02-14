package tech.olatunbosun.wastemanagement.usermanagement.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import tech.olatunbosun.wastemanagement.usermanagement.models.User;
import tech.olatunbosun.wastemanagement.usermanagement.request.ChangePasswordDTO;
import tech.olatunbosun.wastemanagement.usermanagement.request.CreateUserDTO;
import tech.olatunbosun.wastemanagement.usermanagement.request.GoogleAuthRequest;
import tech.olatunbosun.wastemanagement.usermanagement.request.LoginDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.GenericResponseDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.UserResponseDTO;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Principal;

public interface UserService {


    GenericResponseDTO saveUser(CreateUserDTO createUserDTO);
    GenericResponseDTO verifyUser(String token);
    GenericResponseDTO resendVerificationToken(String email);

    GenericResponseDTO forgetPassword(String email, String phoneNumber);
    GenericResponseDTO login(LoginDTO loginDTO);

    GenericResponseDTO loginWithGoogle(GoogleAuthRequest googleAuthRequest) throws GeneralSecurityException, IOException;
    GenericResponseDTO changePassword(ChangePasswordDTO changePasswordDTO, Principal principal);

    void saveUserToken(User user, String token);
    void revokeAllUserToken(User user);
    GenericResponseDTO refreshToken( HttpServletRequest request,
                       HttpServletResponse response);


}
