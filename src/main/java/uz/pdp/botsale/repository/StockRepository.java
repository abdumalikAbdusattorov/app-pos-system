package uz.pdp.botsale.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.botsale.collections.StockCol;
import uz.pdp.botsale.entity.Stock;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    Page<Stock> findAllByNameStartingWithIgnoreCase(String search, Pageable pageable);
   
   Optional<StockCol> findStockById(Integer id);
}
