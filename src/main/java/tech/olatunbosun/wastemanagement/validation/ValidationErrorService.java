package tech.olatunbosun.wastemanagement.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import tech.olatunbosun.wastemanagement.usermanagement.response.GenericResponseDTO;

import java.util.HashMap;
import java.util.Map;


@Service
public class ValidationErrorService {

    public Map<String, String> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return errors;
    }


    /**
     * @param bindingResult the result of the validation
     * @return
     */
    public ResponseEntity<GenericResponseDTO> validationError(BindingResult bindingResult){
        Map<String, String> errors = validate(bindingResult);
        GenericResponseDTO errorResponse = new GenericResponseDTO();
        errorResponse.setStatus("error");
        errorResponse.setMessage("Validation error occurred");
        errorResponse.setErrorData(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}