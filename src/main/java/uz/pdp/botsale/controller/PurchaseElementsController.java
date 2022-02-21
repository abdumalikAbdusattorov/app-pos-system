package uz.pdp.botsale.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ApiResponseModel;
import uz.pdp.botsale.payload.ReqPurchaseElements;
import uz.pdp.botsale.service.ExcelService;
import uz.pdp.botsale.service.PurchaseElementsService;
import uz.pdp.botsale.utils.AppConstants;

@RestController
@RequestMapping("/api/purchaseElements")
public class PurchaseElementsController {

    @Autowired
    PurchaseElementsService purchaseElementsService;

    @Autowired
    ExcelService excelService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_WAREHOUSE_MANAGER')")
    @PostMapping
    public HttpEntity<?> saveOrEdit(@RequestBody ReqPurchaseElements reqPurchaseElements) {
        ApiResponse response = purchaseElementsService.saveOrEdit(reqPurchaseElements);
        return ResponseEntity.status(response.isSuccess() ? response.getMessage().equals("Saved") ? HttpStatus.CREATED : HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR','ROLE_WAREHOUSE_MANAGER')")
    @GetMapping
    public HttpEntity<?> pageable(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                  @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        return ResponseEntity.ok(purchaseElementsService.pageable(page, size));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_WAREHOUSE_MANAGER')")
    @GetMapping("/{id}")
    public ApiResponse changeActive(@PathVariable Long id, @PathVariable boolean active) {
        return purchaseElementsService.changeActive(id, active);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR','ROLE_WAREHOUSE_MANAGER')")
    @GetMapping("/excel")
    public HttpEntity<?> getExcel() {
        return excelService.getPurchaseElements();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR','ROLE_WAREHOUSE_MANAGER')")
    @PutMapping("/{id}")
    public HttpEntity<?> getPurElementCol(@PathVariable Long id) {
        ApiResponseModel response = purchaseElementsService.getPurElementCol(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR','ROLE_WAREHOUSE_MANAGER')")
    @DeleteMapping("/{id}")
    public ApiResponse removeController(@PathVariable Long id) {
        return purchaseElementsService.removePurchaseElements(id);
    }
}

