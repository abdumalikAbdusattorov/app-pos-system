package uz.pdp.botsale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.botsale.entity.Profit;

public interface ProfitRepository extends JpaRepository<Profit, Integer> {

    @Modifying
    @Query(value = "insert into profit (total_profit) values (0=:profit)", nativeQuery = true)
    void insert(@Param("profit") Double profit1);
}
