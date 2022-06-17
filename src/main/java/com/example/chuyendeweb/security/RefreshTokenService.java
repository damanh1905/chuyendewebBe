package com.example.chuyendeweb.security;

import com.example.chuyendeweb.entity.RefreshTokenEntity;
import com.example.chuyendeweb.exception.TokenRefreshException;
import com.example.chuyendeweb.repository.RefreshTokenRepository;
import com.example.chuyendeweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${jtw.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshTokenEntity finByIdUserEntity(Long id) {
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByIdUserEntity(id);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public void createRefreshToken(Long userId) {
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setUserEntity(userRepository.findById(userId).get());
        refreshTokenRepository.save(refreshToken);
    }
    public RefreshTokenEntity createRefreshToken2(Long userId) {
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();

        refreshToken.setUserEntity(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }
    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }
}
