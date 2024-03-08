package tech.olatunbosun.wastemanagement.usermanagement.services;

import jakarta.mail.MessagingException;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.olatunbosun.wastemanagement.configs.JwtService;
import tech.olatunbosun.wastemanagement.usermanagement.mail.VerificationMail;
import tech.olatunbosun.wastemanagement.usermanagement.models.Partner;
import tech.olatunbosun.wastemanagement.usermanagement.models.User;
import tech.olatunbosun.wastemanagement.usermanagement.repository.PartnerRepository;
import tech.olatunbosun.wastemanagement.usermanagement.repository.UserRepository;
import tech.olatunbosun.wastemanagement.usermanagement.request.ChangePasswordDTO;
import tech.olatunbosun.wastemanagement.usermanagement.request.CreateUserDTO;
import tech.olatunbosun.wastemanagement.usermanagement.request.LoginDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.GenericResponseDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.UserResponseDTO;
import tech.olatunbosun.wastemanagement.usermanagement.utility.TokenGenerator;
import tech.olatunbosun.wastemanagement.usermanagement.utility.enums.UserType;

import java.security.Principal;
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
            return responseDTO;
        }

        if (phoneNumberExists.get()) {
            responseDTO.setMessage("Phone number already in use");
            responseDTO.setStatus("error");
            return responseDTO;
        }
        user.setVerificationCode(verificationToken);
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));

        System.out.println(user.toString());
        try {
            User savedUser = userRepository.save(user);

            if (createUserDTO.getUserType().equals("partner")) {
                Partner partner = modelMapper.map(createUserDTO, Partner.class);
                partner.setUser(savedUser);
                partnerRepository.save(partner);
            }


            Map<String, Object> mailData = Map.of("verificationToken", verificationToken, "fullName", savedUser.getFullName());
            try {
                verificationMail.sendVerificationEmail(savedUser.getEmail(), mailData);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            var jwt = jwtService.generateToken(savedUser);
            responseDTO.setMessage("User created successfully");
            responseDTO.setStatus("success");
            responseDTO.setData(UserResponseDTO.userResponseBuilder(savedUser));
            responseDTO.setToken(jwt);
            return responseDTO;
        }catch (DataIntegrityViolationException e){
            return getGenericResponseDTO(e);
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
    public UserResponseDTO updateUser(User user) {

        return null;
    }

    @Override
    public UserResponseDTO findUserById(Long id) {
        return null;
    }

    @Override
    public UserResponseDTO findUserByEmail(String email) {
        return null;
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
            user.setVerificationCode(null);
            userRepository.save(user);
            responseDTO.setMessage("User verified successfully");
            responseDTO.setStatus("success");
            responseDTO.setData(UserResponseDTO.userResponseBuilder(user));
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
        return responseDTO;
    }

    @Override
    public GenericResponseDTO login(LoginDTO loginDTO) {
        GenericResponseDTO response = new GenericResponseDTO();
        try {
            User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User with email " + loginDTO.getEmail() + " not found"));
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

            response.setMessage("Login successful");
            response.setStatus("success");
            response.setData(UserResponseDTO.userResponseBuilder(user));
            response.setStatusCode(HttpStatus.OK.value());
            response.setToken(jwt);
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
        var user = (User) ((UsernamePasswordAuthenticationToken) loggedInUser).getPrincipal();
        GenericResponseDTO response = new GenericResponseDTO();
        if (!passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())) {
            response.setStatus("error");
            response.setMessage("Old password is incorrect");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return response;
        }
        // check if the two new passwords are the same
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmationPassword())) {
            response.setStatus("error");
            response.setMessage("new passwords do not match each other");
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

    private Converter<String, UserType> userTypeConverter(){
        return new Converter<String, UserType>() {
            @Override
            public UserType convert(MappingContext<String, UserType> context) {
                return UserType.valueOf(context.getSource().toUpperCase());
            }
        };

    }


}

