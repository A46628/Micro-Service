package asi.saga.demo.stock.model;

import java.util.List;

public class StockMessage {

    private List<ItemList> items; // Lista de itens de estoque

    public Boolean getIncrOrDecrease() {
        return incrOrDecrease;
    }

    public void setIncrOrDecrease(Boolean incrOrDecrease) {
        this.incrOrDecrease = incrOrDecrease;
    }

    private Boolean incrOrDecrease ;

    // Getter e Setter para a lista de itens
    public List<ItemList> getItems() {
        return items;
    }

    public void setItems(List<ItemList> items) {
        this.items = items;
    }

    // Construtores, toString() e outros métodos conforme necessário
    @Override
    public String toString() {
        return "StockMessage{" +
                "items=" + items +
                '}';
    }
}
