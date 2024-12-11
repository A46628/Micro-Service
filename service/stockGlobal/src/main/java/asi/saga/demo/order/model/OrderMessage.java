package asi.saga.demo.order.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_messages")
public class OrderMessage {

     @Id  // Marque este campo como chave primária
     private UUID id;

     private String cardid;
     private String issuer;

     @JsonProperty("itemList")
     @OneToMany(mappedBy = "orderMessage", cascade = CascadeType.ALL, orphanRemoval = true)

     private List<ItemList> itemList;

     public OrderMessage() {
          this.id = UUID.randomUUID();  // Gere um UUID para o ID de cada novo pedido
          this.itemList = new ArrayList<>();
     }

     @Override
     public String toString() {
          return "{" +
                  "id=" + id +  // Inclua o id no toString
                  ", cardid='" + cardid + '\'' +
                  ", issuer='" + issuer + '\'' +
                  ", itemList=" + itemList +
                  '}';
     }

     public UUID getId() {
          return id;
     }

     public void setId(UUID id) {
          this.id = id;
     }

     public String getCardid() {
          return cardid;
     }

     public void setCardid(String cardid) {
          this.cardid = cardid;
     }

     public String getIssuer() {
          return issuer;
     }

     public void setIssuer(String issuer) {
          this.issuer = issuer;
     }

     public List<ItemList> getItemLists() {
          return itemList;
     }

     public void setItemLists(List<ItemList> itemList) {
          this.itemList = itemList;
     }
}

