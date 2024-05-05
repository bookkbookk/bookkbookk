package codesquad.bookkbookk.domain.chapter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.common.type.Status;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    List<Chapter> findAllByBookId(Long bookId);

    List<Chapter> findAllByBookIdAndStatus(Long bookId, Status status);

}
