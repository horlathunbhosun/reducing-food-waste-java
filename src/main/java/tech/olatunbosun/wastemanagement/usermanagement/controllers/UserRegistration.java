package tech.olatunbosun.wastemanagement.usermanagement.controllers;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tech.olatunbosun.wastemanagement.usermanagement.request.CreateUserDTO;
import tech.olatunbosun.wastemanagement.usermanagement.request.ForgetPasswordRequestDTO;
import tech.olatunbosun.wastemanagement.usermanagement.request.ResendVerificationRequestDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.GenericResponseDTO;
import tech.olatunbosun.wastemanagement.usermanagement.services.UserService;
import tech.olatunbosun.wastemanagement.validation.ValidationErrorService;

import java.util.Map;

@RestController
@AllArgsConstructor
//@RequestMapping("v1/")
public class UserRegistration {

    UserService userService;
    ValidationErrorService validationErrorService;

    @PostMapping("/v1/user/register")
    public ResponseEntity<GenericResponseDTO> registerUser(@Valid @RequestBody CreateUserDTO createUserDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            validationError(bindingResult);
        }
        GenericResponseDTO response = userService.saveUser(createUserDTO);
        if (response.getStatus().equals("error")){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("user/verify/{token}")
    public ResponseEntity<GenericResponseDTO> verifyUser(@PathVariable String token){
        GenericResponseDTO response = userService.verifyUser(token);
        if (response.getStatus().equals("error")){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("user/resend-verification")
    public ResponseEntity<GenericResponseDTO> resendVerification(@Valid @RequestBody ResendVerificationRequestDTO body, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            validationError(bindingResult);
        }
        GenericResponseDTO response = userService.resendVerificationToken(body.getEmail());
        if (response.getStatus().equals("error")){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("user/forgot-password")
    public ResponseEntity<GenericResponseDTO> forgotPassword(@Valid @RequestBody ForgetPasswordRequestDTO forgetPasswordRequestDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            validationError(bindingResult);
        }
        GenericResponseDTO response = userService.forgetPassword(forgetPasswordRequestDTO.getEmail(), forgetPasswordRequestDTO.getPhoneNumber());
        if (response.getStatus().equals("error")){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    private ResponseEntity<?> validationError(BindingResult bindingResult){
        Map<String, String> errors = validationErrorService.validate(bindingResult);
        GenericResponseDTO errorResponse = new GenericResponseDTO();
        errorResponse.setStatus("error");
        errorResponse.setMessage("Validation error occurred");
        errorResponse.setErrorData(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
