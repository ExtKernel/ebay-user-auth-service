package com.tes.ebayuserauthservice.token;

import com.tes.ebayuserauthservice.model.AuthCode;
import com.tes.ebayuserauthservice.model.RefreshToken;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

public interface TokenRequestBuilder {
    HttpEntity buildHttpRequestEntity(String requestBody);
    String buildAuthCodeRequestBody(AuthCode authCode);
    String buildRefreshTokenRequestBody(RefreshToken refreshToken);
    RestTemplate getRestTemplate();
}
