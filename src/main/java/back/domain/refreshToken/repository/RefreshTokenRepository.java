package back.domain.refreshToken.repository;

import back.domain.refreshToken.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByKey(String key); // member id 값으로 refresh token 찾기

    Optional<RefreshToken> findByValue(String value); // value 값으로 refresh token 찾기

    void deleteByKey(Long key); // key 값으로 refresh token 삭제

    void deleteByValue(String value); // value 값으로 refresh token 삭제
}
