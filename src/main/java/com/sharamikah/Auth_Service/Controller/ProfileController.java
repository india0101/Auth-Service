package com.sharamikah.Auth_Service.Controller;


import com.sharamikah.Auth_Service.Repository.UserRepository;
import com.sharamikah.Auth_Service.domain.entity.AppUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class ProfileController {

    private final UserRepository userRepo;

    public ProfileController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AppUser> getCurrentUser(
            @AuthenticationPrincipal AppUser currentUser
    ) {
        // Or load fresh data from DB:
        AppUser upToDate = userRepo.findById(currentUser.getId()).orElse(currentUser);
        return ResponseEntity.ok(upToDate);
    }
}
