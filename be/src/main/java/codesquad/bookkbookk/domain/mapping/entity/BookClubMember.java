package codesquad.bookkbookk.domain.mapping.entity;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.member.data.entity.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookClubMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_club_member_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_club_id", nullable = false)
    private BookClub bookClub;

    @Column(name = "book_club_id", nullable = false, insertable = false, updatable = false)
    private Long bookClubId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "member_id", nullable = false, insertable = false, updatable = false)
    private Long memberId;

    public BookClubMember(BookClub bookClub, Member member) {
        this.bookClub = bookClub;
        if (bookClub != null) this.bookClubId = bookClub.getId();
        this.member = member;
        if (member != null) this.memberId = member.getId();
    }

    public static List<Member> toMembers(List<BookClubMember> bookClubMembers) {
        return bookClubMembers.stream()
                .map(BookClubMember::getMember)
                .collect(Collectors.toUnmodifiableList());
    }

}
