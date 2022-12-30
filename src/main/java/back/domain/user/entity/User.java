package back.domain.user.entity;

import back.domain.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "oauth_id", unique = true)
    private String oauthId;

    @Size(min = 8)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private AuthType authType;

    private String provider;    // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지
    private String providerId;  // oauth2를 이용할 경우 아이디값

    private String email; // OAuth의 경우 이메일이 존재할 가능성 있음

    @Builder
    public User(String userId, String password, String nickname)  {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.authType = AuthType.ROLE_USER;
    }

    @Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
    public User(String nickname, String password, String email, AuthType authType, String provider, String providerId) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.authType = authType;
        this.provider = provider;
        this.providerId = providerId;
    }

    public User oauthUpdate(String name, String email) {
        this.nickname = name;
        this.userId = email;
        return this;
    }
}
