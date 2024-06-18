package com.tes.ebayuserauthservice.service;

import com.tes.ebayuserauthservice.exception.NoRecordOfRefreshTokenException;
import com.tes.ebayuserauthservice.exception.RefreshTokenIsNullException;
import com.tes.ebayuserauthservice.model.AccessToken;
import com.tes.ebayuserauthservice.model.AuthCode;
import com.tes.ebayuserauthservice.model.RefreshToken;
import com.tes.ebayuserauthservice.repository.AccessTokenRepository;
import com.tes.ebayuserauthservice.token.TokenManager;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class GenericAccessTokenService
        extends GenericCrudService<AccessToken, Long>
        implements TokenService<AccessToken, RefreshToken> {
    AccessTokenRepository tokenRepository;
    TokenManager<AuthCode> tokenManager;

    public GenericAccessTokenService(
            JpaRepository<AccessToken, Long> repository,
            AccessTokenRepository tokenRepository,
            TokenManager<AuthCode> tokenManager
    ) {
        super(repository);
        this.tokenRepository = tokenRepository;
        this.tokenManager = tokenManager;
    }

    @Override
    public AccessToken generate(RefreshToken refreshToken) {
        return tokenManager.getAccessToken(refreshToken);
    }

    @Override
    public AccessToken findLatest() throws NoRecordOfRefreshTokenException {
        try {
            return tokenRepository.findFirstByOrderByCreationDateDesc()
                    .orElseThrow(() -> new RefreshTokenIsNullException("The refresh token is null"));
        } catch (RefreshTokenIsNullException exception) {
            throw new NoRecordOfRefreshTokenException(
                    "There is no record of access tokens in the database: " + exception.getMessage(),
                    exception
            );
        }
    }
}
