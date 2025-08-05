package com.sharamikah.Auth_Service.Service;



import com.sharamikah.Auth_Service.DTO.LoginHistoryDTO;
import java.util.List;

public interface LoginHistoryService {

    /** Persist a login attempt (success or failure) */
    LoginHistoryDTO recordLogin(LoginHistoryDTO loginHistory);

    /** Retrieve all login events for a user */
    List<LoginHistoryDTO> getLoginHistory(Long userId);
}

