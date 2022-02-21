package uz.pdp.botsale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.botsale.entity.SellDetails;

import java.sql.Timestamp;
import java.util.List;

public interface SellDetailsRepository extends JpaRepository<SellDetails, Long> {
    @Query(value = "select  s from sell_details s where created_at between '1990-09-01 15:13:09'=:startDate and '2080-09-01 19:43:57'=:endDate", nativeQuery = true)
    List<SellDetails> findAllByDate(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);
}
