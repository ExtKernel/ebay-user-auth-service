package com.tes.ebayuserauthservice.service;

import com.tes.ebayuserauthservice.exception.AccessTokenIsNullException;
import com.tes.ebayuserauthservice.exception.NoRecordOfAccessTokenException;
import com.tes.ebayuserauthservice.model.AccessToken;
import com.tes.ebayuserauthservice.model.AuthCode;
import com.tes.ebayuserauthservice.model.RefreshToken;
import com.tes.ebayuserauthservice.repository.AccessTokenRepository;
import com.tes.ebayuserauthservice.token.TokenManager;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A generic class that implements the generic behaviour
 * of services that manage {@link AccessToken} objects.
 */
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
    public AccessToken findLatest() throws NoRecordOfAccessTokenException {
        try {
            return tokenRepository.findFirstByOrderByCreationDateDesc()
                    .orElseThrow(() -> new AccessTokenIsNullException("The access token is null"));
        } catch (AccessTokenIsNullException exception) {
            throw new NoRecordOfAccessTokenException(
                    "There is no record of access tokens in the database: " + exception.getMessage(),
                    exception
            );
        }
    }
}
