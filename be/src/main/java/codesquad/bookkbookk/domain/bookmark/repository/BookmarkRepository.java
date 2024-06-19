package codesquad.bookkbookk.domain.bookmark.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkCustomRepository {

    boolean existsByIdAndWriterId(Long bookmarkId, Long writerId);

    List<Bookmark> findAllByTopicId(Long topicId);

    @Query("SELECT b " +
            "FROM Bookmark b " +
            "WHERE b.page >= :startPage " +
            "AND b.page <= :endPage " +
            "ORDER BY b.page ASC")
    List<Bookmark> findAllByPages(@Param("startPage") Integer startPage, @Param("endPage") Integer endPage);

    @Query("SELECT b " +
            "FROM Bookmark b " +
            "WHERE b.updatedTime >= :startTime " +
            "AND b.updatedTime <= :endTime " +
            "ORDER BY b.page ASC")
    List<Bookmark> findAllByUpdatedTime(@Param("startTime") Instant startTime, @Param("endTime") Instant endTime);

}
