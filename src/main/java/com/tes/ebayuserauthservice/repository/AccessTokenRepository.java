package com.tes.ebayuserauthservice.repository;

import com.tes.ebayuserauthservice.model.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
    Optional<AccessToken> findFirstByOrderByCreationDateDesc();
}
