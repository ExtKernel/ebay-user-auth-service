package com.tes.ebayuserauthservice.service;

import com.tes.ebayuserauthservice.exception.RefreshTokenIsNullException;
import com.tes.ebayuserauthservice.model.AccessToken;
import com.tes.ebayuserauthservice.model.AuthModel;
import com.tes.ebayuserauthservice.model.RefreshToken;
import com.tes.ebayuserauthservice.model.User;

import java.util.List;
import java.util.Optional;

public interface Oauth2UserService<T extends User, ID, M extends AuthModel> extends CrudService<T, ID> {
    AccessToken generateAccessToken(ID userId);
    RefreshToken generateRefreshToken(ID userId);

    /**
     *
     *
     * @param userId
     * @param optionalRefreshToken
     * @return
     * @throws RefreshTokenIsNullException
     */
    RefreshToken saveRefreshToken(
            ID userId,
            Optional<RefreshToken> optionalRefreshToken
    );
    RefreshToken getValidRefreshToken(ID userId);
    List<RefreshToken> getRefreshTokens(ID userId);
}
