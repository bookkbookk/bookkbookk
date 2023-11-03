package codesquad.bookkbookk.domain.bookclub.data.dto;

import lombok.Getter;

@Getter
public class InvitationUrlResponse {

    private final String invitationUrl;

    public InvitationUrlResponse(String invitationUrl) {
        this.invitationUrl = invitationUrl;
    }

}
