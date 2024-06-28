package com.tes.ebayuserauthservice.service;

import com.tes.ebayuserauthservice.exception.NoRecordOfRefreshTokenException;
import com.tes.ebayuserauthservice.exception.RefreshTokenIsNullException;
import com.tes.ebayuserauthservice.model.AuthModel;
import com.tes.ebayuserauthservice.model.RefreshToken;
import com.tes.ebayuserauthservice.repository.RefreshTokenRepository;
import com.tes.ebayuserauthservice.token.TokenManager;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A generic class that implements the generic behaviour
 * of services that manage {@link RefreshToken} objects.
 *
 * @param <T> the object, which is supposed to be used for retrieval of refresh tokens.
 */
public abstract class GenericRefreshTokenService<T extends AuthModel>
        extends GenericCrudService<RefreshToken, Long>
        implements TokenService<RefreshToken, T> {
    RefreshTokenRepository tokenRepository;
    TokenManager<T> tokenManager;

    public GenericRefreshTokenService(
            JpaRepository<RefreshToken, Long> repository,
            RefreshTokenRepository tokenRepository,
            TokenManager<T> tokenManager
    ) {
        super(repository);
        this.tokenRepository = tokenRepository;
        this.tokenManager = tokenManager;
    }

    @Override
    public RefreshToken generate(T authModel) {
        return tokenManager.getRefreshToken(authModel);
    }

    @Override
    public RefreshToken findLatest() throws NoRecordOfRefreshTokenException {
        try {
            return tokenRepository.findFirstByOrderByCreationDateDesc()
                    .orElseThrow(() -> new RefreshTokenIsNullException("The refresh token is null"));
        } catch (RefreshTokenIsNullException exception) {
            throw new NoRecordOfRefreshTokenException(
                    "There is no record of refresh tokens in the database: " + exception.getMessage(),
                    exception
            );
        }
    }
}
