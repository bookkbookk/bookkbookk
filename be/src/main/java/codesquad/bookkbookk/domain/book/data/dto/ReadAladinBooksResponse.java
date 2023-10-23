package codesquad.bookkbookk.domain.book.data.dto;

import static java.lang.String.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadAladinBooksResponse {

    private String title;
    private String link;
    private String author;
    private String pubDate;
    private String description;
    private String isbn13;
    private String cover;
    private String categoryName;
    private String publisher;

    @Builder
    private ReadAladinBooksResponse(String title, String link, String author, String pubDate, String description,
                                    String isbn13, String cover, String categoryName, String publisher) {
        this.title = title;
        this.link = link;
        this.author = author;
        this.pubDate = pubDate;
        this.description = description;
        this.isbn13 = isbn13;
        this.cover = cover;
        this.categoryName = categoryName;
        this.publisher = publisher;
    }

    public static ReadAladinBooksResponse from(Map<String, Object> data) {
        return ReadAladinBooksResponse.builder()
                .title(valueOf(data.get("title")))
                .link((String) data.get("link"))
                .author(valueOf(data.get("author")))
                .pubDate((String) data.get("pubDate"))
                .description((String) data.get("description"))
                .isbn13(valueOf(data.get("isbn13")))
                .cover((String) data.get("cover"))
                .categoryName((String) data.get("categoryName"))
                .publisher((String) data.get("publisher"))
                .build();
    }

    public static List<ReadAladinBooksResponse> from(List<Map<String, Object>> datum) {
        return datum.stream()
                .map(ReadAladinBooksResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

}


