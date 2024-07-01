package codesquad.bookkbookk.domain.book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import codesquad.bookkbookk.domain.book.data.entity.Book;

public interface BookCustomRepository {

    Page<Book> findPageByMemberId(Long memberId, Pageable pageable);

    Slice<Book> findSliceByBookClubId(Long bookClubId, Pageable pageable);

}
