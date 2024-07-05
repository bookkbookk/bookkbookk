package codesquad.bookkbookk.domain.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.error.exception.BookClubAuthorizationFailedException;
import codesquad.bookkbookk.common.error.exception.EntityNotFountException;
import codesquad.bookkbookk.common.error.exception.MemberIsNotBookmarkWriterException;
import codesquad.bookkbookk.common.error.exception.MemberIsNotCommentWriterException;
import codesquad.bookkbookk.common.error.exception.MemberJoinedBookClubException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
import codesquad.bookkbookk.domain.auth.data.dto.BookClubMemberAuthInfo;
import codesquad.bookkbookk.domain.auth.repository.AuthorizationJdbcRepository;
import codesquad.bookkbookk.domain.auth.repository.AuthorizationRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.comment.data.entity.Comment;
import codesquad.bookkbookk.domain.member.data.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final AuthorizationRepository authorizationRepository;

    private final AuthorizationJdbcRepository authorizationJdbcRepository;

    @Transactional(readOnly = true)
    public void authorizeBookClubMembershipByBookClubId(Long bookClubId, Long memberId) {
        List<BookClubMemberAuthInfo> authInfos = authorizationJdbcRepository
                .findBookClubMemberAuthsByBookClubIdAndMemberId(bookClubId, memberId);

        validateAuthInfos(authInfos, bookClubId, memberId, BookClub.class);
    }

    private void validateAuthInfos(List<BookClubMemberAuthInfo> authInfos, Long entityId, Long memberId, Class<?> entity) {

        int infoSize = authInfos.size();

        if (infoSize == 3) return;
        if (infoSize == 2) throw new MemberNotInBookClubException();
        if (infoSize == 1) {
            Long authEntityId = authInfos.get(0).getEntityId();
            Long authMemberId = authInfos.get(0).getMemberId();

            if (authEntityId == null) throw new EntityNotFountException(entity);
            if (authMemberId == null) throw new EntityNotFountException(Member.class);
            if (authEntityId.equals(entityId)) throw new EntityNotFountException(Member.class);
            if (authMemberId.equals(memberId)) throw new EntityNotFountException(entity);
        }
        throw new BookClubAuthorizationFailedException();
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
    public void authorizeBookClubMembershipByCommentId(Long commentId, Long memberId) {
        List<BookClubMemberAuthInfo> authInfos = authorizationJdbcRepository
                .findBookClubMemberAuthsByCommentIdAndMemberId(commentId, memberId);

        validateAuthInfos(authInfos, commentId, memberId, Comment.class);
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
