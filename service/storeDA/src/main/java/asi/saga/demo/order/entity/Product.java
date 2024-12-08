package asi.saga.demo.order.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "produtos")
public class Product {


    @Id
    @Column(name = "id_produto", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nome_produto", nullable = false, length = 100)
    private String name;

    @Column(name = "descricao_produto", length = 255)
    private String description;

    @Column(name = "preco_produto", precision = 10, scale = 2)
    private BigDecimal price;



    @Column(name = "stockquantity")
    private int stockQuantity;


    @JoinColumn(name = "id_loja", nullable = false, foreignKey = @ForeignKey(name = "fk_produtos_lojas"))
    private UUID id_loja;


    public Product(){

    }
    public Product( String name, String description, BigDecimal price,int stockQuantity,  UUID store) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.id_loja = store;
        this.stockQuantity = stockQuantity;
    }

    public Product(UUID id,String name, String description, BigDecimal price,int stockQuantity,  UUID store){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.id_loja = store;
        this.stockQuantity = stockQuantity;
    }
    public UUID getId() {return id;}

    public void setId(UUID id) {this.id = id;}

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

    public UUID getId_loja() {
        return id_loja;
    }

    public void setId_loja(UUID id_loja) {
        this.id_loja = id_loja;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", store=" + id_loja +
                '}';
    }
}
