package tech.olatunbosun.wastemanagement.management.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{

    private  ProductRepository productRepository;
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

        //TODO: Implement this method
        return null;

    }

    @Override
    public GenericResponseDTO deleteProduct(ProductRequestDTO productRequestDto) {
        //TODO: Implement this method
        return null;
    }

    @Override
    public GenericResponseDTO getProductById(ProductRequestDTO productRequestDto) {
        //TODO: Implement this method
        return null;
    }

    @Override
    public GenericResponseDTO getAllProducts() {
        GenericResponseDTO responseDTO = new GenericResponseDTO();
        responseDTO.setStatus("success");
        responseDTO.setMessage("All products fetched successfully");
        responseDTO.setStatusCode(HttpStatus.OK.value());
        responseDTO.setData(productRepository.findAll());
        return responseDTO;
    }
}
