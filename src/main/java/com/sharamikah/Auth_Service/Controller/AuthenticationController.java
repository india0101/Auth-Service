package com.sharamikah.Auth_Service.Controller;


import com.sharamikah.Auth_Service.DTO.AuthenticationResponseDTO;
import com.sharamikah.Auth_Service.DTO.SignupRequestDTO;
import com.sharamikah.Auth_Service.Service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Signup, login, and refresh endpoints")
@Validated
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Sign up a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponseDTO> signup(
            @Parameter(description = "Signup details")
            @Valid @RequestBody SignupRequestDTO signupRequest
    ) {
        AuthenticationResponseDTO response = authService.registerAndGenerateTokens(signupRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(
            @Valid @RequestBody LoginRequestDto loginRequest
    ) {
        AuthenticationResponseDTO response = authService.authenticateAndGenerateTokens(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponseDTO> refreshToken(
            @Valid @RequestBody RefreshTokenDto refreshRequest
    ) {
        AuthenticationResponseDTO response = authService.refreshAccessToken(refreshRequest);
        return ResponseEntity.ok(response);
    }
}
