CREATE TABLE access_token
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    expires_in    INTEGER,
    creation_date TIMESTAMP WITHOUT TIME ZONE,
    access_token  VARCHAR(2560),
    CONSTRAINT pk_accesstoken PRIMARY KEY (id)
);

CREATE TABLE auth_code
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    expires_in    INTEGER,
    creation_date TIMESTAMP WITHOUT TIME ZONE,
    auth_code     VARCHAR(255),
    CONSTRAINT pk_authcode PRIMARY KEY (id)
);

CREATE TABLE ebay_user
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    username      VARCHAR(255),
    client_id     VARCHAR(255),
    client_secret VARCHAR(255),
    CONSTRAINT pk_ebayuser PRIMARY KEY (id)
);

CREATE TABLE ebay_user_auth_codes
(
    ebay_user_id  BIGINT NOT NULL,
    auth_codes_id BIGINT NOT NULL
);

CREATE TABLE ebay_user_refresh_tokens
(
    ebay_user_id      BIGINT NOT NULL,
    refresh_tokens_id BIGINT NOT NULL
);

CREATE TABLE refresh_token
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    expires_in    INTEGER,
    creation_date TIMESTAMP WITHOUT TIME ZONE,
    refresh_token VARCHAR(255),
    CONSTRAINT pk_refreshtoken PRIMARY KEY (id)
);

ALTER TABLE ebay_user_auth_codes
    ADD CONSTRAINT uc_ebay_user_auth_codes_authcodes UNIQUE (auth_codes_id);

ALTER TABLE ebay_user_refresh_tokens
    ADD CONSTRAINT uc_ebay_user_refresh_tokens_refreshtokens UNIQUE (refresh_tokens_id);

ALTER TABLE ebay_user_auth_codes
    ADD CONSTRAINT fk_ebauseautcod_on_auth_code FOREIGN KEY (auth_codes_id) REFERENCES auth_code (id);

ALTER TABLE ebay_user_auth_codes
    ADD CONSTRAINT fk_ebauseautcod_on_ebay_user FOREIGN KEY (ebay_user_id) REFERENCES ebay_user (id);

ALTER TABLE ebay_user_refresh_tokens
    ADD CONSTRAINT fk_ebausereftok_on_ebay_user FOREIGN KEY (ebay_user_id) REFERENCES ebay_user (id);

ALTER TABLE ebay_user_refresh_tokens
    ADD CONSTRAINT fk_ebausereftok_on_refresh_token FOREIGN KEY (refresh_tokens_id) REFERENCES refresh_token (id);