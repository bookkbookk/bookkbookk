package codesquad.bookkbookk.domain.bookmark.repository;

import java.util.List;

import codesquad.bookkbookk.domain.bookmark.data.dto.BookmarkFilter;
import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;

public interface BookmarkCustomRepository {

    List<Bookmark> findAllByFilter(Long bookId, BookmarkFilter bookmarkFilter);

    List<Bookmark> findAllByTopicId(Long topicId);

}
