package codesquad.bookkbookk.domain.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.error.exception.MemberIsNotBookmarkWriterException;
import codesquad.bookkbookk.common.error.exception.MemberIsNotCommentWriterException;
import codesquad.bookkbookk.common.error.exception.MemberJoinedBookClubException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
import codesquad.bookkbookk.domain.auth.repository.AuthorizationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final AuthorizationRepository authorizationRepository;

    @Transactional(readOnly = true)
    public void authorizeBookClubMembershipByBookClubId(Long memberId, Long bookClubId) {
        if (!authorizationRepository.existsBookClubMemberByMemberIdAndBookClubId(memberId, bookClubId)) {
            throw new MemberNotInBookClubException();
        }
    }

    @Transactional(readOnly = true)
    public void authorizeBookClubMembershipByBookId(Long memberId, Long bookId) {
        if (!authorizationRepository.existsBookClubMemberByMemberIdAndBookId(memberId, bookId)) {
            throw new MemberNotInBookClubException();
        }
    }

    @Transactional(readOnly = true)
    public void authorizeBookClubMembershipByGatheringId(Long memberId, Long gatheringId) {
        if (!authorizationRepository.existsBookClubMemberByMemberIdAndGatheringId(memberId, gatheringId)) {
            throw new MemberNotInBookClubException();
        }
    }

    @Transactional(readOnly = true)
    public void authorizeBookClubMembershipByChapterId(Long memberId, Long chapterId) {
        if (!authorizationRepository.existsBookClubMemberByMemberIdAndChapterId(memberId, chapterId)) {
            throw new MemberNotInBookClubException();
        }
    }

    @Transactional(readOnly = true)
    public void authorizeBookClubMembershipByTopicId(Long memberId, Long topicId) {
        if (!authorizationRepository.existsBookClubMemberByMemberIdAndTopicId(memberId, topicId)) {
            throw new MemberNotInBookClubException();
        }
    }

    @Transactional(readOnly = true)
    public void authorizeBookClubMembershipByBookmarkId(Long memberId, Long bookmarkId) {
        if (!authorizationRepository.existsBookClubMemberByMemberIdAndBookmarkId(memberId, bookmarkId)) {
            throw new MemberNotInBookClubException();
        }
    }

    @Transactional(readOnly = true)
    public void authorizeBookClubMembershipByCommentId(Long memberId, Long commentId) {
        if (!authorizationRepository.existsBookClubMemberByMemberIdAndCommentId(memberId, commentId)) {
            throw new MemberNotInBookClubException();
        }
    }

    @Transactional(readOnly = true)
    public void authorizeBookClubJoin(Long memberId, Long bookClubId) {
        if (authorizationRepository.existsBookClubMemberByMemberIdAndBookClubId(memberId, bookClubId)) {
            throw new MemberJoinedBookClubException();
        }
    }

    @Transactional(readOnly = true)
    public void authorizeBookmarkWriter(Long writerId, Long bookmarkId) {
        if (!authorizationRepository.existsBookmarkByIdAndWriterId(bookmarkId, writerId)) {
            throw new MemberIsNotBookmarkWriterException();
        }
    }

    @Transactional(readOnly = true)
    public void authorizeCommentWriter(Long writerId, Long commentId) {
        if (!authorizationRepository.existsCommentByIdAndWriterId(commentId, writerId)) {
            throw new MemberIsNotCommentWriterException();
        }
    }

}
