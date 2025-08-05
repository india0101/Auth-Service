package com.sharamikah.Auth_Service.Repository;


import com.sharamikah.Auth_Service.domain.entity.AppUser;
import com.sharamikah.Auth_Service.domain.entity.ConsentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ConsentLogRepository extends JpaRepository<ConsentLog, Long> {

    @Transactional(readOnly = true)
    List<ConsentLog> findByUser(AppUser user);
}

