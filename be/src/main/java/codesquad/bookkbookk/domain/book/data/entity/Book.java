package codesquad.bookkbookk.domain.book.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import codesquad.bookkbookk.domain.book.data.dto.CreateBookRequest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Book {

    @Id
    @Column(name = "isbn")
    private Long isbn;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String cover;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String category;

    @Builder
    private Book(Long isbn, String title, String cover, String author, String category) {
        this.isbn = isbn;
        this.title = title;
        this.cover = cover;
        this.author = author;
        this.category = category;
    }

    public static Book from(CreateBookRequest request) {
        return Book.builder()
                .isbn(request.getIsbn())
                .title(request.getTitle())
                .cover(request.getCover())
                .author(request.getAuthor())
                .category(request.getCategory())
                .build();
    }

}
