package com.tes.ebayuserauthservice.token;

import com.fasterxml.jackson.databind.JsonNode;
import com.tes.ebayuserauthservice.model.AccessToken;
import com.tes.ebayuserauthservice.model.RefreshToken;

public interface TokenJsonObjectMapper {

    /**
     * Maps a {@link JsonNode} representation of a refresh token to a {@link RefreshToken} object.
     *
     * @param userRefreshTokenJsonNode the JSON node representation of the {@link RefreshToken}.
     * @return the {@link RefreshToken}.
     */
    RefreshToken mapUserRefreshTokenJsonNodeToUserRefreshToken(JsonNode userRefreshTokenJsonNode);

    /**
     * Maps a {@link JsonNode} representation of an access token to a {@link AccessToken} object.
     *
     * @param userAccessTokenJsonNode the JSON node representation of the {@link AccessToken}.
     * @return the {@link AccessToken}.
     */
    AccessToken mapUserAccessTokenJsonNodeToUserAccessToken(JsonNode userAccessTokenJsonNode);
}
