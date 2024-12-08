package asi.saga.demo.stock.model;

import java.util.UUID;

public class ItemList {

    private UUID id;          // ID do produto (UUID em formato de string)
    private String description; // Descrição do produto
    private int quantity;       // Quantidade solicitada
    private float unitPrice;    // Preço unitário do produto

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "ItemList{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
