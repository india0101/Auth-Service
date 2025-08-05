package com.sharamikah.Auth_Service.Repository;


import com.sharamikah.Auth_Service.domain.entity.AppUser;
import com.sharamikah.Auth_Service.domain.entity.SecurityEventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SecurityEventLogRepository extends JpaRepository<SecurityEventLog, Long> {

    @Transactional(readOnly = true)
    List<SecurityEventLog> findByUser(AppUser user);
}

