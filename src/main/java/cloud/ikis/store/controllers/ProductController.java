package cloud.ikis.store.controllers;

import cloud.ikis.store.dtos.ProductDto;
import cloud.ikis.store.dtos.ResponseDto;
import cloud.ikis.store.services.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseDto.response findAll() {
        return productService.findAll();
    }

    @PostMapping
    public ResponseDto.response create(@RequestBody ProductDto productDto) {
        return productService.create(productDto);
    }

    @PutMapping("/{id}")
    public ResponseDto.response fullUpdate(@PathVariable String id, @RequestBody ProductDto productDto) {
        return productService.fullUpdate(id, productDto);
    }

    @PatchMapping("/{id}")
    public ResponseDto.response partialUpdate(@PathVariable String id, @RequestBody ProductDto productDto) {
        return productService.partialUpdate(id, productDto);
    }

    @PatchMapping("/delete/{id}")
    public ResponseDto.response softDelete(@PathVariable String id) {
        return productService.softDelete(id);
    }
}
