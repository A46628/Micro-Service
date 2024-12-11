package asi.saga.demo.order.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "item_lists")
public class ItemList {

    @Id
    private UUID id;

    @Column(name = "description") // Mapeando explicitamente a coluna 'description'
    private String description;

    @Column(name = "quantity") // Mapeando explicitamente a coluna 'quantity'
    private int quantity;

    @Column(name = "unit_price") // Mapeando explicitamente a coluna 'unit_price'
    private float unitPrice;

    @ManyToOne
    @JoinColumn(name = "order_message_id") // Especificando a chave estrangeira 'order_message_id'
    private OrderMessage orderMessage;

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

    public OrderMessage getOrderMessage() {
        return orderMessage;
    }

    public void setOrderMessage(OrderMessage orderMessage) {
        this.orderMessage = orderMessage;
    }

    @Override
    public String toString() {
        return "ItemList{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
