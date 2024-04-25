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

import java.util.Optional;

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
    public GenericResponseDTO updateProduct(ProductRequestDTO productRequestDto, Integer productId) {
        GenericResponseDTO responseDTO = new GenericResponseDTO();
       Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            product.get().setName(productRequestDto.getName());
            product.get().setDescription(productRequestDto.getDescription());
            productRepository.save(product.get());
            responseDTO.setStatus("success");
            responseDTO.setMessage("Product updated successfully");
            responseDTO.setStatusCode(HttpStatus.OK.value());
            responseDTO.setData(product.get());
        }else{
            responseDTO.setStatus("error");
            responseDTO.setMessage("Product not found");
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
        }
        return responseDTO;

    }


    @Override
    public GenericResponseDTO deleteProduct(Integer productId) {
        GenericResponseDTO responseDTO = new GenericResponseDTO();
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            productRepository.delete(product.get());
            responseDTO.setStatus("success");
            responseDTO.setMessage("Product deleted successfully");
            responseDTO.setStatusCode(HttpStatus.OK.value());
        }else{
            responseDTO.setStatus("error");
            responseDTO.setMessage("Product not found");
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
        }
        return responseDTO;
    }

    @Override
    public GenericResponseDTO getProductById(Integer productRequestDto) {
      Optional<Product> product = productRepository.findById(productRequestDto);
        GenericResponseDTO responseDTO = new GenericResponseDTO();
        if (product.isPresent()) {
            responseDTO.setStatus("success");
            responseDTO.setMessage("Product fetched successfully");
            responseDTO.setStatusCode(HttpStatus.OK.value());
            responseDTO.setData(product.get());
        }else{
            responseDTO.setStatus("error");
            responseDTO.setMessage("Product not found");
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
        }
        return responseDTO;
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
