package com.tes.ebayuserauthservice.service;

import com.tes.ebayuserauthservice.model.AuthCode;
import com.tes.ebayuserauthservice.repository.AccessTokenRepository;
import com.tes.ebayuserauthservice.token.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EbayAccessTokenService extends GenericAccessTokenService {

    @Autowired
    public EbayAccessTokenService(
            AccessTokenRepository repository,
            AccessTokenRepository tokenRepository,
            TokenManager<AuthCode> tokenManager
    ) {
        super(
                repository,
                tokenRepository,
                tokenManager
        );
    }
}
