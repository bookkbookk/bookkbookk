package codesquad.bookkbookk.domain.mapping.repository;

import java.util.List;

import codesquad.bookkbookk.domain.mapping.entity.BookmarkReaction;

public interface BookmarkReactionCustomRepository {

    List<BookmarkReaction> findAllByBookmarkId(Long bookmarkId);

}
