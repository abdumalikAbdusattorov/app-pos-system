package uz.pdp.botsale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ApiResponseModel;
import uz.pdp.botsale.payload.ReqBrand;
import uz.pdp.botsale.service.BrandService;
import uz.pdp.botsale.service.ExcelService;
import uz.pdp.botsale.utils.AppConstants;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @Autowired
    ExcelService excelService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_WAREHOUSE_MANAGER')")
    @PostMapping
    public HttpEntity<?> saveOrEdit(@RequestBody ReqBrand reqBrand) {
        ApiResponse response = brandService.saveOrEdit(reqBrand);
        return ResponseEntity.status(response.isSuccess() ? response.getMessage().equals("Saved") ? HttpStatus.CREATED : HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR','ROLE_SELLER','ROLE_WAREHOUSE_MANAGER')")
    @GetMapping
    public HttpEntity<?> pageable(@RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                  @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                  @RequestParam(name = "search", defaultValue = "all") String search) {
        return ResponseEntity.ok(brandService.pageable(page, size, search));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse changeActive(@PathVariable Integer id, @RequestParam boolean active) {
        return brandService.changeActive(id, active);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @GetMapping("/excel")
    public HttpEntity<?> getExcel() {
        return excelService.getBrand();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR','ROLE_SELLER','ROLE_WAREHOUSE_MANAGER')")
    @PutMapping("/{id}")
    public HttpEntity<?> getBrandCol(@PathVariable Integer id) {
        ApiResponseModel response = brandService.getBrandCol(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse removeBrand(@PathVariable Integer id) {
        return brandService.removeBrand(id);
    }
}
