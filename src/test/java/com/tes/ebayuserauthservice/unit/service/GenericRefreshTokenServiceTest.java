package com.tes.ebayuserauthservice.unit.service;

import com.tes.ebayuserauthservice.exception.NoRecordOfRefreshTokenException;
import com.tes.ebayuserauthservice.model.AuthModel;
import com.tes.ebayuserauthservice.model.RefreshToken;
import com.tes.ebayuserauthservice.repository.RefreshTokenRepository;
import com.tes.ebayuserauthservice.service.GenericRefreshTokenService;
import com.tes.ebayuserauthservice.token.TokenManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GenericRefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository repository;

    @Mock
    private RefreshTokenRepository tokenRepository;

    @Mock
    private TokenManager<TestAuthModel> tokenManager;

    private GenericRefreshTokenService<TestAuthModel> service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new TestGenericRefreshTokenService(repository, tokenRepository, tokenManager);
    }

    @Test
    void testGenerate() {
        // Given
        TestAuthModel authModel = new TestAuthModel();
        RefreshToken expectedRefreshToken = new RefreshToken();
        when(tokenManager.getRefreshToken(authModel)).thenReturn(expectedRefreshToken);

        // When
        RefreshToken result = service.generate(authModel);

        // Then
        assertEquals(expectedRefreshToken, result);
        verify(tokenManager).getRefreshToken(authModel);
    }

    @Test
    void testFindLatest() throws NoRecordOfRefreshTokenException {
        // Given
        RefreshToken expectedRefreshToken = new RefreshToken();
        when(tokenRepository.findFirstByOrderByCreationDateDesc()).thenReturn(Optional.of(expectedRefreshToken));

        // When
        RefreshToken result = service.findLatest();

        // Then
        assertEquals(expectedRefreshToken, result);
        verify(tokenRepository).findFirstByOrderByCreationDateDesc();
    }

    @Test
    void testFindLatestNoRecord() {
        // Given
        when(tokenRepository.findFirstByOrderByCreationDateDesc()).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoRecordOfRefreshTokenException.class, () -> service.findLatest());
        verify(tokenRepository).findFirstByOrderByCreationDateDesc();
    }

    // Test AuthModel class
    private static class TestAuthModel extends AuthModel {
    }

    // Concrete implementation of GenericRefreshTokenService for testing
    private static class TestGenericRefreshTokenService extends GenericRefreshTokenService<TestAuthModel> {
        public TestGenericRefreshTokenService(
                JpaRepository<RefreshToken, Long> repository,
                RefreshTokenRepository tokenRepository,
                TokenManager<TestAuthModel> tokenManager
        ) {
            super(repository, tokenRepository, tokenManager);
        }
    }
}
