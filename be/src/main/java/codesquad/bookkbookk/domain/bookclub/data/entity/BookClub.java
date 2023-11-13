package codesquad.bookkbookk.domain.bookclub.data.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.bookclub.data.type.BookClubStatus;
import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_club_id")
    private Long id;
    @Column(name = "creator_id", nullable = false)
    private Long creatorId;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(name = "profile_img_url", nullable = false)
    private String profileImgUrl;
    @Column(name = "book_club_status", nullable = false)
    private BookClubStatus bookClubStatus;
    @CreationTimestamp
    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;
    @Column(name = "closed_time")
    private LocalDateTime closedTime;
    @Column(name = "upcoming_gathering_date")
    private LocalDateTime upcomingGatheringDate;

    @OneToMany(mappedBy = "bookClub")
    private List<BookClubMember> bookClubMembers = new ArrayList<>();
    @OneToMany(mappedBy = "bookClub")
    private List<Book> books = new ArrayList<>();

    @Builder
    private BookClub(Long creatorId, String name, String profileImgUrl) {
        this.creatorId = creatorId;
        this.name = name;
        this.profileImgUrl = profileImgUrl;
        this.bookClubStatus = BookClubStatus.OPEN;
    }

    public void updateUpcomingGatheringDate(LocalDateTime gatheringDate) {
        if (gatheringDate.isBefore(upcomingGatheringDate)) {
            upcomingGatheringDate = gatheringDate;
        }
    }

}
