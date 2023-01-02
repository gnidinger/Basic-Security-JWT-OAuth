package back.global.config.security.jwtTokenizer;

import back.global.config.security.refreshToken.entity.RefreshToken;
import back.global.config.security.refreshToken.repository.RefreshTokenRepository;
import back.domain.user.entity.User;
import back.global.config.security.cookieManager.CookieManager;
import back.global.error.exception.BusinessException;
import back.global.error.exception.ExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenizer {
    @Getter
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Getter
    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Getter
    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;

    private final RefreshTokenRepository repository;
    private final CookieManager cookieManager;

    public String delegateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("roles", user.getAuthType());

        String subject = user.getEmail();
        Date expiration = getTokenExpiration(getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = encodeBase64SecretKey(getSecretKey());

        String accessToken = generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    public String delegateRefreshToken(User user) {
        String subject = user.getEmail();
        Date expiration = getTokenExpiration(getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = encodeBase64SecretKey(getSecretKey());

        String refreshToken = generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }

    public Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", "");
        String base64EncodedSecretKey = encodeBase64SecretKey(getSecretKey());
        Map<String, Object> claims = getClaims(jws, base64EncodedSecretKey).getBody();
        return claims;
    }

    public RefreshToken getRefreshToken(String value) {
        return repository.findByValue(value)
                .orElse(null);
    }

    public void addRefreshToken(Long key, String value) {
        repository.save(RefreshToken.builder()
                .key(key)
                .value(value)
                .build());
    }

    @Transactional
    public void removeRefreshToken(String value) {
        repository.deleteByValue(value);
    }

    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
        return claims;
    }

    public void verifySignature(String jws) {
        String base64EncodedSecretKey = encodeBase64SecretKey(getSecretKey());
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    private String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private String generateAccessToken(Map<String, Object> claims,
                                       String subject,
                                       Date expiration,
                                       String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    private String generateRefreshToken(String subject, Date expiration, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    private Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expiration = calendar.getTime();

        return expiration;
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }

    public Boolean checkUserWithToken(HttpServletRequest request, String auth) {
        if (request.getHeader("Cookie") == null)
            return false;

        String refreshToken = cookieManager.outCookie(request, "refreshToken");
        if (refreshToken == null) return false;

        try {
            verifySignature(refreshToken);
        } catch (ExpiredJwtException e) {
            removeRefreshToken(refreshToken);
            throw new BusinessException(ExceptionCode.TOKEN_EXPIRED); // 토큰 만료
        }

        if (getRefreshToken(refreshToken) == null || auth == null)
            throw new BusinessException(ExceptionCode.ACCESS_TOKEN_NOT_FOUND); // 쿠키나 auth가 없는 경우

        return true;
    }
}
