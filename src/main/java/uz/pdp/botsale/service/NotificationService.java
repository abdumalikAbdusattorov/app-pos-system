package uz.pdp.botsale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ResPageable;
import uz.pdp.botsale.repository.NotificationRepository;
import uz.pdp.botsale.repository.PurchaseElementsRepository;
import uz.pdp.botsale.utils.CommonUtils;

import java.awt.*;
import java.sql.Timestamp;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    PurchaseElementsRepository purchaseElementsRepository;

    public ResPageable getNotifications(int page, int size, Timestamp initialTime, Timestamp expirationTime) {
        Page<ApiResponse> responsePage = notificationRepository.findAllByBeginDateAndEndDate(CommonUtils.getPageable(page, size), initialTime, expirationTime);
        return new ResPageable(responsePage.getContent(), responsePage.getTotalElements(), page);
    }

    public ApiResponse removeStatus(Long id) {
        try {
            notificationRepository.findById(id);
            return new ApiResponse("Deleted", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }

    public ApiResponse removeAll() {
        try {
            notificationRepository.deleteAll();
            return new ApiResponse("Deleted", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }
}
