package codesquad.bookkbookk.domain.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.error.exception.BookmarkNotFoundException;
import codesquad.bookkbookk.common.error.exception.CommentNotFoundException;
import codesquad.bookkbookk.common.error.exception.CommentReactionExistsException;
import codesquad.bookkbookk.common.error.exception.CommentReactionNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;
import codesquad.bookkbookk.common.type.Reaction;
import codesquad.bookkbookk.domain.auth.service.AuthorizationService;
import codesquad.bookkbookk.domain.bookmark.data.dto.ReadReactionsResponse;
import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.bookmark.repository.BookmarkRepository;
import codesquad.bookkbookk.domain.comment.data.dto.CreateCommentReactionRequest;
import codesquad.bookkbookk.domain.comment.data.dto.CreateCommentRequest;
import codesquad.bookkbookk.domain.comment.data.dto.DeleteCommentReactionRequest;
import codesquad.bookkbookk.domain.comment.data.dto.ReadCommentResponse;
import codesquad.bookkbookk.domain.comment.data.dto.UpdateCommentRequest;
import codesquad.bookkbookk.domain.comment.data.entity.Comment;
import codesquad.bookkbookk.domain.comment.repository.CommentRepository;
import codesquad.bookkbookk.domain.mapping.entity.CommentReaction;
import codesquad.bookkbookk.domain.mapping.repository.CommentReactionRepository;
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
    private final CommentReactionRepository commentReactionRepository;

    @Transactional
    public void createComment(Long memberId, CreateCommentRequest createCommentRequest) {
        authorizationService.authorizeBookClubMembershipByBookmarkId(memberId, createCommentRequest.getBookmarkId());

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

    @Transactional
    public void createCommentReaction(Long memberId, Long commentId, CreateCommentReactionRequest request) {
        authorizationService.authorizeBookClubMembershipByCommentId(memberId, commentId);

        Reaction reaction = Reaction.of(request.getReactionName());
        if (commentReactionRepository.existsByCommentIdAndReactorIdAndReaction(commentId, memberId, reaction)) {
            throw new CommentReactionExistsException();
        }
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        CommentReaction commentReaction = new CommentReaction(comment, member, reaction);
        commentReactionRepository.save(commentReaction);
        comment.getCommentReactions().add(commentReaction);
    }

    @Transactional
    public void deleteCommentReaction(Long memberId, Long commentId, DeleteCommentReactionRequest request) {
        Reaction reaction = Reaction.of(request.getReactionName());

        CommentReaction commentReaction = commentReactionRepository
                .findByCommentIdAndReactorIdAndReaction(commentId, memberId, reaction)
                .orElseThrow(CommentReactionNotFoundException::new);
        commentReaction.getComment().getCommentReactions().remove(commentReaction);
        commentReactionRepository.delete(commentReaction);
    }

    @Transactional(readOnly = true)
    public List<ReadCommentResponse> readComments(Long memberId, Long bookmarkId) {
        authorizationService.authorizeBookClubMembershipByBookmarkId(memberId, bookmarkId);

        return ReadCommentResponse.from(commentRepository.findAllByBookmarkId(bookmarkId));
    }

    @Transactional(readOnly = true)
    public ReadReactionsResponse readCommentReactions(Long memberId, Long commentId) {
        authorizationService.authorizeBookClubMembershipByCommentId(memberId, commentId);

        return ReadReactionsResponse.fromCommentReactions(commentReactionRepository.findAllByCommentId(commentId));
    }

}
