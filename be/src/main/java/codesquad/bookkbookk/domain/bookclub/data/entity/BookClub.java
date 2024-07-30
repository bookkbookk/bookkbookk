package codesquad.bookkbookk.domain.bookclub.data.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;

import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.bookclub.data.type.BookClubStatus;
import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;
import codesquad.bookkbookk.domain.member.data.entity.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_club_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long creatorId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private BookClubStatus status;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String profileImageUrl;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private Instant createdTime;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant closedTime;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant upcomingGatheringTime;

    @OneToMany(mappedBy = "bookClub", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookClubMember> bookClubMembers = new ArrayList<>();

    @OneToMany(mappedBy = "bookClub", fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    @Builder
    private BookClub(Long creatorId, String name, String profileImageUrl) {
        this.creatorId = creatorId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.status = BookClubStatus.OPEN;
    }

    public boolean addMember(BookClubMember bookClubMember) {
        return this.bookClubMembers.add(bookClubMember);
    }

    public boolean addBook(Book book) {
        return this.books.add(book);
    }

    public void updateUpcomingGatheringDate(Instant gatheringTime) {
        if (upcomingGatheringTime == null || upcomingGatheringTime.isAfter(gatheringTime)) {
            upcomingGatheringTime = gatheringTime;
        }
    }

    public List<Member> getMembers() {
        return bookClubMembers.stream()
                .map(BookClubMember::getMember)
                .collect(Collectors.toUnmodifiableList());
    }

    public void close() {
        this.status = BookClubStatus.CLOSED;
        this.closedTime = Instant.now();
    }

}
