package codesquad.bookkbookk.domain.bookclub.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookClubInvitationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bookClubId;

    @Column(nullable = false)
    private String invitationCode;

    public BookClubInvitationCode(Long bookClubId, String invitationCode) {
        this.bookClubId = bookClubId;
        this.invitationCode = invitationCode;
    }

}
