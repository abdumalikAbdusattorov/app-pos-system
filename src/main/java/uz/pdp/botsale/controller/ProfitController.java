package uz.pdp.botsale.controller;

import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.service.ProfitService;
import uz.pdp.botsale.utils.AppConstants;

import java.sql.Timestamp;

@RestController
public class ProfitController {
   
   @Autowired
   ProfitService profitService;
   
   
//   @GetMapping
//   public ApiResponse getProfit(@RequestParam(name = "startDate", defaultValue = AppConstants.BEGIN_DATE) Timestamp startDate,
//                                @RequestParam(name = "endDate",defaultValue = AppConstants.END_DATE) Timestamp endDate){
//      return ResponseEntity.ok(profitService.profit(startDate,endDate));
//   }
}
