package com.tes.ebayuserauthservice.token;

import com.fasterxml.jackson.databind.JsonNode;
import com.tes.ebayuserauthservice.model.AccessToken;
import com.tes.ebayuserauthservice.model.RefreshToken;

public interface TokenJsonObjectMapper {

    /**
     * Maps a JSON node representation of a refresh token to a {@link RefreshToken} object.
     *
     * @param userRefreshTokenJsonNode the JSON node representation of the refresh token.
     * @return the refresh token.
     */
    RefreshToken mapUserRefreshTokenJsonNodeToUserRefreshToken(JsonNode userRefreshTokenJsonNode);

    /**
     * Maps a JSON node representation of an access token to a {@link AccessToken} object.
     *
     * @param userAccessTokenJsonNode the JSON node representation of the access token.
     * @return the access token.
     */
    AccessToken mapUserAccessTokenJsonNodeToUserAccessToken(JsonNode userAccessTokenJsonNode);
}
