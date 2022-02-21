/*
package uz.pdp.botsale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.botsale.entity.Base;
import uz.pdp.botsale.exception.ResourceNotFoundException;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ReqBase;
import uz.pdp.botsale.repository.BaseRepository;
import uz.pdp.botsale.repository.MarketRepository;
import uz.pdp.botsale.repository.PurchaseElementsRepository;

import java.awt.*;

@Service
public class BaseService {

    @Autowired
    BaseRepository baseRepository;

    @Autowired
    MarketRepository marketRepository;

    @Autowired
    PurchaseElementsRepository purchaseElementsRepository;

    public ApiResponse saveOrEdit(ReqBase reqBase) {

        ApiResponse response = new ApiResponse();
        try {
            response.setSuccess(true);
            response.setMessage("Saved");
            response.setMessageType(TrayIcon.MessageType.INFO);
            Base base = new Base();
            if (reqBase.getId() != null) {
                base = baseRepository.findById(reqBase.getId()).orElseThrow(() -> new ResourceNotFoundException("Base", "id", reqBase.getId()));
                response.setMessage("Edited");
            }
            base.setMarket(marketRepository.findById(reqBase.getMarketId()).orElseThrow(() -> new ResourceNotFoundException("market", "id", reqBase.getMarketId())));
            base.setPurchaseElementsList(reqBase.getPurchaseElementsList());
            base.setProduct(reqBase.getProductId());
            //    base.setCount(purchaseElementsRepository.findById());
            baseRepository.save(base);

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error");
            response.setMessageType(TrayIcon.MessageType.INFO);
        }
        return response;
    }

}
*/
