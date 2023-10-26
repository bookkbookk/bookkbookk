package codesquad.bookkbookk.domain.gathering.data.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.gathering.data.dto.CreateGatheringRequest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Gathering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gathering_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    private String place;

    private Gathering(Book book, LocalDateTime dateTime, String place) {
        this.book = book;
        this.dateTime = dateTime;
        this.place = place;
    }

    public static Gathering of(CreateGatheringRequest request, Book book) {
        return new Gathering(book, request.getDateTime(), request.getPlace());
    }

}
