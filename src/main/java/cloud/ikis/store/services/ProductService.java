package cloud.ikis.store.services;

import cloud.ikis.store.dtos.ProductDto;
import cloud.ikis.store.dtos.ResponseDto;
import cloud.ikis.store.entities.Product;
import cloud.ikis.store.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseDto.response findAll() {
        return ResponseDto.response.success("success", productRepository.findAll());
    }

    public ResponseDto.response create(ProductDto productDto) {
        Product product = new Product(UUID.randomUUID(),
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getStock(),
                Instant.now(),
                Instant.now(),
                null
        );

        return ResponseDto.response.success("success", productRepository.save(product));
    }

    public ResponseDto.response fullUpdate(String id, ProductDto productDto) {
        Product product = findProductById(id);
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setUpdatedAt(Instant.now());

        return ResponseDto.response.success("success", productRepository.save(product));
    }

    public ResponseDto.response partialUpdate(String id, ProductDto productDto) {
        Product product = findProductById(id);
        if (productDto.getName() != null) product.setName(productDto.getName());
        if (productDto.getDescription() != null) product.setDescription(productDto.getDescription());
        if (productDto.getPrice() != null) product.setPrice(productDto.getPrice());
        if (productDto.getStock() != null) product.setStock(productDto.getStock());
        product.setUpdatedAt(Instant.now());

        return ResponseDto.response.success("success", productRepository.save(product));
    }

    public ResponseDto.response softDelete(String id) {
        Product product = findProductById(id);
        product.setUpdatedAt(Instant.now());
        product.setDeletedAt(Instant.now());
        productRepository.save(product);

        return ResponseDto.response.success("success", null);
    }

    public Product findProductById(String id) {
        UUID uuid = UUID.fromString(id);
        return productRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }
}
