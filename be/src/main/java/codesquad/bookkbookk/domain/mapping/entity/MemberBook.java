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

import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.member.data.entity.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "member_id", nullable = false, insertable = false, updatable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "book_id", nullable = false, insertable = false, updatable = false)
    private Long bookId;

    public MemberBook(Member member, Book book) {
        this.member = member;
        if (member != null) this.memberId = member.getId();
        this.book = book;
        if (book != null) this.bookId = book.getId();
    }

    public static List<MemberBook> of(Member member, List<Book> books) {
        return books.stream()
                .map(book -> new MemberBook(member, book))
                .collect(Collectors.toUnmodifiableList());
    }

}

