package codesquad.bookkbookk.domain.mapping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.common.type.Reaction;
import codesquad.bookkbookk.domain.mapping.entity.BookmarkReaction;

public interface BookmarkReactionRepository extends JpaRepository<BookmarkReaction, Long> {

    public boolean existsByBookmarkIdAndReactorIdAndReaction(Long bookmarkId, Long reactorId, Reaction reaction);

}
