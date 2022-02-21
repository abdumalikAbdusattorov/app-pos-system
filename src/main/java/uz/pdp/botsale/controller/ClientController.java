package uz.pdp.botsale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ApiResponseModel;
import uz.pdp.botsale.payload.ReqClient;
import uz.pdp.botsale.service.ClientService;
import uz.pdp.botsale.service.ExcelService;
import uz.pdp.botsale.utils.AppConstants;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    ExcelService excelService;

    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    @PostMapping
    public HttpEntity<?> saveOrEdit(@RequestBody ReqClient reqClient) {
        ApiResponse response = clientService.saveOrEdit(reqClient);
        return ResponseEntity.status(response.isSuccess() ? response.getMessage().equals("Saved") ? HttpStatus.CREATED : HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR','ROLE_SELLER')")
    @GetMapping
    public HttpEntity<?> pageable(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                  @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                  @RequestParam(value = "search", defaultValue = "all") String search) {
        return ResponseEntity.ok(clientService.pageable(page, size, search));
    }

/*    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR','ROLE_SELLER')")
    @GetMapping("/{id}")
    public ApiResponse changeActive(@PathVariable Long id, @RequestParam boolean active) {
        return clientService.changeActive(id, active);
    }*/

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @GetMapping("/excel")
    public HttpEntity<?> getExcel() {
        return excelService.getClient();
    }

/*    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    @DeleteMapping("/{id}")
    public ApiResponse removeController(@PathVariable Long id) {
        return clientService.removeClient(id);
    }*/
}

