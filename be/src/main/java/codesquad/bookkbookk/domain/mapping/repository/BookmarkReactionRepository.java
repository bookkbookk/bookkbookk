package codesquad.bookkbookk.domain.mapping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.common.type.Reaction;
import codesquad.bookkbookk.domain.mapping.entity.BookmarkReaction;

public interface BookmarkReactionRepository extends JpaRepository<BookmarkReaction, Long>, BookmarkReactionCustomRepository {

    public boolean existsByBookmarkIdAndReactorIdAndReaction(Long bookmarkId, Long reactorId, Reaction reaction);
    public Optional<BookmarkReaction> findByBookmarkIdAndReactorIdAndReaction(Long bookmarkId, Long reactorId,
                                                                              Reaction reaction);

}
