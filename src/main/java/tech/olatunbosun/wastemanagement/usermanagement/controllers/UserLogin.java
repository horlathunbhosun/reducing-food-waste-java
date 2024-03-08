package tech.olatunbosun.wastemanagement.usermanagement.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.olatunbosun.wastemanagement.usermanagement.request.LoginDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.GenericResponseDTO;
import tech.olatunbosun.wastemanagement.configs.JwtService;
import tech.olatunbosun.wastemanagement.usermanagement.services.UserService;
import tech.olatunbosun.wastemanagement.usermanagement.services.UserServiceImpl;
import tech.olatunbosun.wastemanagement.validation.ValidationErrorService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1/")
@RequiredArgsConstructor
public class UserLogin {


    private final ValidationErrorService validationErrorService;
    private final UserServiceImpl userService;

    @PostMapping("/user/login")
    public ResponseEntity<GenericResponseDTO> login(@RequestBody LoginDTO loginDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return validate(bindingResult);
        }
        GenericResponseDTO genericResponseDTO = userService.login(loginDTO);
        if(genericResponseDTO.getStatus().equals("error")){
            return new ResponseEntity<>(genericResponseDTO, HttpStatusCode.valueOf(genericResponseDTO.getStatusCode()));
        }
        return ResponseEntity.ok().body(genericResponseDTO);

    }

    @PostMapping("/user/refresh-token")
    public ResponseEntity<GenericResponseDTO> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        GenericResponseDTO genericResponseDTO = userService.refreshToken(request, response);
        if(genericResponseDTO.getStatus().equals("error")){
            return new ResponseEntity<>(genericResponseDTO, HttpStatusCode.valueOf(genericResponseDTO.getStatusCode()));
        }
        return ResponseEntity.ok().body(genericResponseDTO);
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
