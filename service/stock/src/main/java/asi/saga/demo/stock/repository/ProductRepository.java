package asi.saga.demo.stock.repository;

import asi.saga.demo.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Stock, Integer> {
}
