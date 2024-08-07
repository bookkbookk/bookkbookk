package codesquad.bookkbookk.domain.bookmark.data.dto;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;

import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.comment.data.dto.ReadCommentSliceResponse;
import codesquad.bookkbookk.domain.member.data.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReadBookmarkSliceResponse {

    private final List<ReadBookmarkSliceResponseBookmark> bookmarks;
    private final boolean hasNext;

    public static ReadBookmarkSliceResponse from(Slice<Bookmark> bookmarkSlice, Map<Long, ReadCommentSliceResponse> commentSliceMap) {
        List<ReadBookmarkSliceResponseBookmark> bookmarks = bookmarkSlice.getContent().stream()
                .map(bookmark -> ReadBookmarkSliceResponseBookmark.from(bookmark, commentSliceMap))
                .collect(Collectors.toUnmodifiableList());

        return new ReadBookmarkSliceResponse(bookmarks, bookmarkSlice.hasNext());
    }

    @Getter
    private static class ReadBookmarkSliceResponseBookmark {

        private final Long bookmarkId;
        private final ReadBookmarkSliceResponseWriter author;
        private final Integer page;
        private final Instant createdTime;
        private final Instant updatedTime;
        private final String content;
        private final ReadReactionsResponse reactions;
        private final ReadCommentSliceResponse commentSlice;

        @Builder
        private ReadBookmarkSliceResponseBookmark(Long bookmarkId, ReadBookmarkSliceResponseWriter author, Integer page,
                                                  Instant createdTime, Instant updatedTime, String content,
                                                  ReadReactionsResponse reactions,
                                                  ReadCommentSliceResponse commentSlice) {
            this.bookmarkId = bookmarkId;
            this.author = author;
            this.page = page;
            this.createdTime = createdTime;
            this.updatedTime = updatedTime;
            this.content = content;
            this.reactions = reactions;
            this.commentSlice = commentSlice;
        }

        private static ReadBookmarkSliceResponseBookmark from(Bookmark bookmark,
                                                              Map<Long, ReadCommentSliceResponse> commentSliceMap) {
            return ReadBookmarkSliceResponseBookmark.builder()
                    .bookmarkId(bookmark.getId())
                    .author(new ReadBookmarkSliceResponseWriter(bookmark.getWriter()))
                    .page(bookmark.getPage())
                    .createdTime(bookmark.getCreatedTime())
                    .updatedTime(bookmark.getUpdatedTime())
                    .content(bookmark.getContents())
                    .reactions(ReadReactionsResponse.fromBookmarkReactions(bookmark.getBookmarkReactions()))
                    .commentSlice(commentSliceMap.get(bookmark.getId()))
                    .build();
        }

    }

    @Getter
    private static class ReadBookmarkSliceResponseWriter {

        private final Long memberId;
        private final String nickname;
        private final String profileImageUrl;

        private ReadBookmarkSliceResponseWriter(Member member) {
            this.memberId = member.getId();
            this.nickname = member.getNickname();
            this.profileImageUrl = member.getProfileImageUrl();
        }

    }

}
