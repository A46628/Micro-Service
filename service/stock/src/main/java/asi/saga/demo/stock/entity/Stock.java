package asi.saga.demo.stock.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Stock {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id; // Alterado para UUID

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unitprice")
    private float unitPrice;

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
        return "Stock{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
