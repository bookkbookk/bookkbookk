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
    private final Instant createdTime;
    private final String content;

    @Builder
    private ReadBookmarkResponse(Long bookmarkId, ReadBookmarkResponseWriter author, Instant createdTime, String content) {
        this.bookmarkId = bookmarkId;
        this.author = author;
        this.createdTime = createdTime;
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
                .createdTime(bookmark.getCreatedTime())
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
