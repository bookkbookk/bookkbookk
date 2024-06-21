package codesquad.bookkbookk.domain.mapping.entity;

import java.util.List;
import java.util.stream.Collectors;

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
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_club_id")
    private BookClub bookClub;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    public BookClubMember(BookClub bookClub, Member member) {
        this.bookClub = bookClub;
        this.member = member;
    }

    public static List<Member> toMembers(List<BookClubMember> bookClubMembers) {
        return bookClubMembers.stream()
                .map(BookClubMember::getMember)
                .collect(Collectors.toUnmodifiableList());
    }

}
