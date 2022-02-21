package uz.pdp.botsale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.botsale.collections.CashCol;
import uz.pdp.botsale.entity.Cash;
import uz.pdp.botsale.entity.Notification;
import uz.pdp.botsale.exception.ResourceNotFoundException;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ApiResponseModel;
import uz.pdp.botsale.payload.ReqCash;
import uz.pdp.botsale.payload.ResPageable;
import uz.pdp.botsale.repository.CashRepository;
import uz.pdp.botsale.repository.NotificationRepository;
import uz.pdp.botsale.repository.SellRepository;
import uz.pdp.botsale.utils.CommonUtils;

import java.awt.*;
import java.util.Optional;

@Service
public class CashService {

    @Autowired
    CashRepository cashRepository;

    @Autowired
    SellRepository sellRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public ApiResponse saveOrEdit(ReqCash reqCash) {
        ApiResponse response = new ApiResponse();
        try {
            if (reqCash.getName().length() > 0 && reqCash.getStartBalance() >= 0) {
                response.setMessage("Saved");
                response.setSuccess(true);
                response.setMessageType(TrayIcon.MessageType.INFO);
                Cash cash = new Cash();
                if (reqCash.getId() != null) {
                    cash = cashRepository.findById(reqCash.getId()).orElseThrow(() -> new ResourceNotFoundException("Cash", "id", reqCash.getId()));
                    response.setMessage("Edited");
                }
                cash.setName(reqCash.getName());
                cash.setStartBalance(reqCash.getStartBalance());
                notificationRepository.save(new Notification("sizga yangi Kassa qo`shildi ,Kassa nomi:" + cash.getName() + " !", true, TrayIcon.MessageType.INFO));
                cashRepository.save(cash);
            } else if (reqCash.getName().length() == 0) {
                response.setMessage("must be name");
                response.setSuccess(false);
                response.setMessageType(TrayIcon.MessageType.WARNING);
            } else if (reqCash.getSellList().size() == 0) {
                response.setMessage("must be sell List");
                response.setSuccess(false);
                response.setMessageType(TrayIcon.MessageType.WARNING);
            }
        } catch (Exception e) {
            response.setMessage("Error");
            response.setSuccess(false);
            response.setMessageType(TrayIcon.MessageType.ERROR);
        }
        return response;
    }

    public ResPageable pageable(Integer page, Integer size) {
        Page<Cash> cashPage = cashRepository.findAll(CommonUtils.getPageableById(page, size));
        return new ResPageable(cashPage.getContent(), cashPage.getTotalElements(), page);
    }

    public ApiResponse changeActive(Integer id, boolean active) {
        try {
            Cash cash = cashRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("cash", "id", id));
            cash.setActive(active);
            cashRepository.save(cash);
            return new ApiResponse(active ? "Activated" : "Blocked", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }

    public ApiResponseModel cashCollection(Integer id) {
        try {
            return new ApiResponseModel(true, "Ok", cashRepository.findCashById(id).get());
        } catch (Exception e) {
            return new ApiResponseModel(false, "Error", TrayIcon.MessageType.ERROR);
        }
    }

    public ApiResponse removeCash(Integer id) {
        try {
            cashRepository.deleteById(id);
            return new ApiResponse("Deleted", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }
}
