package uz.pdp.botsale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.botsale.entity.User;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ApiResponseModel;
import uz.pdp.botsale.payload.ReqSignUp;
import uz.pdp.botsale.repository.UserRepository;
import uz.pdp.botsale.security.AuthService;
import uz.pdp.botsale.security.CurrentUser;
import uz.pdp.botsale.service.UserService;
import uz.pdp.botsale.utils.AppConstants;

import java.awt.*;
import java.sql.Timestamp;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/me")
    public HttpEntity<?> getUser(@CurrentUser User user) {
        return ResponseEntity.ok(new ApiResponseModel(user != null, user != null ? "Mana user" : "Error", user));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @PostMapping
    public HttpEntity<?> createUser(@RequestBody ReqSignUp reqUser) {
        ApiResponse response = userService.addUser(reqUser);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(response.getMessage(), true, TrayIcon.MessageType.INFO));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(response.getMessage(), false, TrayIcon.MessageType.ERROR));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @GetMapping("/byPageable")
    public HttpEntity<?> getByPageable(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        return ResponseEntity.ok(userService.getByPageable(page, size));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @GetMapping
    public HttpEntity<?> getUsers() {
        return ResponseEntity.ok(new ApiResponseModel(true, "All users", userService.getUsers()));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @PutMapping
    public HttpEntity<?> getDocumentation(@RequestParam UUID id, @RequestParam(name = "startDate",defaultValue = AppConstants.BEGIN_DATE) Timestamp startDate, @RequestParam(name = "entDate",defaultValue = AppConstants.END_DATE) Timestamp endDate) {
        ApiResponseModel apiResponseModel = userService.getDocumentation(id,startDate,endDate);
        return ResponseEntity.status(apiResponseModel.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponseModel);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @GetMapping("/changeEnable")
    public HttpEntity<?> changeEnabled(@RequestParam UUID id, @RequestParam boolean status) {
        return ResponseEntity.ok(userService.changeEnabled(id, status));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> changeEnabled(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.removeEmployee(id));
    }

    @GetMapping("/all")
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }
}
