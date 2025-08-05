package com.sharamikah.Auth_Service.Repository;


import com.sharamikah.Auth_Service.domain.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    @Transactional(readOnly = true)
    Optional<AppUser > findByEmail(@Param("email") String email);

    @Transactional(readOnly = true)
    boolean existsByEmail(@Param("email") String email);
}
