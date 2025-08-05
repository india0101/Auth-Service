package com.sharamikah.Auth_Service.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Payload to initiate a new device session for a user.
 */
@Data
public class CreateDeviceSessionRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Device name cannot be empty")
    @Size(max = 100, message = "Device name must be 100 characters or fewer")
    private String deviceName;

    @NotBlank(message = "IP address is required")
    @Pattern(
            regexp =
                    // IPv4 or IPv6 loose match
                    "^(?:\\d{1,3}\\.){3}\\d{1,3}$|^([0-9a-fA-F]{0,4}:){1,7}[0-9a-fA-F]{0,4}$",
            message = "Must be a valid IP address"
    )
    private String ipAddress;

    @NotBlank(message = "User agent is required")
    @Size(max = 512, message = "User agent string too long")
    private String userAgent;
}

