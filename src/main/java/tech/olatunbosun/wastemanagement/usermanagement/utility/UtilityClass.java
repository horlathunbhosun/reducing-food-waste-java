package tech.olatunbosun.wastemanagement.usermanagement.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import tech.olatunbosun.wastemanagement.usermanagement.response.GenericResponseDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author olulodeolatunbosun
 * @created 08/06/2024/06/2024 - 04:35
 */
public class UtilityClass {

    public static ResponseEntity<GenericResponseDTO> getGenericResponseDTOResponseEntity(BindingResult result) {
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
