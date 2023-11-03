package codesquad.bookkbookk.domain.bookclub.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import codesquad.bookkbookk.domain.bookclub.data.dto.CreateInvitationUrlRequest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookClubInvitationUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_club_invite_url_id")
    private Long id;

    @Column(name = "book_club_id", nullable = false)
    private Long bookClubId;

    @Column(nullable = false)
    private String invitationUrl;


    public BookClubInvitationUrl(CreateInvitationUrlRequest request, String invitationUrl) {
        this.bookClubId = request.getBookClubId();
        this.invitationUrl = invitationUrl;
    }

}
