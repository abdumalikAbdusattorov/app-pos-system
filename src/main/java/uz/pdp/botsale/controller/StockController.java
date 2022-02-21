package uz.pdp.botsale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ApiResponseModel;
import uz.pdp.botsale.payload.ReqStock;
import uz.pdp.botsale.service.StockService;
import uz.pdp.botsale.utils.AppConstants;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    StockService stockService;

    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR')")
    @PostMapping
    public HttpEntity<?> saveOrEdit(@RequestBody ReqStock reqStock) {
        ApiResponse response = stockService.saveOrEdit(reqStock);
        return ResponseEntity.status(response.isSuccess() ? response.getMessage().equals("Saved") ? HttpStatus.CREATED : HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @GetMapping
    public HttpEntity<?> pageable(@RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                  @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer size,
                                  @RequestParam(name = "search", defaultValue = "all") String search) {
        return ResponseEntity.ok(stockService.getPageable(page, size, search));
    }

    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR')")
    @GetMapping("/{id}")
    public ApiResponse changeActive(@PathVariable Integer id, @RequestParam boolean active) {
        return stockService.changeActive(id, active);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> getStockCol(@PathVariable Integer id) {
        ApiResponseModel responseModel = stockService.getStockCol(id);
        return ResponseEntity.status(responseModel.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(responseModel);
    }
}
