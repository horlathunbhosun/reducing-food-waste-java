package tech.olatunbosun.wastemanagement.usermanagement.services;

import jakarta.mail.MessagingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;
import tech.olatunbosun.wastemanagement.configs.JwtService;
import tech.olatunbosun.wastemanagement.usermanagement.mail.VerificationMail;
import tech.olatunbosun.wastemanagement.usermanagement.models.Partner;
import tech.olatunbosun.wastemanagement.usermanagement.models.Token;
import tech.olatunbosun.wastemanagement.usermanagement.models.User;
import tech.olatunbosun.wastemanagement.usermanagement.repository.PartnerRepository;
import tech.olatunbosun.wastemanagement.usermanagement.repository.TokenRepository;
import tech.olatunbosun.wastemanagement.usermanagement.repository.UserRepository;
import tech.olatunbosun.wastemanagement.usermanagement.request.ChangePasswordDTO;
import tech.olatunbosun.wastemanagement.usermanagement.request.CreateUserDTO;
import tech.olatunbosun.wastemanagement.usermanagement.request.LoginDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.GenericResponseDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.JwtTokenDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.UserResponseDTO;
import tech.olatunbosun.wastemanagement.usermanagement.utility.TokenGenerator;
import tech.olatunbosun.wastemanagement.usermanagement.utility.enums.TokenType;
import tech.olatunbosun.wastemanagement.usermanagement.utility.enums.UserStatus;
import tech.olatunbosun.wastemanagement.usermanagement.utility.enums.UserType;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;



