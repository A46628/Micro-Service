package asi.saga.demo.order.model;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductMessage {
    private UUID id;
    private String name;
    private String description;
    private UUID storeId;



    private int stockQuantity;



    public ProductMessage(UUID id, String name, String description,int  stockQuantity, UUID storeId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.storeId = storeId;
        this.stockQuantity = stockQuantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public UUID getStoreId() {
        return storeId;
    }

    public void setStoreId(UUID storeId) {
        this.storeId = storeId;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "ProductMessage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", storeId=" + storeId +
                '}';
    }
}
