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
public class BookClubInvitationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_club_invitation_code_id")
    private Long id;

    @Column(name = "book_club_id", nullable = false)
    private Long bookClubId;

    @Column(nullable = false)
    private String invitationCode;


    public BookClubInvitationCode(CreateInvitationUrlRequest request, String invitationCode) {
        this.bookClubId = request.getBookClubId();
        this.invitationCode = invitationCode;
    }

}
