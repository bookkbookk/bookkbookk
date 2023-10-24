package codesquad.bookkbookk.domain.book.data.dto;

import static java.lang.String.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadAladinBooksResponse {

    private final String title;
    private final String link;
    private final String author;
    private final String pubDate;
    private final String description;
    @JsonProperty("isbn")
    private final String isbn13;
    private final String cover;
    @JsonProperty("category")
    private final String categoryName;
    private final String publisher;

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
                .link(valueOf(data.get("link")))
                .author(valueOf(data.get("author")))
                .pubDate(valueOf(data.get("pubDate")))
                .description(valueOf(data.get("description")))
                .isbn13(valueOf(data.get("isbn13")))
                .cover(valueOf(data.get("cover")))
                .categoryName(valueOf(data.get("categoryName")))
                .publisher(valueOf(data.get("publisher")))
                .build();
    }

    public static List<ReadAladinBooksResponse> from(List<Map<String, Object>> datum) {
        return datum.stream()
                .map(ReadAladinBooksResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

}


