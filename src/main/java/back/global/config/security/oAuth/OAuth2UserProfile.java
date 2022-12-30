package back.global.config.security.oAuth;

import back.domain.member.entity.Member;

public class OAuth2UserProfile {

    private final String name;
    private final String email;
    private final String oauthId;

    public OAuth2UserProfile(String oauthId, String name, String email) {
        this.name = name;
        this.email = email;
        this.oauthId = oauthId;
    }

    public Member createOauth2Member() {
        return Member.builder()
                .userId(email) //이메일
                .nickname(name) // 이름
                .password(oauthId) // 고유값
                .build();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
