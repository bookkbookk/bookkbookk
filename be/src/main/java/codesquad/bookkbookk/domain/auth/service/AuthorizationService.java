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
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;
import codesquad.bookkbookk.domain.comment.data.entity.Comment;
import codesquad.bookkbookk.domain.gathering.data.entity.Gathering;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;

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

    @Transactional(readOnly = true)
    public void authorizeBookClubMembershipByBookId(Long bookId, Long memberId) {
        List<BookClubMemberAuthInfo> authInfos = authorizationJdbcRepository
                .findBookClubMemberAuthsByBookIdAndMemberId(bookId, memberId);

        validateAuthInfos(authInfos, bookId, memberId, Book.class);
    }

    @Transactional(readOnly = true)
    public void authorizeBookClubMembershipByGatheringId(Long gatheringId, Long memberId) {
        List<BookClubMemberAuthInfo> authInfos = authorizationJdbcRepository
                .findBookClubMemberAuthsByGatheringIdAndMemberId(gatheringId, memberId);

        validateAuthInfos(authInfos, gatheringId, memberId, Gathering.class);
    }

    @Transactional(readOnly = true)
    public void authorizeBookClubMembershipByChapterId(Long chapterId, Long memberId) {
        List<BookClubMemberAuthInfo> authInfos = authorizationJdbcRepository
                .findBookClubMemberAuthsByChapterIdAndMemberId(chapterId, memberId);

        validateAuthInfos(authInfos, chapterId, memberId, Chapter.class);
    }

    @Transactional(readOnly = true)
    public void authorizeBookClubMembershipByTopicId(Long topicId, Long memberId) {
        List<BookClubMemberAuthInfo> authInfos = authorizationJdbcRepository
                .findBookClubMemberAuthsByTopicIdAndMemberId(topicId, memberId);

        validateAuthInfos(authInfos, topicId, memberId, Topic.class);
    }

    @Transactional(readOnly = true)
    public void authorizeBookClubMembershipByBookmarkId(Long bookmarkId, Long memberId) {
        List<BookClubMemberAuthInfo> authInfos = authorizationJdbcRepository
                .findBookClubMemberAuthsByBookmarkIdAndMemberId(bookmarkId, memberId);

        validateAuthInfos(authInfos, bookmarkId, memberId, Bookmark.class);
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

}
