package codesquad.bookkbookk.domain.chapter.repository;

import java.util.List;

import codesquad.bookkbookk.common.type.Status;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;

public interface ChapterCustomRepository {

    List<Chapter> findAllByBookIdAndStatus(Long bookId, Status status);
    List<Chapter> saveAllInBatch(List<Chapter> chapters);


}
