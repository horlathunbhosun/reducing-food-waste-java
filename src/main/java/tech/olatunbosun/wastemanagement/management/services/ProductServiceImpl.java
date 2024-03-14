package tech.olatunbosun.wastemanagement.management.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.olatunbosun.wastemanagement.management.models.Product;
import tech.olatunbosun.wastemanagement.management.requests.ProductRequestDTO;
import tech.olatunbosun.wastemanagement.management.respository.ProductRepository;
import tech.olatunbosun.wastemanagement.usermanagement.response.GenericResponseDTO;

/**
 * @author olulodeolatunbosun
 * @created 12/03/2024/03/2024 - 13:16
 */


@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{

    private static ProductRepository productRepository;
    @Override
    public GenericResponseDTO createProduct(ProductRequestDTO productRequestDto) {

        GenericResponseDTO responseDTO = new GenericResponseDTO();
         var product = Product.builder().name(productRequestDto.getName()).description(productRequestDto.getDescription()).build();
        productRepository.save(product);

        responseDTO.setStatus("success");
        responseDTO.setMessage("Product created successfully");
        responseDTO.setStatusCode(HttpStatus.CREATED.value());
        return responseDTO;
    }

    @Override
    public GenericResponseDTO updateProduct(ProductRequestDTO productRequestDto) {
        return null;
    }

    @Override
    public GenericResponseDTO editProduct(ProductRequestDTO productRequestDto) {
        return null;
    }
}
