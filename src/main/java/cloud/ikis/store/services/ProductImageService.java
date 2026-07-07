package cloud.ikis.store.services;

import cloud.ikis.store.dtos.ProductImageDto;
import cloud.ikis.store.dtos.ResponseDto;
import cloud.ikis.store.entities.ProductImage;
import cloud.ikis.store.repositories.ProductImageRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ProductImageService {
    private final ProductImageRepository productImageRepository;

    public ProductImageService(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    public ResponseDto.response create(String productId, ProductImageDto productImageDto) {
        ProductImage productImage = new ProductImage(UUID.randomUUID(),
                UUID.fromString(productId),
                productImageDto.getImage(),
                Instant.now(),
                Instant.now(),
                null
                );

        return ResponseDto.response.success("success", productImageRepository.save(productImage));
    }
}
