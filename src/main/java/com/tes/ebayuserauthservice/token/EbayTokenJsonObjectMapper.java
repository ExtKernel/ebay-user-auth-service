package com.tes.ebayuserauthservice.token;

import com.fasterxml.jackson.databind.JsonNode;
import com.tes.ebayuserauthservice.model.AccessToken;
import com.tes.ebayuserauthservice.model.RefreshToken;
import org.springframework.stereotype.Component;

@Component
public class EbayTokenJsonObjectMapper implements TokenJsonObjectMapper {

    @Override
    public RefreshToken mapUserRefreshTokenJsonNodeToUserRefreshToken(JsonNode userRefreshTokenJsonNode) {
        return new RefreshToken(
                userRefreshTokenJsonNode.path("refresh_token").asText(),
                userRefreshTokenJsonNode.path("refresh_token_expires_in").asInt()
        );
    }

    @Override
    public AccessToken mapUserAccessTokenJsonNodeToUserAccessToken(JsonNode userAccessTokenJsonNode) {
        return new AccessToken(
                userAccessTokenJsonNode.path("access_token").asText(),
                userAccessTokenJsonNode.path("expires_in").asInt()
        );
    }
}
