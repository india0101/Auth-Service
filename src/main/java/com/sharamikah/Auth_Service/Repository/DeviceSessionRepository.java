package com.sharamikah.Auth_Service.Repository;


import com.sharamikah.Auth_Service.domain.entity.AppUser;
import com.sharamikah.Auth_Service.domain.entity.DeviceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DeviceSessionRepository extends JpaRepository<DeviceSession, Long> {

    @Transactional(readOnly = true)
    List<DeviceSession> findByUser(AppUser user);

    @Modifying
    @Transactional
    void deleteByUser(AppUser user);
}
