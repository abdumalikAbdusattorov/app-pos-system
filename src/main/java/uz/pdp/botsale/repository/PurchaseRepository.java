package uz.pdp.botsale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.botsale.collections.PurchaseCol;
import uz.pdp.botsale.entity.Purchase;

import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Optional<PurchaseCol> findPurchaseById(Long id);
}
