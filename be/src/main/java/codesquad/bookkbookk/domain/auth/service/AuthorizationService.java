package codesquad.bookkbookk.domain.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.error.exception.MemberIsNotBookmarkWriterException;
import codesquad.bookkbookk.common.error.exception.MemberIsNotCommentWriterException;
import codesquad.bookkbookk.common.error.exception.MemberJoinedBookClubException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
import codesquad.bookkbookk.domain.mapping.repository.BookClubMemberRepository;
import codesquad.bookkbookk.domain.bookmark.repository.BookmarkRepository;
import codesquad.bookkbookk.domain.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final BookClubMemberRepository bookClubMemberRepository;
    private final BookmarkRepository bookmarkRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public void authorizeBookClubMembership(Long memberId, Long bookClubId) {
        if (!bookClubMemberRepository.existsByBookClubIdAndMemberId(bookClubId, memberId)) {
            throw new MemberNotInBookClubException();
        }
    }

    @Transactional(readOnly = true)
    public void authorizeBookClubJoin(Long memberId, Long bookClubId) {
        if (bookClubMemberRepository.existsByBookClubIdAndMemberId(bookClubId, memberId)) {
            throw new MemberJoinedBookClubException();
        }
    }

    @Transactional(readOnly = true)
    public void authorizeBookmarkWriter(Long memberId, Long bookmarkId) {
        if (!bookmarkRepository.existsByIdAndWriterId(bookmarkId, memberId)) {
            throw new MemberIsNotBookmarkWriterException();
        }
    }

    @Transactional(readOnly = true)
    public void authorizeCommentWriter(Long memberId, Long bookmarkId) {
        if (!commentRepository.existsByIdAndWriterId(bookmarkId, memberId)) {
            throw new MemberIsNotCommentWriterException();
        }
    }

}
