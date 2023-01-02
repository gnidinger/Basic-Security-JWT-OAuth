package back.global.config.security.dto;

import back.global.config.security.userDetails.AuthUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDto {

    @Getter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ResponseDto { // 일반 로그인 response

        private Long id;
        private String userId;
        private String nickName;
        private boolean firstLogin;
        private List<String> roles;
        private String profileImage; // 프로필 이미지

        public static LoginResponseDto.ResponseDto of(AuthUser authUser){
            return LoginResponseDto.ResponseDto.builder()
                    .id(authUser.getId())
                    .userId(authUser.getUserId())
                    .nickName(authUser.getNickname())
                    .build();
        }
    }


//    @Setter
//    @Getter
//    @Builder
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class firstLoginResponseDto { // 첫 로그인 response
//        private boolean firstLogin;
//        private String nickName;
//        private String email;
//        private double bookTemp = 36.5;
//        private List<String> roles;
//        private GenderType genderType;
//        private AgeType ageType;
//        private List<Genre> genres;
//        private String profileImage; // 프로필 이미지
//    }
}
