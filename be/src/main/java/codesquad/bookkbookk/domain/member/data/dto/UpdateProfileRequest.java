package codesquad.bookkbookk.domain.member.data.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateProfileRequest {

    private String nickname;
    private MultipartFile profileImage;

    public UpdateProfileRequest(String nickname, MultipartFile profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

}
