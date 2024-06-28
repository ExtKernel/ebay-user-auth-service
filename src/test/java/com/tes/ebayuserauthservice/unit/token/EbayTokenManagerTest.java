package com.tes.ebayuserauthservice.unit.token;

import com.fasterxml.jackson.databind.JsonNode;
import com.tes.ebayuserauthservice.model.AccessToken;
import com.tes.ebayuserauthservice.model.AuthCode;
import com.tes.ebayuserauthservice.model.RefreshToken;
import com.tes.ebayuserauthservice.token.EbayTokenManager;
import com.tes.ebayuserauthservice.token.TokenJsonObjectMapper;
import com.tes.ebayuserauthservice.token.TokenRequestSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EbayTokenManagerTest {

    @Mock
    private TokenRequestSender requestSender;

    @Mock
    private TokenJsonObjectMapper jsonObjectMapper;

    private EbayTokenManager tokenManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenManager = new EbayTokenManager(requestSender, jsonObjectMapper);
    }

    @Test
    void testGetRefreshToken() {
        // Given
        AuthCode authCode = new AuthCode(
                "auth-code-value",
                3600
        );
        JsonNode refreshTokenJsonNode = mock(JsonNode.class);
        RefreshToken expectedRefreshToken = new RefreshToken("refresh-token-value", 3600);

        when(requestSender.sendGetRefreshTokenRequest(authCode)).thenReturn(refreshTokenJsonNode);
        when(jsonObjectMapper.mapUserRefreshTokenJsonNodeToUserRefreshToken(refreshTokenJsonNode)).thenReturn(expectedRefreshToken);

        // When
        RefreshToken result = tokenManager.getRefreshToken(authCode);

        // Then
        assertEquals(expectedRefreshToken, result);
    }

    @Test
    void testGetAccessToken() {
        // Given
        RefreshToken refreshToken = new RefreshToken("refresh-token-value", 3600);
        JsonNode accessTokenJsonNode = mock(JsonNode.class);
        AccessToken expectedAccessToken = new AccessToken("access-token-value", 1800);

        when(requestSender.sendGetAccessTokenRequest(refreshToken)).thenReturn(accessTokenJsonNode);
        when(jsonObjectMapper.mapUserAccessTokenJsonNodeToUserAccessToken(accessTokenJsonNode)).thenReturn(expectedAccessToken);

        // When
        AccessToken result = tokenManager.getAccessToken(refreshToken);

        // Then
        assertEquals(expectedAccessToken, result);
    }
}
