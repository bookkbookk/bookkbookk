package codesquad.bookkbookk.domain.bookmark.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    boolean existsByIdAndWriterId(Long bookmarkId, Long writerId);
    List<Bookmark> findAllByTopicId(Long topicId);

}
