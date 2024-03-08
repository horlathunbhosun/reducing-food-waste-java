package tech.olatunbosun.wastemanagement.usermanagement.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import tech.olatunbosun.wastemanagement.usermanagement.request.ChangePasswordDTO;
import tech.olatunbosun.wastemanagement.usermanagement.request.CreateUserDTO;
import tech.olatunbosun.wastemanagement.usermanagement.request.ForgetPasswordRequestDTO;
import tech.olatunbosun.wastemanagement.usermanagement.request.ResendVerificationRequestDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.GenericResponseDTO;
import tech.olatunbosun.wastemanagement.usermanagement.services.UserServiceImpl;
import tech.olatunbosun.wastemanagement.validation.ValidationErrorService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "User Registration", description = "User Registration and Verification")
//@AllArgsConstructor
@RequestMapping("v1/")
public class UserRegistration {


    private final UserDetailsService userDetailsService;
    private final UserServiceImpl userService;
    private final ValidationErrorService validationErrorService;

    @Operation(
            description = "This endpoint is used to register a new user",
            summary =  "Register a new user",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = {
                                    @io.swagger.v3.oas.annotations.media.Content(
                                            mediaType = "application/json",
                                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = GenericResponseDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403",
                            content = {
                                    @io.swagger.v3.oas.annotations.media.Content(
                                            mediaType = "application/json",
                                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = GenericResponseDTO.class)
                                    )
                            }

                    )
            }

    )

    @PostMapping("/user/register")
    public ResponseEntity<GenericResponseDTO> registerUser(@Valid @RequestBody CreateUserDTO createUserDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return validate(bindingResult);
        }
        GenericResponseDTO response = userService.saveUser(createUserDTO);
        if (response.getStatus().equals("error")){
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("user/verify/{token}")
    public ResponseEntity<GenericResponseDTO> verifyUser(@PathVariable String token){
        GenericResponseDTO response = userService.verifyUser(token);
        if (response.getStatus().equals("error")){
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("user/resend-verification")
    public ResponseEntity<GenericResponseDTO> resendVerification(@Valid @RequestBody ResendVerificationRequestDTO body, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return validate(bindingResult);
        }
        GenericResponseDTO response = userService.resendVerificationToken(body.getEmail());
        if (response.getStatus().equals("error")){
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("user/forgot-password")
    public ResponseEntity<GenericResponseDTO> forgotPassword(@Valid @RequestBody ForgetPasswordRequestDTO forgetPasswordRequestDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
           return validate(bindingResult);
        }
        GenericResponseDTO response = userService.forgetPassword(forgetPasswordRequestDTO.getEmail(), forgetPasswordRequestDTO.getPhoneNumber());
        if (response.getStatus().equals("error")){
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("user/change-password")
    public ResponseEntity<GenericResponseDTO> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO, BindingResult bindingResult, Principal loggedInUser){
        if (bindingResult.hasErrors()){
            return validate(bindingResult);
        }
        GenericResponseDTO response = userService.changePassword(changePasswordDTO, loggedInUser);
        if (response.getStatus().equals("error")){
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<GenericResponseDTO> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        GenericResponseDTO errorResponse = new GenericResponseDTO();
        errorResponse.setStatus("error");
        errorResponse.setMessage("Validation error occurred");
        errorResponse.setErrorMessage(errors);
        errorResponse.setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }




}
