package uz.pdp.botsale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.botsale.entity.SellDetailCount;
import uz.pdp.botsale.entity.SellDetails;
import uz.pdp.botsale.exception.ResourceNotFoundException;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ReqSellDetailCount;
import uz.pdp.botsale.payload.ReqSellDetails;
import uz.pdp.botsale.payload.ResPageable;
import uz.pdp.botsale.repository.ProductRepository;
import uz.pdp.botsale.repository.PurchaseElementsRepository;
import uz.pdp.botsale.repository.SellDetailCountRepository;
import uz.pdp.botsale.repository.SellDetailsRepository;
import uz.pdp.botsale.utils.CommonUtils;

import java.awt.*;

@Service
public class SellDetailsService {

    @Autowired
    SellDetailsRepository sellDetailsRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PurchaseElementsRepository purchaseElementsRepository;

    public ApiResponse saveOrEdit(ReqSellDetails reqSellDetails) {
        ApiResponse response = new ApiResponse();
        try {
            if (reqSellDetails.getProductBarcode().length() > 0 && reqSellDetails.getCount() > 0) {
                response.setMessage("Saved");
                response.setSuccess(true);
                response.setMessageType(TrayIcon.MessageType.INFO);
                SellDetails sellDetails = new SellDetails();
                if (reqSellDetails.getId() != null) {
                    sellDetails = sellDetailsRepository.findById(reqSellDetails.getId()).orElseThrow(() -> new ResourceNotFoundException("PurchaseElement", "id", reqSellDetails.getId()));
                    response.setMessage("Edited");
                }
//                sellDetails.setPrice(reqSellDetails.getPrice());
                sellDetails.setCount(reqSellDetails.getCount());
//                for (ReqSellDetailCount reqSellDetailCount : reqSellDetails.getReqSellDetailCounts()) {
//                    SellDetailCount sellDetailCount = new SellDetailCount();
//                    sellDetailCount.setCount(reqSellDetailCount.getCount());
//                    sellDetailCount.setPurchaseElements(purchaseElementsRepository.findById(reqSellDetailCount.getPurchaseElementId()).orElseThrow(() -> new ResourceNotFoundException("PurchaseElements", "id", reqSellDetailCount.getPurchaseElementId())));
//                }
                sellDetails.setProduct(productRepository.findByBarCode(reqSellDetails.getProductBarcode()).orElseThrow(() -> new ResourceNotFoundException("Product", "id", reqSellDetails.getProductBarcode())));
                sellDetailsRepository.save(sellDetails);
            } else if (reqSellDetails.getProductBarcode().length() <= 0) {
                response.setMessage("must be Product barcode ");
                response.setSuccess(false);
                response.setMessageType(TrayIcon.MessageType.WARNING);
            }
            /*else if (reqSellDetails.getPrice() <= 0) {
                response.setMessage("should be Price");
                response.setSuccess(false);
                response.setMessageType(TrayIcon.MessageType.WARNING);
            } */else if (reqSellDetails.getCount() <= 0) {
                response.setMessage("should be Count");
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
        Page<SellDetails> sellDetailsPage = sellDetailsRepository.findAll(CommonUtils.getPageableById(page, size));
        return new ResPageable(sellDetailsPage.getContent(), sellDetailsPage.getTotalElements(), page);
    }

/*    public ApiResponse changeActive(Long id, boolean active) {
        try {
            SellDetails sellDetails = sellDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("SellDetail", "id", id));
            sellDetails.setActive(active);
            sellDetailsRepository.save(sellDetails);
            return new ApiResponse(active ? "Activated" : "Blocked", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }*/

/*    public ApiResponse removePurchaseElements(Long id) {
        try {
            sellDetailsRepository.deleteById(id);
            return new ApiResponse("Deleted", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }*/
}
