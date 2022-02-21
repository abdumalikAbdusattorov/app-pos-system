package uz.pdp.botsale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.botsale.entity.Profit;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ApiResponseModel;
import uz.pdp.botsale.repository.ProfitRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ProfitService {

   @Autowired
   ProfitRepository profitRepository;

   public ApiResponseModel profit(Timestamp startDate, Timestamp endDate) {
      ApiResponse response = new ApiResponse();
      try {
         response.setMessage("Ok ^_^");
         response.setSuccess(true);
      } catch (Exception e) {
         response.setMessage("Error");
         response.setSuccess(false);
      }
      return null;
   }
}
