package codesquad.bookkbookk.domain.chapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

}
