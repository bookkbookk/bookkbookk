package codesquad.bookkbookk.domain.bookclub.data.dto;

import lombok.Getter;

@Getter
public class InvitationUrlResponse {

    private static final String INVITATION_URL_PREFIX = "https://bookkbookk.site/join/";

    private final String invitationUrl;

    public InvitationUrlResponse(String invitationCode) {
        this.invitationUrl = INVITATION_URL_PREFIX + invitationCode;
    }

}
