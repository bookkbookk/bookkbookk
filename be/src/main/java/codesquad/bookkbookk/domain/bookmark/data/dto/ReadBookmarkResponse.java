package codesquad.bookkbookk.domain.bookmark.data.dto;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.member.data.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ReadBookmarkResponse {

    private final Long bookmarkId;
    private final ReadBookmarkResponseWriter author;
    private final Integer page;
    private final Instant createdTime;
    private final Instant updatedTime;
    private final String content;

    @Builder
    private ReadBookmarkResponse(Long bookmarkId, ReadBookmarkResponseWriter author, Integer page, Instant createdTime,
                                 Instant updatedTime, String content) {
        this.bookmarkId = bookmarkId;
        this.author = author;
        this.page = page;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.content = content;
    }

    public static List<ReadBookmarkResponse> from(List<Bookmark> bookmarks) {
        return bookmarks.stream()
                .map(ReadBookmarkResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    private static ReadBookmarkResponse from(Bookmark bookmark) {
        Member writer = bookmark.getWriter();

        return ReadBookmarkResponse.builder()
                .bookmarkId(bookmark.getId())
                .author(new ReadBookmarkResponseWriter(writer.getId(), writer.getNickname(), writer.getProfileImageUrl()))
                .page(bookmark.getPage())
                .createdTime(bookmark.getCreatedTime())
                .updatedTime(bookmark.getUpdatedTime())
                .content(bookmark.getContents())
                .build();
    }

    @RequiredArgsConstructor
    @Getter
    private static class ReadBookmarkResponseWriter {

        private final Long memberId;
        private final String nickname;
        private final String profileImageUrl;

    }

}