//@NoArgsConstructor(force = true)
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


   private final UserRepository userRepository;
  private final  PartnerRepository partnerRepository;
  private final  ModelMapper modelMapper;
   private final VerificationMail verificationMail;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;


    @Transactional
    @Override
    public GenericResponseDTO saveUser(CreateUserDTO createUserDTO) {
        String verificationToken = TokenGenerator.generateToken(6);
        modelMapper.addConverter(userTypeConverter());
        User user = modelMapper.map(createUserDTO, User.class);
        GenericResponseDTO responseDTO = new GenericResponseDTO();

        AtomicBoolean emailExists = new AtomicBoolean(false);
        AtomicBoolean phoneNumberExists = new AtomicBoolean(false);
        userRepository.findByEmail(createUserDTO.getEmail()).ifPresent(user1 -> {
            emailExists.set(true);

        });
        userRepository.findByPhoneNumber(createUserDTO.getPhoneNumber()).ifPresent(user1 -> {
            phoneNumberExists.set(true);
        });

        if (emailExists.get()) {
            responseDTO.setMessage("Email already in use");
            responseDTO.setStatus("error");
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return responseDTO;
        }

        if (phoneNumberExists.get()) {
            responseDTO.setMessage("Phone number already in use");
            responseDTO.setStatus("error");
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return responseDTO;
        }
        user.setVerificationCode(verificationToken);
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        try {
            Partner partner = null;

            User savedUser = userRepository.save(user);
            if (createUserDTO.getUserType().equals("partner")) {
                 partner = Partner.builder()
                        .user(savedUser)
                        .brNumber(createUserDTO.getPartner().getBRNumber())
                        .address(createUserDTO.getPartner().getAddress())
                        .logo(createUserDTO.getPartner().getLogo())
                        .build();
                partner = partnerRepository.save(partner);

                if (partnerRepository.findById(partner.getId()).isEmpty()) {
                    responseDTO.setMessage("Failed to save Partner object to the database");
                    responseDTO.setStatus("error");
                    responseDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    return responseDTO;
                }
            }


            Map<String, Object> mailData = Map.of("verificationToken", verificationToken, "fullName", user.getFullName());
            try {
                verificationMail.sendVerificationEmail(user.getEmail(), mailData);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            var jwt = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

            saveUserToken(user, jwt);
            responseDTO.setMessage("User created successfully");
            responseDTO.setStatus("success");
            responseDTO.setData(UserResponseDTO.userResponseBuilder(user, partner));
            responseDTO.setToken(JwtTokenDTO.data(jwt, TokenType.BEARER.name(), (int) jwtExpiration, refreshToken));
            return responseDTO;
        } catch (DataIntegrityViolationException e) {
            return getGenericResponseDTO(e);
        } catch (DataAccessException e) {
            responseDTO.setMessage("Data access error: " + e.getMessage());
            responseDTO.setStatus("error");
            responseDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return responseDTO;
        } catch (Exception e) {
            responseDTO.setMessage("An error occurred: " + e.getMessage());
            responseDTO.setStatus("error");
            responseDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return responseDTO;
        }
    }
    private static GenericResponseDTO getGenericResponseDTO(DataIntegrityViolationException e) {
        System.out.println(e.getMessage());
        GenericResponseDTO responseDTO = new GenericResponseDTO();
        if (e.getMessage().contains("Email already in use")) {
            responseDTO.setMessage("Email already in use");
            responseDTO.setStatus("error");
        } else if (e.getMessage().contains("Phone number already in use")) {
            responseDTO.setMessage("Phone number already in use");
            responseDTO.setStatus("error");
        } else {
            throw e;
        }
        return responseDTO;
    }


    @Override
    public GenericResponseDTO verifyUser(String token) {
        GenericResponseDTO responseDTO = new GenericResponseDTO();
        if (userRepository.findByVerificationCode(token).isPresent()) {
            User user = userRepository.findByVerificationCode(token).get();
            if (user.isVerified()) {
                responseDTO.setMessage("User already verified");
                responseDTO.setStatus("error");
                responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
                return responseDTO;
            }
            user.setVerified(true);
            user.setStatus(UserStatus.ACTIVE);
            user.setVerificationCode(null);
            userRepository.save(user);
            responseDTO.setMessage("User verified successfully");
            responseDTO.setStatus("success");
            responseDTO.setStatusCode(HttpStatus.OK.value());
            //check if user is a partner
            Partner partner = checkIfUserIsPartner(user);
            if (partner != null) {
                responseDTO.setData(UserResponseDTO.userResponseBuilder(user, partner));
            } else {
                responseDTO.setData(UserResponseDTO.userResponseBuilder(user, null));
            }
            return responseDTO;
        }
        responseDTO.setMessage("Verification Token not found");
        responseDTO.setStatus("error");
        responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return responseDTO;
    }

    @Override
    public GenericResponseDTO resendVerificationToken(String email) {
        GenericResponseDTO responseDTO = new GenericResponseDTO();
        if(userRepository.findByEmail(email).isPresent()){
            User user = userRepository.findByEmail(email).get();
            if(user.isVerified()){
                responseDTO.setMessage("User already verified");
                responseDTO.setStatus("error");
                responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
                return responseDTO;
            }
            String verificationToken = TokenGenerator.generateToken(6);
            user.setVerificationCode(verificationToken);
            userRepository.save(user);
            Map<String, Object> mailData = Map.of("verificationToken", verificationToken, "fullName", user.getFullName());
            try {
                verificationMail.sendVerificationEmail(user.getEmail(), mailData);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            responseDTO.setMessage("Verification token sent successfully");
            responseDTO.setStatus("success");
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return responseDTO;
        }
        responseDTO.setMessage("User not found");
        responseDTO.setStatus("error");
        responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return responseDTO;
    }

    @Override
    public GenericResponseDTO forgetPassword(String email, String phoneNumber) {
        GenericResponseDTO responseDTO = new GenericResponseDTO();
        if (email != null){
            if(userRepository.findByEmail(email).isPresent()){
                User user = userRepository.findByEmail(email).get();
                String verificationToken = TokenGenerator.generateToken(6);
                user.setVerificationCode(verificationToken);
                userRepository.save(user);
                Map<String, Object> mailData = Map.of("verificationToken", verificationToken, "fullName", user.getFullName());
                try {
                    verificationMail.sendVerificationEmail(user.getEmail(), mailData);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                responseDTO.setMessage("Verification token sent successfully");
                responseDTO.setStatus("success");
                responseDTO.setStatusCode(HttpStatus.OK.value());
                return responseDTO;
            }
        }else{
            if(userRepository.findByPhoneNumber(phoneNumber).isPresent()){
                User user = userRepository.findByPhoneNumber(phoneNumber).get();
                String verificationToken = TokenGenerator.generateToken(6);
                user.setVerificationCode(verificationToken);
                userRepository.save(user);
                //todo send sms

            }
        }
        responseDTO.setMessage("Something went wrong, try again");
        responseDTO.setStatus("error");
        responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return responseDTO;
    }

    @Override
    public GenericResponseDTO login(LoginDTO loginDTO) {
        GenericResponseDTO response = new GenericResponseDTO();
        try {
            User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User with email " + loginDTO.getEmail() + " does not exist"));
            if (!user.isVerified()) {
                response.setStatus("error");
                response.setMessage("User not verified");
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                return response;
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
            if (!authentication.isAuthenticated()) {
                response.setStatus("error");
                response.setMessage("Invalid Credentials");
                return response;
            }
            var jwt = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserToken(user);

            saveUserToken(user, jwt);
            //check if user is a partner
            Partner partner = checkIfUserIsPartner(user);
            if (partner != null) {
                response.setData(UserResponseDTO.userResponseBuilder(user, partner));
            } else {
                response.setData(UserResponseDTO.userResponseBuilder(user, null));
            }
            response.setMessage("Login successful");
            response.setStatus("success");
            response.setStatusCode(HttpStatus.OK.value());
            response.setToken(JwtTokenDTO.data(jwt, TokenType.BEARER.name(), (int) jwtExpiration,refreshToken));
        } catch (AuthenticationException e){
            response.setStatus("error");
            response.setMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return response;
        }
        return response;
    }

    @Override
    public GenericResponseDTO changePassword(ChangePasswordDTO changePasswordDTO, Principal loggedInUser) {
        GenericResponseDTO response = new GenericResponseDTO();
        if (loggedInUser == null) {
            response.setStatus("error");
            response.setMessage("User is not authenticated");
            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            return response;
        }

        var user = (User) ((UsernamePasswordAuthenticationToken) loggedInUser).getPrincipal();


        if (!passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())) {
            response.setStatus("error");
            response.setMessage("Validation error occurred");
            response.setErrorMessage(Map.of("error", "Current password is incorrect"));
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return response;
        }
        // check if the two new passwords are the same
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmationPassword())) {
            response.setStatus("error");
            response.setMessage("Validation error occurred");
            response.setErrorMessage(Map.of("error", "New password and confirmation password do not match"));
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return response;
        }
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
        response.setStatus("success");
        response.setMessage("Password changed successfully");
        response.setStatusCode(HttpStatus.OK.value());
        return response;

    }

    @Override
    public void saveUserToken(User user, String tokenJwt) {
        var token = Token.builder()
                .user(user)
                .token(tokenJwt)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public void revokeAllUserToken(User user) {
        var validUserToken = tokenRepository.findAllValidTokenByUser(user.getId());
        if (!validUserToken.isEmpty()) {
            validUserToken.forEach(token -> {
                token.setRevoked(true);
                token.setExpired(true);
                tokenRepository.save(token);
            });
        }
        tokenRepository.saveAll(validUserToken);
    }

    @Override
    public GenericResponseDTO refreshToken(HttpServletRequest request, HttpServletResponse response) {
        GenericResponseDTO responseDTO = new GenericResponseDTO();
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            responseDTO.setStatus("error");
            responseDTO.setMessage("Bearer token not found");
            responseDTO.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            return responseDTO;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            responseDTO.setStatus("error");
            responseDTO.setMessage("User with token not found");
            responseDTO.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            return responseDTO;
        }

        var user = this.userRepository.findByEmail(userEmail)
                .orElseThrow();
        if (!jwtService.isTokenValid(refreshToken, user)) {

            responseDTO.setStatus("error");
            responseDTO.setMessage("Invalid token provided");
            responseDTO.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            return responseDTO;
        }
        var accessToken = jwtService.generateToken(user);
        revokeAllUserToken(user);
        saveUserToken(user, accessToken);

        responseDTO.setStatus("success");
        responseDTO.setMessage("Token refreshed successfully");
        responseDTO.setStatusCode(HttpStatus.OK.value());
        responseDTO.setToken(JwtTokenDTO.data(accessToken, TokenType.BEARER.name(), (int) jwtExpiration,refreshToken));
        return responseDTO;
    }

    private Converter<String, UserType> userTypeConverter(){
        return new Converter<String, UserType>() {
            @Override
            public UserType convert(MappingContext<String, UserType> context) {
                return UserType.valueOf(context.getSource().toUpperCase());
            }
        };

    }


    private Partner checkIfUserIsPartner(User user){
        return partnerRepository.findByUserId(user.getId()).orElse(null);
    }

}

