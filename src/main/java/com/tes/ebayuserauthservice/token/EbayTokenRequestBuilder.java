package com.tes.ebayuserauthservice.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tes.ebayuserauthservice.exception.RestTemplateResponseErrorHandler;
import com.tes.ebayuserauthservice.exception.WritingAuthCodeRequestBodyToJsonStringException;
import com.tes.ebayuserauthservice.exception.WritingRefreshTokenRequestBodyToJsonStringException;
import com.tes.ebayuserauthservice.model.AuthCode;
import com.tes.ebayuserauthservice.model.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class EbayTokenRequestBuilder implements TokenRequestBuilder {
    ObjectMapper objectMapper;
    RestTemplateBuilder restTemplateBuilder;

    @Value("${ebayClientId}")
    private String clientId;

    @Value("${ebayClientSecret}")
    private String clientSecret;

    @Value("${EBAY_CLIENT_REDIRECT_URI}")
    private String redirectUri;

    @Autowired
    public EbayTokenRequestBuilder(
            ObjectMapper objectMapper,
            RestTemplateBuilder restTemplateBuilder
    ) {
        this.objectMapper = objectMapper;
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public HttpEntity<String> buildHttpRequestEntity(String requestBody) {
        return new HttpEntity<>(
                requestBody,
                buildHeaders()
        );
    }

    @Override
    public String buildAuthCodeRequestBody(AuthCode authCode) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "authorization_code");
        requestBody.put("code", authCode.getAuthCode());
        requestBody.put("redirect_uri", redirectUri);

        try {
            return objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException exception) {
            throw new WritingAuthCodeRequestBodyToJsonStringException(
                    "An exception occurred, while writing a request body,"
                            + " containing an authorization code to a JSON string",
                    exception
            );
        }
    }

    @Override
    public String buildRefreshTokenRequestBody(RefreshToken refreshToken) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "refresh_token");
        requestBody.put("refresh_token", refreshToken.getRefreshToken());

        try {
            return objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException exception) {
            throw new WritingRefreshTokenRequestBodyToJsonStringException(
                    "An exception occurred, while writing a request body,"
                            + " containing a refresh token to a JSON string",
                    exception
            );
        }
    }

    @Override
    public RestTemplate getRestTemplate() {
        return restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(
                Base64.getEncoder()
                        .encodeToString((clientId + ":" + clientSecret).getBytes()));

        return headers;
    }
}
