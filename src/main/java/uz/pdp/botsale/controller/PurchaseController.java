package uz.pdp.botsale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ApiResponseModel;
import uz.pdp.botsale.payload.ReqPurchase;
import uz.pdp.botsale.service.PurchaseService;
import uz.pdp.botsale.utils.AppConstants;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_WAREHOUSE_MANAGER')")
    @PostMapping
    public HttpEntity<?> saveOrEdit(@RequestBody ReqPurchase reqPurchase) {
        ApiResponse apiResponse = purchaseService.saveOrEdit(reqPurchase);
        return ResponseEntity.status(apiResponse.isSuccess() ? apiResponse.getMessage().equals("Saved") ? HttpStatus.CREATED : HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR','ROLE_WAREHOUSE_MANAGER')")
    @GetMapping
    public HttpEntity<?> pageable(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                  @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        return ResponseEntity.ok(purchaseService.pageable(page, size));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_WAREHOUSE_MANAGER')")
    @GetMapping("/{id}")
    public ApiResponse changeActive(@PathVariable Long id, @PathVariable boolean active) {
        return purchaseService.changeActive(id, active);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR','ROLE_WAREHOUSE_MANAGER')")
    @PutMapping("/{id}")
    public HttpEntity<?> getPurchaseCol(@PathVariable Long id) {
        ApiResponseModel response = purchaseService.getPurchaseCol(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }
}
