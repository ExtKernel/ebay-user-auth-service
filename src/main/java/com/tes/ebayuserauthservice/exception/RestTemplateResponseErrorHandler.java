package com.tes.ebayuserauthservice.exception;

import com.tes.ebayuserauthservice.service.AuthCodeService;
import com.tes.ebayuserauthservice.service.GenericRefreshTokenService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor
@AllArgsConstructor
@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    private AuthCodeService authCodeService;
    private GenericRefreshTokenService genericRefreshTokenService;

    public RestTemplateResponseErrorHandler(AuthCodeService authCodeService) {
        this.authCodeService = authCodeService;
    }

    public RestTemplateResponseErrorHandler(GenericRefreshTokenService genericRefreshTokenService) {
        this.genericRefreshTokenService = genericRefreshTokenService;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            String responseBodyText = new String(
                    response.getBody().readAllBytes(), StandardCharsets.UTF_8);

            // if the response body contains "code", it's the error
            // related to an expired or invalid authorization code.
            // if it contains "refresh", it's the error
            // related to an expired or invalid refresh token.
            if (responseBodyText.contains("code")) {
                throw new ExpiredAuthCodeException(
                        "The authorization code has expired. "
                                + "It was valid for: "
                                + authCodeService.findLatest().getExpiresIn()
                                + " seconds");
            } else if (responseBodyText.contains("refresh")) {
                throw new RuntimeException(
                        new ExpiredRefreshTokenException(
                                "The refresh token has expired. "
                                        + "It was valid for: "
                                        + genericRefreshTokenService.findLatest().getExpiresIn()
                                        + " seconds"
                        )
                );
            }
        }
    }
}
