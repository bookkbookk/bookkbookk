package codesquad.bookkbookk.domain.gathering.data.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.gathering.data.dto.UpdateGatheringRequest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Gathering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private Instant startTime;

    @Column(nullable = false)
    private String place;

    public Gathering(Book book, Instant startTime, String place) {
        this.book = book;
        this.startTime = startTime;
        this.place = place;
    }

    public void update(UpdateGatheringRequest request) {
        if (request.getDateTime() != null) {
            this.startTime = request.getDateTime();
        }
        if (request.getPlace() != null) {
            this.place = request.getPlace();
        }
    }

}
