package asi.saga.demo.order.repository;

import asi.saga.demo.order.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocalStockRepository extends JpaRepository<Product, Integer> {
    //Stock findById(int id);

    Product findById(UUID id);
}
