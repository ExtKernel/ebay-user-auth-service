package com.tes.ebayuserauthservice.repository;

import com.tes.ebayuserauthservice.model.EbayUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EbayUserRepository extends JpaRepository<EbayUser, Long> {
}
