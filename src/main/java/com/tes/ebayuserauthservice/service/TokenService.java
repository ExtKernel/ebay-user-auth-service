package com.tes.ebayuserauthservice.service;

import com.tes.ebayuserauthservice.exception.NoRecordOfRefreshTokenException;
import com.tes.ebayuserauthservice.model.AuthModel;

public interface TokenService<TokenType, T extends AuthModel> extends CrudService<TokenType, Long> {
    TokenType generate(
            T authModel
    );
    TokenType findLatest() throws NoRecordOfRefreshTokenException;
}
