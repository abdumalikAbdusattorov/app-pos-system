package uz.pdp.botsale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ReqCompany;
import uz.pdp.botsale.service.CompanyService;
import uz.pdp.botsale.service.ExcelService;
import uz.pdp.botsale.utils.AppConstants;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    ExcelService excelService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public HttpEntity<?> saveOrEdit(@RequestBody ReqCompany reqCompany) {
        ApiResponse response = companyService.saveOrEdit(reqCompany);
        return ResponseEntity.status(response.isSuccess() ? response.getMessage().equals("Saved") ? HttpStatus.CREATED : HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @GetMapping
    public HttpEntity<?> pageable(@RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                  @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                  @RequestParam(name = "search", defaultValue = "all") String search) {
        return ResponseEntity.ok(companyService.pageable(page, size, search));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @GetMapping("/{id}")
    public ApiResponse changeActive(@PathVariable Long id, @RequestParam boolean active) {
        return companyService.changeActive(id, active);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @GetMapping("/excel")
    public HttpEntity<?> getExcel() {
        return excelService.getCompany();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @DeleteMapping("/{id}")
    public ApiResponse removeCompany(@PathVariable Long id) {
        return companyService.removeCompany(id);
    }
}
