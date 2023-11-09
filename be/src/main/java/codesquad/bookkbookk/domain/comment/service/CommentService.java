package codesquad.bookkbookk.domain.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.error.exception.BookmarkNotFoundException;
import codesquad.bookkbookk.common.error.exception.CommentNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;
import codesquad.bookkbookk.domain.auth.service.AuthorizationService;
import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.bookmark.repository.BookmarkRepository;
import codesquad.bookkbookk.domain.comment.data.dto.CreateCommentRequest;
import codesquad.bookkbookk.domain.comment.data.dto.UpdateCommentRequest;
import codesquad.bookkbookk.domain.comment.data.entity.Comment;
import codesquad.bookkbookk.domain.comment.repository.CommentRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final AuthorizationService authorizationService;

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public void createComment(Long memberId, CreateCommentRequest createCommentRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Bookmark bookmark = bookmarkRepository.findById(createCommentRequest.getBookmarkId())
                .orElseThrow(BookmarkNotFoundException::new);
        Comment comment = new Comment(bookmark, member, createCommentRequest.getContent());

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long memberId, Long commentId, UpdateCommentRequest updateCommentRequest) {
        authorizationService.authorizeCommentWriter(commentId, memberId);

        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        comment.updateComment(updateCommentRequest);
    }

    @Transactional
    public void deleteComment(Long memberId, Long commentId) {
        authorizationService.authorizeCommentWriter(commentId, memberId);

        commentRepository.deleteById(commentId);
    }

}
