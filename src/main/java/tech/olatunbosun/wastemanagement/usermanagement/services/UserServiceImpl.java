package tech.olatunbosun.wastemanagement.usermanagement.services;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import tech.olatunbosun.wastemanagement.usermanagement.mail.VerificationMail;
import tech.olatunbosun.wastemanagement.usermanagement.models.Partner;
import tech.olatunbosun.wastemanagement.usermanagement.models.User;
import tech.olatunbosun.wastemanagement.usermanagement.repository.PartnerRepository;
import tech.olatunbosun.wastemanagement.usermanagement.repository.UserRepository;
import tech.olatunbosun.wastemanagement.usermanagement.request.CreateUserDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.GenericResponseDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.UserResponseDTO;
import tech.olatunbosun.wastemanagement.usermanagement.utility.TokenGenerator;
import tech.olatunbosun.wastemanagement.usermanagement.utility.enums.UserType;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PartnerRepository partnerRepository;


    private final ModelMapper modelMapper;
    private final VerificationMail verificationMail;

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
        System.out.println(user.toString());
        try {
            User savedUser = userRepository.save(user);

            if (createUserDTO.getUserType().equals("partner")) {
                Partner partner = modelMapper.map(createUserDTO, Partner.class);
                partner.setUserId(savedUser.getId());
                partnerRepository.save(partner);
            }


            Map<String, Object> mailData = Map.of("verificationToken", verificationToken, "fullName", savedUser.getFullName());
            try {
                verificationMail.sendVerificationEmail(savedUser.getEmail(), mailData);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            responseDTO.setMessage("User created successfully");
            responseDTO.setStatus("success");
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

    private Converter<String, UserType> userTypeConverter(){
        return new Converter<String, UserType>() {
            @Override
            public UserType convert(MappingContext<String, UserType> context) {
                return UserType.valueOf(context.getSource().toUpperCase());
            }
        };

    }

}

