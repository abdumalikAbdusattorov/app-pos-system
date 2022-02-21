package uz.pdp.botsale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ReqSellDetails;
import uz.pdp.botsale.service.ExcelService;
import uz.pdp.botsale.service.SellDetailsService;
import uz.pdp.botsale.utils.AppConstants;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/sellDetails")
public class SellDetailsController {

    @Autowired
    SellDetailsService sellDetailsService;

    @Autowired
    ExcelService excelService;

/*    @PostMapping
    public HttpEntity<?> saveOrEdit(@RequestBody ReqSellDetails reqSellDetails) {
        ApiResponse response = sellDetailsService.saveOrEdit(reqSellDetails);
        return ResponseEntity.status(response.isSuccess() ? response.getMessage().equals("Saved") ? HttpStatus.CREATED : HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(response);
    }*/

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SELLER','ROLE_DIRECTOR')")
    @GetMapping
    public HttpEntity<?> pageable(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                  @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        return ResponseEntity.ok(sellDetailsService.pageable(page, size));
    }

/*    @GetMapping("/{id}")
    public ApiResponse changeActive(@PathVariable Long id, @RequestParam boolean active) {
        return sellDetailsService.changeActive(id, active);
    }*/

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SELLER','ROLE_DIRECTOR')")
    @GetMapping("/excel")
    public HttpEntity<?> getExcel(@RequestParam(name = "startDate", defaultValue = AppConstants.BEGIN_DATE) Timestamp startDate,
                                  @RequestParam(name = "endDate", defaultValue = AppConstants.END_DATE) Timestamp endDate) {
        return excelService.getSellDetails(startDate,endDate);
    }

/*    @DeleteMapping("/{id}")
    public ApiResponse removeController(@PathVariable Long id) {
        return sellDetailsService.removePurchaseElements(id);
    }*/
}
