package com.tes.ebayuserauthservice.token;

import com.tes.ebayuserauthservice.model.AccessToken;
import com.tes.ebayuserauthservice.model.AuthCode;
import com.tes.ebayuserauthservice.model.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EbayTokenManager implements TokenManager<AuthCode> {
    private final TokenRequestSender requestSender;
    private final TokenJsonObjectMapper jsonObjectMapper;

    @Autowired
    public EbayTokenManager(
            TokenRequestSender requestSender,
            TokenJsonObjectMapper jsonObjectMapper
    ) {
        this.requestSender = requestSender;
        this.jsonObjectMapper = jsonObjectMapper;
    }

    @Override
    public RefreshToken getRefreshToken(AuthCode authCode) {
        return jsonObjectMapper.mapUserRefreshTokenJsonNodeToUserRefreshToken(
                requestSender.sendGetRefreshTokenRequest(authCode));
    }

    @Override
    public AccessToken getAccessToken(RefreshToken refreshToken) {
        return jsonObjectMapper.mapUserAccessTokenJsonNodeToUserAccessToken(
                requestSender.sendGetAccessTokenRequest(refreshToken));
    }
}
