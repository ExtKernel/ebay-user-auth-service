package com.tes.ebayuserauthservice.token;

import com.fasterxml.jackson.databind.JsonNode;
import com.tes.ebayuserauthservice.model.AuthCode;
import com.tes.ebayuserauthservice.model.RefreshToken;

/**
 * An interface to send token related requests.
 */
public interface TokenRequestSender {
    /**
     * Sends a request to get a refresh token.
     *
     * @param authCode an authorization code.
     * @return a JSON node containing the refresh token.
     */
    JsonNode sendGetRefreshTokenRequest(AuthCode authCode);

    /**
     * Sends a request to get an access token.
     *
     * @param refreshToken a refresh token.
     * @return a JSON node containing the access token.
     */
    JsonNode sendGetAccessTokenRequest(RefreshToken refreshToken);
}
