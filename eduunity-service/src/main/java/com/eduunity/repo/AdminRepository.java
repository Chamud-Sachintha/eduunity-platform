package com.eduunity.repo;

import com.eduunity.dto.admin.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminUser, Integer> {

    Optional<AdminUser> findByUserName(String username);
}
