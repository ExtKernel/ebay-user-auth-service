package com.tes.ebayuserauthservice.service;

import com.tes.ebayuserauthservice.exception.ExpiredAuthCodeException;
import com.tes.ebayuserauthservice.exception.NoRecordOfAuthCodeException;
import com.tes.ebayuserauthservice.model.AuthCode;
import com.tes.ebayuserauthservice.repository.AuthCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class AuthCodeService extends GenericCrudService<AuthCode, Long> {
    AuthCodeRepository repository;

    @Autowired
    public AuthCodeService(
            AuthCodeRepository repository
    ) {
        super(repository);
        this.repository = repository;
    }

    /**
     * Get a {@link AuthCode}, which will be checked for validity.
     *
     * @return the latest {@link AuthCode}.
     * @throws ExpiredAuthCodeException if the latest {@link AuthCode} is expired.
     */
    public AuthCode getValid() {
        AuthCode authCode = findLatest();

        // check if the code is expired by adding its expiration time to the creation date
        // if the resulting date-time is before the current moment, the code is expired
        Date authCodeExpirationDate = Date.from(Instant.ofEpochMilli(
                authCode.getCreationDate().getTime() + authCode.getExpiresIn()));

        if (!authCodeExpirationDate.after(new Date())) throw new ExpiredAuthCodeException(
                "The auth code has expired."
                        + " It was valid for "
                        + authCode.getExpiresIn()
        );

        return authCode;
    }

    /**
     * Retrieves the latest {@link AuthCode} object saved in the database.
     * Which might be expired!
     *
     * @return the latest {@link AuthCode} object saved in the database.
     * @throws NoRecordOfAuthCodeException if there are no {@link AuthCode} objects in the database.
     */
    public AuthCode findLatest()
            throws NoRecordOfAuthCodeException {
        if (repository.findFirstByOrderByCreationDateDesc().isPresent()) {
            return repository.findFirstByOrderByCreationDateDesc().get();
        } else {
            throw new NoRecordOfAuthCodeException(
                            "The latest saved authorization code was not found, "
                                    + "because no record exists in the database"
            );
        }
    }
}
