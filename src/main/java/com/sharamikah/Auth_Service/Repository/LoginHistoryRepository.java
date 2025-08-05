package com.sharamikah.Auth_Service.Repository;


import com.sharamikah.Auth_Service.domain.entity.AppUser;
import com.sharamikah.Auth_Service.domain.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    @Transactional(readOnly = true)
    List<LoginHistory> findByUser(AppUser user);
}

