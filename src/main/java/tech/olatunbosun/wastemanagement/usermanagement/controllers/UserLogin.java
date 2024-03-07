package tech.olatunbosun.wastemanagement.usermanagement.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.olatunbosun.wastemanagement.usermanagement.request.LoginDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.GenericResponseDTO;
import tech.olatunbosun.wastemanagement.configs.JwtService;
import tech.olatunbosun.wastemanagement.usermanagement.services.UserService;
import tech.olatunbosun.wastemanagement.validation.ValidationErrorService;

import java.util.Map;

@RestController
@RequestMapping("v1/")
@RequiredArgsConstructor
public class UserLogin {


    private final ValidationErrorService validationErrorService;
    private final UserService userService;

    @PostMapping("/user/login")
    public ResponseEntity<GenericResponseDTO> login(@RequestBody LoginDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            validationError(bindingResult);
        }
        GenericResponseDTO genericResponseDTO = userService.login(loginDTO);
        if(genericResponseDTO.getStatus().equals("error")){
            return ResponseEntity.badRequest().body(genericResponseDTO);
        }
        return ResponseEntity.ok().body(genericResponseDTO);

    }


    private ResponseEntity<GenericResponseDTO> validationError(BindingResult bindingResult){
        Map<String, String> errors = validationErrorService.validate(bindingResult);
        GenericResponseDTO errorResponse = new GenericResponseDTO();
        errorResponse.setStatus("error");
        errorResponse.setMessage("Validation error occurred");
        errorResponse.setErrorData(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
