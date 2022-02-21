package uz.pdp.botsale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ApiResponseModel;
import uz.pdp.botsale.payload.ReqMarket;
import uz.pdp.botsale.service.ExcelService;
import uz.pdp.botsale.service.MarketService;
import uz.pdp.botsale.utils.AppConstants;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    @Autowired
    MarketService marketService;

    @Autowired
    ExcelService excelService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @PostMapping
    public HttpEntity<?> saveOrEdit(@RequestBody ReqMarket reqMarket) {
        ApiResponse response = marketService.saveOrEdit(reqMarket);
        return ResponseEntity.status(response.isSuccess() ? response.getMessage().equals("Saved") ? HttpStatus.CREATED : HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR','ROLE_WAREHOUSE_MANAGER')")
    @GetMapping
    public HttpEntity<?> pageable(@RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                  @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                  @RequestParam(name = "search", defaultValue = "all") String search) {
        return ResponseEntity.ok(marketService.pageable(page, size, search));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @GetMapping("/{id}")
    public ApiResponse changeActive(@PathVariable Integer id, @RequestParam boolean active) {
        return marketService.changeActive(id, active);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @GetMapping("/excel")
    public HttpEntity<?> getExcel() {
        return excelService.getMarket();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR','ROLE_WAREHOUSE_MANAGER')")
    @PutMapping("/{id}")
    public HttpEntity<?> getMarketCol(@PathVariable Integer id) {
        ApiResponseModel response = marketService.getMarketCol(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @DeleteMapping("/{id}")
    public ApiResponse removeMarket(@PathVariable Integer id) {
        return marketService.removeMarket(id);
    }
}
