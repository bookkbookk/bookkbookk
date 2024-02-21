package codesquad.bookkbookk.domain.auth.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthorizationRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public boolean existsBookClubMemberByMemberIdAndBookClubId(Long memberId, Long bookClubId) {
        String nativeQuery = "SELECT bookClubMember.id " +
                "FROM BookClubMember AS bookClubMember " +
                "WHERE bookClubMember.bookClub.id = :bookClubId " +
                    "AND bookClubMember.member.id = :memberId";

        List<?> foundIds = entityManager.createQuery(nativeQuery)
                .setParameter("bookClubId", bookClubId)
                .setParameter("memberId", memberId)
                .setMaxResults(1)
                .getResultList();
        return !foundIds.isEmpty();
    }

    public boolean existsBookClubMemberByMemberIdAndBookId(Long memberId, Long bookId) {
        String nativeQuery = "SELECT book.id " +
                "FROM Book AS book " +
                "INNER JOIN BookClubMember AS bookClubMember " +
                    "ON bookClubMember.bookClub.id = book.bookClub.id " +
                "WHERE book.id = :bookId " +
                    "AND bookClubMember.member.id = :memberId";

        List<?> foundIds = entityManager.createQuery(nativeQuery)
                .setParameter("bookId", bookId)
                .setParameter("memberId", memberId)
                .setMaxResults(1)
                .getResultList();
        return !foundIds.isEmpty();
    }

    public boolean existsBookClubMemberByMemberIdAndGatheringId(Long memberId, Long gatheringId) {
        String nativeQuery = "SELECT gathering.id " +
                "FROM Gathering AS gathering " +
                "INNER JOIN BookClubMember AS bookClubMember " +
                    "ON bookClubMember.bookClub.id = gathering.book.bookClub.id " +
                "WHERE gathering.id = :gatheringId " +
                    "AND bookClubMember.member.id = :memberId";

        List<?> foundIds = entityManager.createQuery(nativeQuery)
                .setParameter("gatheringId", gatheringId)
                .setParameter("memberId", memberId)
                .setMaxResults(1)
                .getResultList();
        return !foundIds.isEmpty();
    }

    public boolean existsBookClubMemberByMemberIdAndChapterId(Long memberId, Long chapterId) {
        String nativeQuery = "SELECT chapter.id " +
                "FROM Chapter AS chapter " +
                "INNER JOIN BookClubMember AS bookClubMember " +
                    "ON chapter.book.bookClub.id = bookClubMember.bookClub.id " +
                "WHERE chapter.id = :chapterId " +
                    "AND bookClubMember.member.id = :memberId";

        List<?> foundIds = entityManager.createQuery(nativeQuery)
                .setParameter("chapterId", chapterId)
                .setParameter("memberId", memberId)
                .setMaxResults(1)
                .getResultList();
        return !foundIds.isEmpty();
    }

    public boolean existsBookClubMemberByMemberIdAndTopicId(Long memberId, Long topicId) {
        String nativeQuery = "SELECT topic.id " +
                "FROM Topic AS topic " +
                "INNER JOIN BookClubMember AS bookClubMember " +
                    "ON topic.chapter.book.bookClub.id = bookClubMember.bookClub.id " +
                "WHERE topic.id = :topicId " +
                    "AND bookClubMember.member.id = :memberId";

        List<?> foundIds = entityManager.createQuery(nativeQuery)
                .setParameter("topicId", topicId)
                .setParameter("memberId", memberId)
                .setMaxResults(1)
                .getResultList();
        return !foundIds.isEmpty();
    }

    public boolean existsBookClubMemberByMemberIdAndBookmarkId(Long memberId, Long bookmarkId) {
        String nativeQuery = "SELECT bookmark.id " +
                "FROM Bookmark AS bookmark " +
                "INNER JOIN BookClubMember AS bookClubMember " +
                    "ON bookmark.topic.chapter.book.bookClub.id = bookClubMember.bookClub.id " +
                "WHERE bookmark.id = :bookmarkId " +
                    "AND bookClubMember.member.id = :memberId";

        List<?> foundIds = entityManager.createQuery(nativeQuery)
                .setParameter("bookmarkId", bookmarkId)
                .setParameter("memberId", memberId)
                .setMaxResults(1)
                .getResultList();
        return !foundIds.isEmpty();
    }

    public boolean existsBookClubMemberByMemberIdAndCommentId(Long memberId, Long commentId) {
        String nativeQuery = "SELECT comment.id " +
                "FROM Comment AS comment " +
                "INNER JOIN BookClubMember AS bookClubMember " +
                    "ON comment.bookmark.topic.chapter.book.bookClub.id = bookClubMember.bookClub.id " +
                "WHERE comment.id = :commentId " +
                    "AND bookClubMember.member.id = :memberId";

        List<?> foundIds = entityManager.createQuery(nativeQuery)
                .setParameter("commentId", commentId)
                .setParameter("memberId", memberId)
                .setMaxResults(1)
                .getResultList();
        return !foundIds.isEmpty();
    }

    public boolean existsBookmarkByIdAndWriterId(Long bookmarkId, Long writerId) {
        String nativeQuery = "SELECT bookmark.id " +
                "FROM Bookmark AS bookmark " +
                "WHERE bookmark.id = :bookmarkId " +
                    "AND bookmark.writer.id = :writerId";

        List<?> foundIds = entityManager.createQuery(nativeQuery)
                .setParameter("bookmarkId", bookmarkId)
                .setParameter("writerId", writerId)
                .setMaxResults(1)
                .getResultList();
        return !foundIds.isEmpty();
    }

    public boolean existsCommentByIdAndWriterId(Long commentId, Long writerId) {
        String nativeQuery = "SELECT comment.id " +
                "FROM Comment as comment " +
                "WHERE comment.id = :commentId " +
                    "AND comment.writer.id = :writerId";

        List<?> foundIds = entityManager.createQuery(nativeQuery)
                .setParameter("commentId", commentId)
                .setParameter("writerId", writerId)
                .setMaxResults(1)
                .getResultList();
        return !foundIds.isEmpty();
    }

}
