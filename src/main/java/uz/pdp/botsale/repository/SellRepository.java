package uz.pdp.botsale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.botsale.collections.SellCol;
import uz.pdp.botsale.entity.Sell;

import java.sql.Timestamp;
import java.util.Optional;

public interface SellRepository extends JpaRepository<Sell, Long> {
    @Query(value = "select sum (s.total) from sell s where created_at between '1990-09-01 15:13:09'=:startDate and '2080-09-01 19:43:57'=:endDate", nativeQuery = true)
    Double findAllByTotal(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Query(value = "select sum (s) from sell s where created_at between '1990-09-01 15:13:09'=:startDate and '2080-09-01 19:43:57'=:endDate", nativeQuery = true)
    Double findAllByTotalAndCash(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Query(value = "select sum (p.count * p.income_price) from purchase_elements p where created_at between '1990-09-01 15:13:09'=:startDate and '2080-09-01 19:43:57'=:endDate-(select sum (s.total) from sell s where created_at between '1990-09-01 15:13:09'=:startDate and '2080-09-01 19:43:57'=:endDate)", nativeQuery = true)
    Double findAllByTotalAndCashAmAndCash(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    Optional<SellCol> findSellById(Long id);
}
