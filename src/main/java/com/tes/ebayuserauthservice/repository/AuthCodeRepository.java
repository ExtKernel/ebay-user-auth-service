package com.tes.ebayuserauthservice.repository;

import com.tes.ebayuserauthservice.model.AuthCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthCodeRepository
        extends JpaRepository<AuthCode, Long> {
    Optional<AuthCode> findFirstByOrderByCreationDateDesc();
}
