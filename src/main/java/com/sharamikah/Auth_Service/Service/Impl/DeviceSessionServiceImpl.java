package com.sharamikah.Auth_Service.Service.Impl;


import com.sharamikah.Auth_Service.DTO.CreateDeviceSessionRequest;
import com.sharamikah.Auth_Service.Service.DeviceSessionService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Default implementation that reads standard headers and fallback values.
 */
@Service
public class DeviceSessionServiceImpl implements DeviceSessionService {

    private static final String HEADER_X_FORWARDED_FOR = "X-Forwarded-For";
    private static final String HEADER_USER_AGENT      = "User-Agent";
    private static final String HEADER_DEVICE_NAME     = "X-Device-Name";
    private static final String UNKNOWN               = "UNKNOWN";

    @Override
    public CreateDeviceSessionRequest mapFrom(HttpServletRequest request) {
        String ip = extractClientIp(request);
        String ua = Optional.ofNullable(request.getHeader(HEADER_USER_AGENT))
                .filter(h -> !h.isBlank())
                .orElse(UNKNOWN);
        String deviceName = Optional.ofNullable(request.getHeader(HEADER_DEVICE_NAME))
                .filter(h -> !h.isBlank())
                .orElse(UNKNOWN);

        return new CreateDeviceSessionRequest(ip, ua, deviceName);
    }

    /**
     * Uses X-Forwarded-For (if present) or falls back to servlet remote address.
     */
    private String extractClientIp(HttpServletRequest req) {
        String xff = req.getHeader(HEADER_X_FORWARDED_FOR);
        if (xff != null && !xff.isBlank()) {
            // first IP in list is the original client
            return xff.split(",")[0].trim();
        }
        return Optional.ofNullable(req.getRemoteAddr())
                .orElse(UNKNOWN);
    }
}
