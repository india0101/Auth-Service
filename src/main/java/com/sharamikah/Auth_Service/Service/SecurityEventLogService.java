package com.sharamikah.Auth_Service.Service;


import com.sharamikah.Auth_Service.DTO.SecurityEventLogDTO;
import java.util.List;

public interface SecurityEventLogService {

    SecurityEventLogDTO logEvent(SecurityEventLogDTO eventLog);

    List<SecurityEventLogDTO> getSecurityEvents(Long userId);
}

