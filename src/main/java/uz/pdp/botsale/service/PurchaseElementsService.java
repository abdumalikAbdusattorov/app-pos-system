package uz.pdp.botsale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.botsale.collections.PurchaseElementsCol;
import uz.pdp.botsale.entity.Notification;
import uz.pdp.botsale.entity.PurchaseElements;
import uz.pdp.botsale.entity.enums.RoleName;
import uz.pdp.botsale.exception.ResourceNotFoundException;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ApiResponseModel;
import uz.pdp.botsale.payload.ReqPurchaseElements;
import uz.pdp.botsale.payload.ResPageable;
import uz.pdp.botsale.repository.NotificationRepository;
import uz.pdp.botsale.repository.ProductRepository;
import uz.pdp.botsale.repository.PurchaseElementsRepository;
import uz.pdp.botsale.utils.CommonUtils;

import java.awt.*;
import java.util.Optional;

@Service
public class PurchaseElementsService {

    @Autowired
    PurchaseElementsRepository purchaseElementsRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public ApiResponse saveOrEdit(ReqPurchaseElements reqPurchaseElements) {
        ApiResponse response = new ApiResponse();
        try {
            response.setMessage("Saved");
            response.setSuccess(true);
            response.setMessageType(TrayIcon.MessageType.INFO);
            PurchaseElements purchaseElements = new PurchaseElements();
            if (reqPurchaseElements.getId() != null) {
                purchaseElementsRepository.findById(reqPurchaseElements.getId()).orElseThrow(() -> new ResourceNotFoundException("PurchaseElement", "id", reqPurchaseElements.getId()));
                response.setMessage("Edited");
            }
            purchaseElements.setProduct(productRepository.findById(reqPurchaseElements.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product", "id", reqPurchaseElements.getProductId())));
            purchaseElements.setIncomePrice(reqPurchaseElements.getIncomePrice());
            purchaseElements.setSellPrice(reqPurchaseElements.getSellPrice());
            purchaseElements.setCount(reqPurchaseElements.getCount());
            purchaseElementsRepository.save(purchaseElements);
            notificationRepository.save(new Notification("Bazaga yangi tavar qo`shildi tavar nomi(" + purchaseElements.getProduct().getName() + "),miqdori" + reqPurchaseElements.getCount() + "kirgan sanasi ", true, RoleName.ROLE_WAREHOUSE_MANAGER, TrayIcon.MessageType.INFO));
        } catch (Exception e) {
            response.setMessage("Error");
            response.setSuccess(false);
            response.setMessageType(TrayIcon.MessageType.ERROR);
        }
        return response;
    }

    public ResPageable pageable(Integer page, Integer size) {
        Page<PurchaseElements> purchasePage = purchaseElementsRepository.findAll(CommonUtils.getPageableById(page, size));
        return new ResPageable(purchasePage.getContent(), purchasePage.getTotalElements(), page);
    }

    public ApiResponse changeActive(Long id, boolean active) {
        try {
            PurchaseElements purchaseElements = purchaseElementsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("PurchesElements", "id", id));
            purchaseElements.setActive(active);
            purchaseElementsRepository.save(purchaseElements);
            return new ApiResponse(active ? "Activated" : "Blocked", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }

    public ApiResponseModel getPurElementCol(Long id) {
        try {
            Optional<PurchaseElementsCol> elements = purchaseElementsRepository.findPurchaseElementsById(id);
            return new ApiResponseModel(true, "Ok", elements.get());
        } catch (Exception e) {
            return new ApiResponseModel(false, "Error", TrayIcon.MessageType.ERROR);
        }
    }

    public ApiResponse removePurchaseElements(Long id) {
        try {
            purchaseElementsRepository.deleteById(id);
            return new ApiResponse("Deleted", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }

}
