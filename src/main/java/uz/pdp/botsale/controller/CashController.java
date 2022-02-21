package uz.pdp.botsale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ApiResponseModel;
import uz.pdp.botsale.payload.ReqCash;
import uz.pdp.botsale.service.CashService;
import uz.pdp.botsale.service.ExcelService;
import uz.pdp.botsale.utils.AppConstants;

@RestController
@RequestMapping("/api/cash")
public class CashController {

    @Autowired
    CashService cashService;

    @Autowired
    ExcelService excelService;

    @Scheduled(initialDelay = 5000L, cron = "* * * * * *")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public HttpEntity<?> saveOrEdit(@RequestBody ReqCash reqCash) {
        ApiResponse response = cashService.saveOrEdit(reqCash);
        return ResponseEntity.status(response.isSuccess() ? response.getMessage().equals("saved") ? HttpStatus.CREATED : HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @GetMapping
    public HttpEntity<?> pageable(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                  @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        return ResponseEntity.ok(cashService.pageable(page, size));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse changeActive(@PathVariable Integer id, @RequestParam boolean active) {
        return cashService.changeActive(id, active);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @GetMapping("/excel")
    public HttpEntity<?> getExcel() {
        return excelService.getCash();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> getAllDetails(@PathVariable Integer id) {
        ApiResponseModel response = cashService.cashCollection(id);
        return ResponseEntity.status(response.getMessage().equals("Ok") ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @DeleteMapping("{id}")
    public ApiResponse removeController(@PathVariable Integer id) {
        return cashService.removeCash(id);
    }
}
