package uz.pdp.botsale.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.botsale.entity.Notification;
import uz.pdp.botsale.payload.ApiResponse;

import java.sql.Timestamp;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query(value = "select * from notification where created_at between '1970-01-01 00:00:00'=:beginDate and '2100-09-26 10:32:33'=:entDate", nativeQuery = true)
    Page<ApiResponse> findAllByBeginDateAndEndDate(Pageable pageable,@Param(value = "beginDate") Timestamp beginDate, @Param(value = "entDate") Timestamp entDate);

//    Page<ApiResponse> findAllByMessageStartingWithIgnoreCase(String search, Pageable pageable);
}
