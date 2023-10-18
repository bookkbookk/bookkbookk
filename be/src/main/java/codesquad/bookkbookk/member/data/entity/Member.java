package codesquad.bookkbookk.member.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import codesquad.bookkbookk.auth.data.dto.LoginRequest;
import codesquad.bookkbookk.auth.data.type.LoginType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "login_type", nullable = false)
    private LoginType loginType;
    @Column(unique = true, nullable = false)
    private String nickname;
    @Column(name = "profile_img_url", nullable = false)
    private String profileImgUrl;

    @Builder
    private Member(String email, LoginType loginType, String nickname, String profileImgUrl) {
        this.email = email;
        this.loginType = loginType;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }

    public void updateProfile(String nickname, String profileImgUrl) {
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }

    public static Member from(LoginRequest loginRequest) {
        return Member.builder()
                .email(loginRequest.getEmail())
                .loginType(loginRequest.getLoginType())
                .nickname(loginRequest.getNickname())
                .profileImgUrl(loginRequest.getProfileImageUrl())
                .build();
    }

}
