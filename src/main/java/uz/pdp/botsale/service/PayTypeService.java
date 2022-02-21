/*
package uz.pdp.botsale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.botsale.entity.PayType;
import uz.pdp.botsale.exception.ResourceNotFoundException;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ReqPayType;
import uz.pdp.botsale.payload.ResPageable;
import uz.pdp.botsale.repository.PayTypeRepository;
import uz.pdp.botsale.utils.CommonUtils;

@Service
public class PayTypeService {
   @Autowired
   PayTypeRepository paytypeRepository;
   
   public ApiResponse saveOrEdit(ReqPayType reqPayType) {
      ApiResponse response = new ApiResponse();
      try {
         if (!reqPayType.getName().equals(paytypeRepository.findByName(reqPayType.getName())) || reqPayType.getId() != null) {
            response.setMessage("Saved");
            response.setSuccess(true);
            PayType payType = new PayType();
            if (reqPayType.getId() != null) {
               payType = paytypeRepository.findById(reqPayType.getId()).orElseThrow(() -> new ResourceNotFoundException("PayType", "Id", reqPayType.getId()));
               response.setMessage("Edited");
            }
            payType.setName(reqPayType.getName());
            payType.setCardCompany(reqPayType.getCardCompany());
            payType.setCardNumber(reqPayType.getCardNumber());
            payType.setCashMoney(reqPayType.getCashMoney());
            paytypeRepository.save(payType);
         } else if (reqPayType.getName().equals(paytypeRepository.findByName(reqPayType.getName()))) {
            response.setMessage("This message Already exists!");
            response.setSuccess(false);
         }
      } catch (Exception e) {
         response.setMessage("Error");
         response.setSuccess(false);
      }
      return response;
   }
   
   public ResPageable pageable(Integer page, Integer size, String search) {
      Page<PayType> payTypePage = paytypeRepository.findAll(CommonUtils.getPageableById(page, size));
      if (!search.equals("all")){
         payTypePage = paytypeRepository.findAllByNameStartingWithIgnoreCase(search, CommonUtils.getPageableById(page, size));
      }
      return new ResPageable(payTypePage.getContent(), payTypePage.getTotalElements(), page);
   }
   
   public ApiResponse changeActive(Long id, boolean active){
      try{
         PayType payType = paytypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("payType","id", id));
         return new ApiResponse(active ? "Active" : "Blocked", true);
      }catch (Exception e){
         return new ApiResponse("Error", false);
      }
   }
   
   public ApiResponse removePayType(Long id) {
      try {
         paytypeRepository.deleteById(id);
         return new ApiResponse("Deleted",true);
      } catch (Exception e){
         return new ApiResponse("Error",false);
      }
   }
}
*/
