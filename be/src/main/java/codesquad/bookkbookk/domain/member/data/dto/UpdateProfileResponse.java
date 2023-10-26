package codesquad.bookkbookk.domain.member.data.dto;

import lombok.Getter;

@Getter
public class UpdateProfileResponse {

    private final String newNickname;
    private final String newProfileImgUrl;

    public UpdateProfileResponse(String newNickname, String newProfileImgUrl) {
        this.newNickname = newNickname;
        this.newProfileImgUrl = newProfileImgUrl;
    }

}
