package asi.saga.demo.stock.repository;

import asi.saga.demo.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, Integer> { // Tipo de ID ajustado
    //Stock findById(int id); // Método renomeado para refletir o campo correto

    Stock findById(UUID id);
}
