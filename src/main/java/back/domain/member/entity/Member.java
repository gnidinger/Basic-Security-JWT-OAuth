package back.domain.member.entity;

import back.domain.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

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

    @Builder
    public Member(String userId, String password, String nickname, String avatarUrl)  {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.authType = AuthType.ROLE_USER;
    }

    public Member oauthUpdate(String name, String email) {
        this.nickname = name;
        this.userId = email;
        return this;
    }
}
