package uz.pdp.botsale.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.botsale.entity.Company;

import java.sql.Timestamp;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Page<Company> findAllByNameStartingWithIgnoreCaseOrAgentNameStartingWithIgnoreCase(String name, String agent, Pageable pageableById);

    Page<Company> findAllByActive(boolean active, Pageable pageableById);

    String findByName(String name);

    @Query(value = "select sum (c) from company c where created_at between '1990-09-01 15:13:09'=:startDate and '2080-09-01 19:43:57'=:endDate", nativeQuery = true)
    Double findAllByName(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);
}
