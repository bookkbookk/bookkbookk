package codesquad.bookkbookk.domain.book.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import codesquad.bookkbookk.domain.book.data.dto.CreateBookRequest;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;
    @Column(nullable = false)
    private String isbn;
    @ManyToOne
    @JoinColumn(name = "book_club_id")
    private BookClub bookClub;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String cover;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String category;

    @Builder
    private Book(String isbn, BookClub bookClub, String title, String cover, String author, String category) {
        this.isbn = isbn;
        this.bookClub = bookClub;
        this.title = title;
        this.cover = cover;
        this.author = author;
        this.category = category;
    }

    public static Book of(CreateBookRequest request, BookClub bookClub) {
        return Book.builder()
                .isbn(request.getIsbn())
                .bookClub(bookClub)
                .title(request.getTitle())
                .cover(request.getCover())
                .author(request.getAuthor())
                .category(request.getCategory())
                .build();
    }

}
