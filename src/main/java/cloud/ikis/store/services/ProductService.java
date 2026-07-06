package cloud.ikis.store.services;

import cloud.ikis.store.dtos.ResponseDto;
import cloud.ikis.store.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseDto.response findAll() {
        return ResponseDto.response.success("success", productRepository.findAll());
    }
}
