package com.tes.ebayuserauthservice.controller;

import com.tes.ebayuserauthservice.model.EbayUser;
import com.tes.ebayuserauthservice.model.RefreshToken;
import com.tes.ebayuserauthservice.service.Oauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/user")
@RestController
public class EbayUserController {
    private final Oauth2UserService<EbayUser, Long> service;

    @Autowired
    public EbayUserController(Oauth2UserService<EbayUser, Long> service) {
        this.service = service;
    }

    @PostMapping()
    public EbayUser save(@RequestBody EbayUser user) {
        return service.save(Optional.of(user));
    }

    @PostMapping("/refresh-token/generate/{userId}")
    public RefreshToken generateRefreshToken(@PathVariable String userId) {
        return service.generateRefreshToken(Long.valueOf(userId));
    }

    @PostMapping("/refresh-token/save/{userId}")
    public RefreshToken saveRefreshToken(
            @RequestBody RefreshToken refreshToken,
            @PathVariable String userId
    ) {
        return service.saveRefreshToken(
                Long.valueOf(userId),
                Optional.of(refreshToken)
        );
    }

    @GetMapping("/{userId}")
    public EbayUser findById(@PathVariable String userId) {
        return service.findById(Long.valueOf(userId));
    }

    @PutMapping()
    public EbayUser update(@RequestBody EbayUser user) {
        return service.update(Optional.of(user));
    }

    @DeleteMapping("/{userId}")
    public void deleteById(@PathVariable String userId) {
        service.deleteById(Long.valueOf(userId));
    }
}
