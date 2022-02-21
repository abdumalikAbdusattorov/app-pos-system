package uz.pdp.botsale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.botsale.payload.ApiResponseModel;
import uz.pdp.botsale.payload.ReqSell;
import uz.pdp.botsale.service.ExcelService;
import uz.pdp.botsale.service.SellService;
import uz.pdp.botsale.utils.AppConstants;

@RestController
@RequestMapping("/api/sell")
public class SellController {

    @Autowired
    SellService sellService;

    @Autowired
    ExcelService excelService;

    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    @PostMapping
    public HttpEntity<?> saveOrEdit(@RequestBody ReqSell reqSell) {
        ApiResponseModel response = sellService.saveOrEdit(reqSell);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(response);
    }

    @Scheduled(cron = "* * */1 * * *")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SELLER','ROLE_DIRECTOR')")
    @GetMapping
    public HttpEntity<?> pageable(@RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                  @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        return ResponseEntity.ok(sellService.pageable(page, size));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SELLER','ROLE_DIRECTOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> getSellCol(@PathVariable Long id) {
        ApiResponseModel response = sellService.getSellCol(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }
}
