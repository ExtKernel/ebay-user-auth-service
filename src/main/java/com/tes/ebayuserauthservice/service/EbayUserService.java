package com.tes.ebayuserauthservice.service;

import com.tes.ebayuserauthservice.exception.NoRecordOfRefreshTokenException;
import com.tes.ebayuserauthservice.exception.RefreshTokenIsNullException;
import com.tes.ebayuserauthservice.model.AccessToken;
import com.tes.ebayuserauthservice.model.AuthCode;
import com.tes.ebayuserauthservice.model.EbayUser;
import com.tes.ebayuserauthservice.model.RefreshToken;
import com.tes.ebayuserauthservice.token.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EbayUserService
        extends GenericCrudService<EbayUser, Long>
        implements Oauth2UserService<EbayUser, Long> {
    AuthCodeService authCodeService;
    TokenManager<AuthCode> tokenManager;
    TokenService<RefreshToken, AuthCode> refreshTokenService;
    TokenService<AccessToken, RefreshToken> accessTokenService;

    @Autowired
    public EbayUserService(
            JpaRepository<EbayUser, Long> repository,
            AuthCodeService authCodeService,
            TokenManager<AuthCode> tokenManager,
            TokenService<RefreshToken, AuthCode> refreshTokenService,
            TokenService<AccessToken, RefreshToken> accessTokenService
    ) {
        super(repository);
        this.authCodeService = authCodeService;
        this.tokenManager = tokenManager;
        this.refreshTokenService = refreshTokenService;
        this.accessTokenService = accessTokenService;
    }

    @Override
    public AccessToken generateAccessToken(Long userId) {
        return accessTokenService.generate(getValidRefreshToken(userId));
    }

    @Override
    public RefreshToken generateRefreshToken(Long userId) {
        return refreshTokenService.generate(authCodeService.getValid());
    }

    @Override
    public RefreshToken saveRefreshToken(
            Long userId,
            Optional<RefreshToken> optionalRefreshToken
    ) {
        RefreshToken refreshToken = optionalRefreshToken.orElseThrow(() -> new RefreshTokenIsNullException("The refresh token is null"));

        // add the token to the list, so old tokens are not deleted
        List<RefreshToken> refreshTokens = getRefreshTokens(userId);
        refreshTokens.add(refreshToken);

        // set tokens to the user
        EbayUser user = findById(userId);
        user.setRefreshTokens(refreshTokens);

        // save
        refreshTokenService.save(optionalRefreshToken);
        save(Optional.of(user));

        return refreshToken;
    }

    @Override
    public RefreshToken getValidRefreshToken(Long userId) {
        try {
            RefreshToken refreshToken = refreshTokenService.findLatest();

            // check if the token is expired by adding its expiration time to the creation date
            // if the resulting date-time is before the current moment, the token is expired
            Date refreshTokenExpirationDate = Date.from(Instant.ofEpochMilli(
                    refreshToken.getCreationDate().getTime() + refreshToken.getExpiresIn()));

            if (!refreshTokenExpirationDate.after(new Date())) {
                return saveRefreshToken(
                        userId,
                        Optional.of(generateRefreshToken(userId))
                );
            } else {
                return refreshToken;
            }
        } catch (NoRecordOfRefreshTokenException exception) {
            return saveRefreshToken(
                    userId,
                    Optional.of(generateRefreshToken(userId))
            );
        }
    }

    @Override
    public List<RefreshToken> getRefreshTokens(Long userId) {
        EbayUser user = findById(userId);
        List<RefreshToken> refreshTokens;

        if (user.getRefreshTokens() != null) {
            refreshTokens = user.getRefreshTokens();
        } else {
            refreshTokens = new ArrayList<>();
        }

        return refreshTokens;
    }
}
