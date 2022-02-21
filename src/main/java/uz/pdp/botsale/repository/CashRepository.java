package uz.pdp.botsale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.botsale.collections.CashCol;
import uz.pdp.botsale.entity.Cash;

import java.util.Optional;

public interface CashRepository extends JpaRepository<Cash, Integer> {
    String findByName(String name);

    Optional<CashCol> findCashById(Integer id);

//    @Query(value = "select * from cash where id=:id", nativeQuery = true)
//    Optional<CashCol> findByIdIn(@Param(value = "id") Integer id);
}
