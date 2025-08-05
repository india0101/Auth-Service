package com.sharamikah.Auth_Service.Repository;


import com.sharamikah.Auth_Service.domain.entity.AppUser;
import com.sharamikah.Auth_Service.domain.entity.UserRoleAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleAssignmentRepository extends JpaRepository<UserRoleAssignment, Long> {

    @Transactional(readOnly = true)
    List<UserRoleAssignment> findByUser(AppUser user);

    @Transactional(readOnly = true)
    Optional<UserRoleAssignment> findByUserAndRoleId(AppUser user, String roleId);

    @Modifying
    @Transactional
    void deleteByUser(AppUser user);
}
