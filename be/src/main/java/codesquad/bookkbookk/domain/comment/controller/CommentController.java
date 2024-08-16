package codesquad.bookkbookk.domain.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.common.resolver.MemberId;
import codesquad.bookkbookk.domain.bookmark.data.dto.ReadReactionsResponse;
import codesquad.bookkbookk.domain.comment.data.dto.CreateCommentReactionRequest;
import codesquad.bookkbookk.domain.comment.data.dto.CreateCommentRequest;
import codesquad.bookkbookk.domain.comment.data.dto.DeleteCommentReactionRequest;
import codesquad.bookkbookk.domain.comment.data.dto.UpdateCommentRequest;
import codesquad.bookkbookk.domain.comment.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@MemberId Long memberId,
                                              @RequestBody CreateCommentRequest createCommentRequest) {
        commentService.createComment(memberId, createCommentRequest);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@MemberId Long memberId, @PathVariable Long commentId,
                                              @RequestBody UpdateCommentRequest updateCommentRequest) {
        commentService.updateComment(memberId, commentId, updateCommentRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@MemberId Long memberId, @PathVariable Long commentId) {
        commentService.deleteComment(memberId, commentId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{commentId}/reactions")
    public ResponseEntity<Void> createBookmarkReaction(@MemberId Long memberId, @PathVariable Long commentId,
                                                       @RequestBody CreateCommentReactionRequest request) {
        commentService.createCommentReaction(memberId, commentId, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}/reactions")
    public ResponseEntity<Void> deleteBookmarkReaction(@MemberId Long memberId, @PathVariable Long commentId,
                                                       @RequestBody DeleteCommentReactionRequest request) {
        commentService.deleteCommentReaction(memberId, commentId, request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{commentId}/reactions")
    public ResponseEntity<ReadReactionsResponse> readReactions(@PathVariable Long commentId) {
        ReadReactionsResponse response = commentService.readCommentReactions(commentId);

        return ResponseEntity.ok()
                .body(response);
    }

}
