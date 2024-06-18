package com.tes.ebayuserauthservice.service;

import com.tes.ebayuserauthservice.exception.NoRecordOfAuthCodeException;
import com.tes.ebayuserauthservice.model.AuthCode;
import com.tes.ebayuserauthservice.repository.AuthCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
