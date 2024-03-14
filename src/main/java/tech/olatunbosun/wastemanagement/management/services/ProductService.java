package tech.olatunbosun.wastemanagement.management.services;

import org.springframework.stereotype.Service;
import tech.olatunbosun.wastemanagement.management.requests.ProductRequestDTO;
import tech.olatunbosun.wastemanagement.usermanagement.response.GenericResponseDTO;

/**
 * @author olulodeolatunbosun
 * @created 12/03/2024/03/2024 - 13:13
 */


public interface ProductService {

    GenericResponseDTO createProduct(ProductRequestDTO productRequestDto);


    GenericResponseDTO updateProduct(ProductRequestDTO productRequestDto);

    GenericResponseDTO  editProduct(ProductRequestDTO productRequestDto);


}

