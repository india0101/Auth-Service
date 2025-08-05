package com.sharamikah.Auth_Service.Service;


import com.sharamikah.Auth_Service.DTO.CreateDeviceSessionRequest;
import com.sharamikah.Auth_Service.DTO.DeviceSessionDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface DeviceSessionService {

    DeviceSessionDTO createSession(CreateDeviceSessionRequest request);

    List<DeviceSessionDTO> getSessionsForUser(Long userId);

    void revokeSession(Long sessionId);

    void revokeAllSessions(Long userId);

    /**
     * Builds a CreateDeviceSessionRequest from the incoming servlet request.
     *
     * @param request the HTTP servlet request
     * @return populated DTO with IP address, UA, and device name
     */
    CreateDeviceSessionRequest mapFrom(HttpServletRequest request);
}

