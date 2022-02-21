package uz.pdp.botsale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.service.NotificationService;
import uz.pdp.botsale.utils.AppConstants;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/notification")
public class NotificationsController {

    @Autowired
    NotificationService notificationService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR','ROLE_WAREHOUSE_MANAGER')")
    @GetMapping
    public HttpEntity<?> pageable(@RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                  @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                  @RequestParam(name = "beginDate", defaultValue = AppConstants.BEGIN_DATE) Timestamp initialTime,
                                  @RequestParam(name = "endDate", defaultValue = AppConstants.END_DATE) Timestamp expirationTime) {
        return ResponseEntity.ok(notificationService.getNotifications(page, size, initialTime, expirationTime));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> removeMessage(@PathVariable Long id) {
        ApiResponse response = notificationService.removeStatus(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping
    public HttpEntity<?> removeAll() {
        return ResponseEntity.ok(notificationService.removeAll());
    }
}
