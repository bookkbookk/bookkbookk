package codesquad.bookkbookk.domain.gathering.data.dto;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import codesquad.bookkbookk.common.type.Status;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.gathering.data.entity.Gathering;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReadGatheringResponse {

        private final ReadGatheringBook book;
        private final Instant dateTime;
        private final String place;

        public static List<ReadGatheringResponse> from(List<Gathering> gatherings) {
            return gatherings.stream()
                    .map(ReadGatheringResponse::from)
                    .collect(Collectors.toUnmodifiableList());
        }

        private static ReadGatheringResponse from(Gathering gathering) {
            return new ReadGatheringResponse(ReadGatheringBook.from(gathering.getBook()), gathering.getStartTime(),
                    gathering.getPlace());
        }

        @Getter
        private static class ReadGatheringBook {

            private final String title;
            private final String cover;
            private final String author;
            private final String category;
            private final Status status;

            @Builder
            private ReadGatheringBook(String title, String cover, String author, String category, Status status) {
                this.title = title;
                this.cover = cover;
                this.author = author;
                this.category = category;
                this.status = status;
            }

            public static ReadGatheringBook from(Book book) {
                return ReadGatheringBook.builder()
                        .title(book.getTitle())
                        .cover(book.getCover())
                        .author(book.getAuthor())
                        .category(book.getCategory())
                        .status(book.getStatus())
                        .build();
            }

        }

}
