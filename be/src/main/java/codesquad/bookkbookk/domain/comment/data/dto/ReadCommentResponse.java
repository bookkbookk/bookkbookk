package codesquad.bookkbookk.domain.comment.data.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import codesquad.bookkbookk.domain.comment.data.entity.Comment;
import codesquad.bookkbookk.domain.member.data.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ReadCommentResponse {

    private final Long commentId;
    private final ReadCommentAuthor author;
    private final LocalDateTime createdTime;
    private final String content;

    @Builder
    private ReadCommentResponse(Long commentId, ReadCommentAuthor author, LocalDateTime createdTime, String content) {
        this.commentId = commentId;
        this.author = author;
        this.createdTime = createdTime;
        this.content = content;
    }

    public static List<ReadCommentResponse> from(List<Comment> comments) {
        return comments.stream()
                .map(ReadCommentResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
    private static ReadCommentResponse from(Comment comment) {
        return ReadCommentResponse.builder()
                .commentId(comment.getId())
                .author(ReadCommentAuthor.from(comment.getWriter()))
                .createdTime(comment.getCreateAt())
                .content(comment.getContent())
                .build();
    }

    @RequiredArgsConstructor
    @Getter
    private static class ReadCommentAuthor {

        private final Long memberId;
        private final String nickname;
        private final String profileImgUrl;

        public static ReadCommentAuthor from(Member member) {
            return new ReadCommentAuthor(member.getId(), member.getNickname(), member.getProfileImgUrl());
        }

    }

}
