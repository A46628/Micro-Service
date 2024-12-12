package asi.saga.demo.order.model;


import asi.saga.demo.order.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ReservationResult {
    private boolean success;
    private List<ProductMessage> reservedProducts;

    public ReservationResult() {
        this.success = true;
        this.reservedProducts = new ArrayList<>();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ProductMessage> getReservedProducts() {
        return reservedProducts;
    }

    public void addReservedProduct(ProductMessage productReservation) {
        this.reservedProducts.add(productReservation);
    }


}
