package uz.pdp.botsale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.botsale.entity.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "select * from users where user_id=:id and created_at  between '1990-09-01 15:13:09'=:startDate and '2080-09-01 19:43:57'=:endDate", nativeQuery = true)
    Optional<User> findAllByCreatedAtAAndUpdatedAt(@Param("startDate") UUID id, @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    Optional<User> findByPhoneNumber(String phoneNumber);

    void deleteById(UUID uuid);
}
