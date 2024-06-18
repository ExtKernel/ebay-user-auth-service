package com.tes.ebayuserauthservice.service;

import com.tes.ebayuserauthservice.model.AuthCode;
import com.tes.ebayuserauthservice.model.RefreshToken;
import com.tes.ebayuserauthservice.repository.RefreshTokenRepository;
import com.tes.ebayuserauthservice.token.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class EbayRefreshTokenService extends GenericRefreshTokenService<AuthCode> {

    @Autowired
    public EbayRefreshTokenService(
            JpaRepository<RefreshToken, Long> repository,
            RefreshTokenRepository tokenRepository,
            TokenManager<AuthCode> tokenManager
    ) {
        super(
                repository,
                tokenRepository,
                tokenManager
        );
    }
}
