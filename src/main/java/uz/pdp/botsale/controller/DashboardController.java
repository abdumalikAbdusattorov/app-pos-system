package uz.pdp.botsale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.botsale.entity.enums.DashboardEnumType;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.service.DashboardService;
import uz.pdp.botsale.utils.AppConstants;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
//    @GetMapping
//    public HttpEntity<?> getOneMonth(){
//        return ResponseEntity.ok(dashboardService.getAllInformation());
//    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @GetMapping("/{type}")
    public HttpEntity<?> getDashboard(@PathVariable DashboardEnumType type, @RequestParam(name = "startDate",defaultValue = AppConstants.BEGIN_DATE) Timestamp startDate, @RequestParam(name = "endDate",defaultValue = AppConstants.END_DATE) Timestamp endDate) {
        return ResponseEntity.ok(dashboardService.getInformation(type,startDate,endDate));
    }
}
