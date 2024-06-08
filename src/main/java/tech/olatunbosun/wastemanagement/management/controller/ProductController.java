package tech.olatunbosun.wastemanagement.management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import tech.olatunbosun.wastemanagement.management.requests.ProductRequestDTO;
import tech.olatunbosun.wastemanagement.management.respository.ProductRepository;
import tech.olatunbosun.wastemanagement.management.services.ProductServiceImpl;
import tech.olatunbosun.wastemanagement.usermanagement.response.GenericResponseDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static tech.olatunbosun.wastemanagement.usermanagement.utility.UtilityClass.getGenericResponseDTOResponseEntity;

/**
 * @author olulodeolatunbosun
 * @created 12/03/2024/03/2024 - 13:59
 */


@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    @PostMapping("/create")
    public ResponseEntity<GenericResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return validate(bindingResult);
        }

        GenericResponseDTO responseDTO = productService.createProduct(productRequestDto);
        if (responseDTO.getStatusCode() == HttpStatus.CREATED.value()){
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }

        responseDTO.setStatus("error");
        responseDTO.setMessage("Product not created");
        responseDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<GenericResponseDTO> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<GenericResponseDTO> getProductById(@PathVariable Integer productId) {
        GenericResponseDTO responseDTO = productService.getProductById(productId);
        if (responseDTO.getStatusCode() == HttpStatus.OK.value()){
            return ResponseEntity.ok(responseDTO);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<GenericResponseDTO> updateProduct(@PathVariable Integer productId, @Valid @RequestBody ProductRequestDTO productRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return validate(bindingResult);
        }
        GenericResponseDTO responseDTO = productService.updateProduct(productRequestDto, productId);
        if (responseDTO.getStatusCode() == HttpStatus.OK.value()){
            return ResponseEntity.ok(responseDTO);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<GenericResponseDTO> deleteProduct(@PathVariable Integer productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    public ResponseEntity<GenericResponseDTO> validate(BindingResult result) {
        return getGenericResponseDTOResponseEntity(result);
    }
}
