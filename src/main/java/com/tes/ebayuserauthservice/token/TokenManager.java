package com.tes.ebayuserauthservice.token;

import com.tes.ebayuserauthservice.model.AccessToken;
import com.tes.ebayuserauthservice.model.RefreshToken;

/**
 * @param <T> an object, which is supposed to be used for refresh token retrieval.
 */
public interface TokenManager<T> {
    RefreshToken getRefreshToken(T authModel);
    AccessToken getAccessToken(RefreshToken authModel);
}
