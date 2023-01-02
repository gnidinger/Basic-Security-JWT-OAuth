package back.global.config.security.refreshToken.entity;

import back.global.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseTimeEntity {

    @Id
    @Column(name = "rt_key")
    // user id 값이 들어감
    private Long key;

    @Column(name = "rt_value")
    // refresh token (String)
    private String value;

    @Builder
    public RefreshToken(Long key, String value) {
        this.key = key;
        this.value = value;
    }

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
