package codesquad.bookkbookk.domain.comment.data.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import codesquad.bookkbookk.domain.bookmark.data.dto.ReadReactionsResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReadCommentSliceResponse {

    @JsonIgnore
    private final Map<Long, ReadCommentSliceResponseComment> commentMap;
    private final List<ReadCommentSliceResponseComment> comments;
    private boolean hasNext;

    public ReadCommentSliceResponse() {
        this.comments = new ArrayList<>();
        this.commentMap = new HashMap<>();
    }

    public void addComment(Long commentId, Long writerId, String writerNickname, String writerProfileImageUrl,
                           Instant createdTime, String content) {
        if (this.commentMap.containsKey(commentId)) return;

        ReadCommentSliceResponseComment comment = ReadCommentSliceResponseComment.builder()
                .commentId(commentId)
                .author(new ReadCommentSliceResponseWriter(writerId, writerNickname, writerProfileImageUrl))
                .createdTime(createdTime)
                .content(content)
                .build();

        this.comments.add(comment);
        this.commentMap.put(commentId, comment);
    }

    public void addCommentReaction(Long commentId, String reactorNickname, String reactionName) {
        if (reactorNickname == null || reactionName == null) return;
        ReadCommentSliceResponseComment comment = commentMap.get(commentId);

        comment.reactions.addName(reactorNickname, reactionName);
    }

    public void markAsHasNext() {
        this.hasNext = true;
    }

    @Getter
    private static class ReadCommentSliceResponseComment {

        private final Long commentId;
        private final ReadCommentSliceResponseWriter author;
        private final Instant createdTime;
        private final String content;
        private final ReadReactionsResponse reactions;

        @Builder
        private ReadCommentSliceResponseComment(Long commentId, ReadCommentSliceResponseWriter author,
                                                Instant createdTime, String content) {
            this.commentId = commentId;
            this.author = author;
            this.createdTime = createdTime;
            this.content = content;
            this.reactions = new ReadReactionsResponse();
        }

    }

    @Getter
    private static class ReadCommentSliceResponseWriter {

        private final Long memberId;
        private final String nickname;
        private final String profileImageUrl;

        private ReadCommentSliceResponseWriter(Long memberId, String nickname, String profileImageUrl) {
            this.memberId = memberId;
            this.nickname = nickname;
            this.profileImageUrl = profileImageUrl;
        }

    }

}
