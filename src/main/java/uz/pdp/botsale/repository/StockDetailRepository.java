package uz.pdp.botsale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.botsale.entity.StockDetail;

public interface StockDetailRepository extends JpaRepository<StockDetail, Long> {
}
