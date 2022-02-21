package uz.pdp.botsale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.botsale.collections.PurchaseCol;
import uz.pdp.botsale.entity.Purchase;
import uz.pdp.botsale.entity.PurchaseElements;
import uz.pdp.botsale.exception.ResourceNotFoundException;
import uz.pdp.botsale.payload.*;
import uz.pdp.botsale.repository.CompanyRepository;
import uz.pdp.botsale.repository.ProductRepository;
import uz.pdp.botsale.repository.PurchaseElementsRepository;
import uz.pdp.botsale.repository.PurchaseRepository;
import uz.pdp.botsale.utils.CommonUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    PurchaseElementsRepository purchaseElementsRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ProductRepository productRepository;

    public ApiResponse saveOrEdit(ReqPurchase reqPurchase) {
        ApiResponse response = new ApiResponse();
        try {
            if (reqPurchase.getCompanyId() != null && reqPurchase.getReqPurchaseElementsList().size() > 0) {
                response.setMessage("Saved");
                response.setSuccess(true);
                response.setMessageType(TrayIcon.MessageType.INFO);
                Purchase purchase = new Purchase();
                /*if (reqPurchase.getId() != null) {
                    purchase = purchaseRepository.findById(reqPurchase.getId()).orElseThrow(() -> new ResourceNotFoundException("purchase", "id", reqPurchase.getId()));
                    response.setMessage("Edited");
                }*/
                purchase.setCompany(companyRepository.findById(reqPurchase.getCompanyId()).orElseThrow(() -> new ResourceNotFoundException("Company", "id", reqPurchase.getCompanyId())));
                double sum = 0.0;
                List<PurchaseElements> purchaseElementsList = new ArrayList<>();
                for (ReqPurchaseElements reqPurchaseElements : reqPurchase.getReqPurchaseElementsList()) {
                    PurchaseElements purchaseElements = new PurchaseElements();
                    if (reqPurchaseElements.getId() != null) {
                        purchaseElements = purchaseElementsRepository.findById(reqPurchaseElements.getId()).orElseThrow(() -> new ResourceNotFoundException("PurchaseElement", "id", reqPurchaseElements.getId()));
                        purchaseElementsList.add(purchaseElements);
                    } else {
                        purchaseElements.setSellPrice(reqPurchaseElements.getSellPrice());
                        purchaseElements.setPresentPrice(reqPurchaseElements.getSellPrice());
                        purchaseElements.setIncomePrice(reqPurchaseElements.getIncomePrice());
                        purchaseElements.setCount(reqPurchaseElements.getCount());
                        purchaseElements.setPresentCount(reqPurchaseElements.getCount());
                        purchaseElements.setMinCount(reqPurchaseElements.getMinCount());
                        purchaseElements.setProduct(productRepository.findById(reqPurchaseElements.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product", "Id", reqPurchaseElements.getProductId())));
                        purchaseElementsList.add(purchaseElements);
                        purchaseElementsRepository.save(purchaseElements);
                    }
                    sum += (reqPurchaseElements.getCount() * reqPurchaseElements.getSellPrice());
                }
                purchase.setPurchaseElementsList(purchaseElementsList);
//                Double sum = reqPurchase.getReqPurchaseElementsList().stream().map(s -> s.getCount() * s.getIncomePrice()).mapToDouble(Double::doubleValue).sum();
//                 sum += purchaseElementsRepository.findByPurchaseId(reqPurchase.getId());
                purchase.setTotal(sum);
                purchaseRepository.save(purchase);
            } else if (reqPurchase.getReqPurchaseElementsList().size() == 0) {
                response.setMessage("should Purchase Elements");
                response.setSuccess(false);
                response.setMessageType(TrayIcon.MessageType.WARNING);
            } else if (reqPurchase.getCompanyId() == 0) {
                response.setMessage("should be product id");
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
        Page<Purchase> purchasePage = purchaseRepository.findAll(CommonUtils.getPageableById(page, size));
        return new ResPageable(purchasePage.getContent(), purchasePage.getTotalElements(), page);
    }

    public ApiResponse changeActive(Long id, boolean active) {
        try {
            Purchase purchase = purchaseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("brand", "id", id));
            purchase.setActive(active);
            purchaseRepository.save(purchase);
            return new ApiResponse(active ? "Activated" : "Blocked", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }

    public ApiResponseModel getPurchaseCol(Long id) {
        try {
            return new ApiResponseModel(true, "Ok", purchaseRepository.findPurchaseById(id).get());
        } catch (Exception e) {
            return new ApiResponseModel(false, "Error", TrayIcon.MessageType.ERROR);
        }
    }
}

