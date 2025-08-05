package com.sharamikah.Auth_Service.Service;


import com.sharamikah.Auth_Service.DTO.UserRoleAssignmentDTO;

import java.util.List;

public interface UserRoleAssignmentService {

    /** Assign a role to a user */
    UserRoleAssignmentDTO assignRole(Long userId, String roleId);

    /** Remove a user’s role */
    void removeRole(Long userId, String roleId);

    /** List all roles for a user */
    List<UserRoleAssignmentDTO> getUserRoles(Long userId);
}

