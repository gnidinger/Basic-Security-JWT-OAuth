package back.domain.user.entity;

import back.domain.user.entity.enums.AgeType;
import back.domain.user.entity.enums.AuthType;
import back.domain.user.entity.enums.GenderType;
import back.global.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Builder
@Table(name = "users")
@AllArgsConstructor
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
    @Column(name = "profile_image")
    private String profileImage; // 프로필 이미지 경로

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Enumerated(EnumType.STRING)
    private AgeType ageType;

    @Enumerated(EnumType.STRING)
    private AuthType authType;

    private String provider;    // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지

    private String providerId;  // oauth2를 이용할 경우 아이디 값

    private String email; // OAuth의 경우 이메일이 존재할 가능성 있음


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
