package back.global.config.security.oAuth.service;

import back.domain.user.entity.AuthType;
import back.domain.user.entity.User;
import back.domain.user.repository.UserRepository;
import back.global.config.security.oAuth.userInfo.KakaoUserInfo;
import back.global.config.security.oAuth.userInfo.NaverUserInfo;
import back.global.config.security.oAuth.userInfo.OAuth2UserInfo;
import back.global.config.security.oAuth.userInfo.OAuth2PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static back.domain.user.entity.AuthType.ROLE_USER;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2PrincipalUserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;	//추가
        String provider = userRequest.getClientRegistration().getRegistrationId();    //google

        if (provider.equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
        } else if (provider.equals("kakao")) {    //추가
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }

        String providerId = oAuth2UserInfo.getProviderId(); // 네이버를 위해 추가
        String nickname = provider+"_"+providerId; // 사용자가 입력한 적은 없지만 만들어준다

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String password = bCryptPasswordEncoder.encode("패스워드"+uuid);  // 사용자가 입력한 적은 없지만 만들어준다

        String email = oAuth2UserInfo.getEmail(); //수정
        AuthType authType = ROLE_USER;

        User findUser = userRepository.findByNickname(nickname);

        //DB에 없는 사용자라면 회원가입처리
        if(findUser == null){
            findUser = User.oauth2Register()
                    .nickname(nickname)
                    .password(password)
                    .email(email)
                    .authType(authType)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(findUser);
        }

        return new OAuth2PrincipalDetails(findUser, oAuth2User.getAttributes());
    }
}
