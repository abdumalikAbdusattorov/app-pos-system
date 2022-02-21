package uz.pdp.botsale.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.botsale.collections.ClientCol;
import uz.pdp.botsale.entity.Client;

import java.sql.Timestamp;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
   Page<Client> findAllByNameStartingWithIgnoreCase(String search, Pageable pageableById);
   
   @Query(value = "select sum (c.debt) from client c where created_at between '1990-09-01 15:13:09'=:startDate and '2080-09-01 19:43:57'=:endDate", nativeQuery = true)
   Double findAllByDebt(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);
}
