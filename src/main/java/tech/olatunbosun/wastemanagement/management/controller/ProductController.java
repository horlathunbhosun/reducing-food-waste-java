package tech.olatunbosun.wastemanagement.management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tech.olatunbosun.wastemanagement.management.requests.ProductRequestDTO;
import tech.olatunbosun.wastemanagement.management.respository.ProductRepository;
import tech.olatunbosun.wastemanagement.management.services.ProductServiceImpl;
import tech.olatunbosun.wastemanagement.usermanagement.response.GenericResponseDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author olulodeolatunbosun
 * @created 12/03/2024/03/2024 - 13:59
 */


@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;


    public ResponseEntity<GenericResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return validate(bindingResult);
        }

        productService.createProduct(productRequestDto);


        return null;
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
