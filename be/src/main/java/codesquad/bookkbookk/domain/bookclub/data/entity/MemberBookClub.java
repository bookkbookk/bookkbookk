package codesquad.bookkbookk.domain.bookclub.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import codesquad.bookkbookk.domain.member.data.entity.Member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity(name = "member_book_club")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberBookClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_book_club_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_club_id")
    private BookClub bookClub;

    public MemberBookClub(Member member, BookClub bookClub) {
        this.member = member;
        this.bookClub = bookClub;
    }

}
