package cloud.ikis.store.dtos;

import java.math.BigDecimal;
import java.time.Instant;

public class ProductDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Instant UpdatedAt;
    private Instant DeletedAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Instant getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        UpdatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return DeletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        DeletedAt = deletedAt;
    }
}
