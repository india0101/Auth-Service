package com.sharamikah.Auth_Service.Service;


import com.sharamikah.Auth_Service.DTO.ConsentLogDTO;
import java.util.List;

public interface ConsentLogService {

    ConsentLogDTO logConsent(ConsentLogDTO consentLog);

    List<ConsentLogDTO> getConsentLogs(Long userId);
}

