package uz.pdp.botsale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ApiResponseModel;
import uz.pdp.botsale.payload.ReqProduct;
import uz.pdp.botsale.service.ExcelService;
import uz.pdp.botsale.service.ProductService;
import uz.pdp.botsale.utils.AppConstants;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ExcelService excelService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_WAREHOUSE_MANAGER')")
    @PostMapping
    public HttpEntity<?> saveOrEdit(@RequestBody ReqProduct reqProduct) {
        ApiResponse response = productService.saveOrEdit(reqProduct);
        return ResponseEntity.status(response.isSuccess() ? response.getMessage().equals("Saved") ? HttpStatus.CREATED : HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_WAREHOUSE_MANAGER')")
    @GetMapping
    public HttpEntity<?> pageable(@RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                  @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                  @RequestParam(name = "search", defaultValue = "all") String search) {
        return ResponseEntity.ok(productService.pageable(page, size, search));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_WAREHOUSE_MANAGER')")
    @GetMapping("/{id}")
    public ApiResponse changeActive(@PathVariable Long id, @RequestParam boolean active) {
        return productService.changeActive(id, active);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR','ROLE_WAREHOUSE_MANAGER')")
    @GetMapping("/excel")
    public HttpEntity<?> getExcel() {
        return excelService.getProduct();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR','ROLE_WAREHOUSE_MANAGER')")
    @PutMapping("/{code}")
    public HttpEntity<?> getByCollection(@PathVariable String code) {
        ApiResponseModel response = productService.getProductCol(code);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @DeleteMapping("/{id}")
    public ApiResponse removeController(@PathVariable Long id) {
        return productService.removeProduct(id);
    }
}
